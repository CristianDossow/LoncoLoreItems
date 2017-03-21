 package com.github.supavitax.itemlorestats.Misc;
 
 import com.github.supavitax.itemlorestats.ItemLoreStats;
 import java.io.BufferedInputStream;
 import java.io.BufferedOutputStream;
 import java.io.BufferedReader;
 import java.io.File;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.io.InputStreamReader;
 import java.net.MalformedURLException;
 import java.net.URL;
 import java.net.URLConnection;
 import java.util.Enumeration;
 import java.util.List;
 import java.util.logging.Level;
 import java.util.logging.Logger;
 import java.util.zip.ZipEntry;
 import java.util.zip.ZipFile;
 import org.bukkit.Server;
 import org.bukkit.configuration.file.YamlConfiguration;
 import org.bukkit.configuration.file.YamlConfigurationOptions;
 import org.bukkit.plugin.Plugin;
 import org.bukkit.plugin.PluginDescriptionFile;
 import org.json.simple.JSONArray;
 import org.json.simple.JSONObject;
 import org.json.simple.JSONValue;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 public class Updater
 {
   private Plugin plugin;
   private UpdateType type;
   private String versionName;
   private String versionLink;
   private String versionType;
   private String versionGameVersion;
   private boolean announce;
   private URL url;
   private File file;
   private Thread thread;
   private int id = -1;
   private String apiKey = null;
   
   private static final String TITLE_VALUE = "name";
   private static final String LINK_VALUE = "downloadUrl";
   private static final String TYPE_VALUE = "releaseType";
   private static final String VERSION_VALUE = "gameVersion";
   private static final String QUERY = "/servermods/files?projectIds=";
   private static final String HOST = "https://api.curseforge.com";
   private static final String USER_AGENT = "Updater (by Gravity)";
   private static final String delimiter = "^v|[\\s_-]v";
   private static final String[] NO_UPDATE_TAG = { "-DEV", "-PRE", "-SNAPSHOT" };
   private static final int BYTE_SIZE = 1024;
   private final YamlConfiguration config = new YamlConfiguration();
   private String updateFolder;
   private UpdateResult result = UpdateResult.SUCCESS;
   
 
 
   public static enum UpdateResult
   {
     SUCCESS, 
     
 
 
     NO_UPDATE, 
     
 
 
     DISABLED, 
     
 
 
     FAIL_DOWNLOAD, 
     
 
 
     FAIL_DBO, 
     
 
 
     FAIL_NOVERSION, 
     
 
 
     FAIL_BADID, 
     
 
 
     FAIL_APIKEY, 
     
 
 
     UPDATE_AVAILABLE;
   }
   
 
 
 
 
 
   public static enum UpdateType
   {
     DEFAULT, 
     
 
 
     NO_VERSION_CHECK, 
     
 
 
     NO_DOWNLOAD;
   }
   
 
 
 
 
 
   public static enum ReleaseType
   {
     ALPHA, 
     
 
 
     BETA, 
     
 
 
     RELEASE;
   }
   
 
 
 
 
 
 
 
 
 
 
 
   public Updater(Plugin plugin, int id, File file, UpdateType type, boolean announce)
   {
     this.plugin = plugin;
     this.type = type;
     this.announce = announce;
     this.file = file;
     this.id = id;
     this.updateFolder = ItemLoreStats.plugin.getServer().getUpdateFolder();
     
     File pluginFile = plugin.getDataFolder().getParentFile();
     File updaterFile = new File(pluginFile, "Updater");
     File updaterConfigFile = new File(updaterFile, "config.yml");
     
     this.config.options().header("This configuration file affects all plugins using the Updater system (version 2+ - http://forums.bukkit.org/threads/96681/ )\nIf you wish to use your API key, read http://wiki.bukkit.org/ServerMods_API and place it below.\nSome updating systems will not adhere to the disabled value, but these may be turned off in their plugin's configuration.");
     
 
     this.config.addDefault("api-key", "PUT_API_KEY_HERE");
     this.config.addDefault("disable", Boolean.valueOf(false));
     
     if (!updaterFile.exists()) {
       updaterFile.mkdir();
     }
     
     boolean createFile = !updaterConfigFile.exists();
     try {
       if (createFile) {
         updaterConfigFile.createNewFile();
         this.config.options().copyDefaults(true);
         this.config.save(updaterConfigFile);
       } else {
         this.config.load(updaterConfigFile);
       }
     } catch (Exception e) {
       if (createFile) {
         plugin.getLogger().severe("The updater could not create configuration at " + updaterFile.getAbsolutePath());
       } else {
         plugin.getLogger().severe("The updater could not load configuration at " + updaterFile.getAbsolutePath());
       }
       plugin.getLogger().log(Level.SEVERE, null, e);
     }
     
     if (this.config.getBoolean("disable")) {
       this.result = UpdateResult.DISABLED;
       return;
     }
     
     String key = this.config.getString("api-key");
     if ((key.equalsIgnoreCase("PUT_API_KEY_HERE")) || (key.equals(""))) {
       key = null;
     }
     
     this.apiKey = key;
     try
     {
       this.url = new URL("https://api.curseforge.com/servermods/files?projectIds=" + id);
     } catch (MalformedURLException e) {
       plugin.getLogger().log(Level.SEVERE, "The project ID provided for updating, " + id + " is invalid.", e);
       this.result = UpdateResult.FAIL_BADID;
     }
     
     this.thread = new Thread(new UpdateRunnable());
     this.thread.start();
   }
   
 
 
 
 
 
   public UpdateResult getResult()
   {
     waitForThread();
     return this.result;
   }
   
 
 
 
 
 
   public ReleaseType getLatestType()
   {
     waitForThread();
     if (this.versionType != null) { ReleaseType[] arrayOfReleaseType;
       int j = (arrayOfReleaseType = ReleaseType.values()).length; for (int i = 0; i < j; i++) { ReleaseType type = arrayOfReleaseType[i];
         if (this.versionType.equals(type.name().toLowerCase())) {
           return type;
         }
       }
     }
     return null;
   }
   
 
 
 
 
   public String getLatestGameVersion()
   {
     waitForThread();
     return this.versionGameVersion;
   }
   
 
 
 
 
   public String getLatestName()
   {
     waitForThread();
     return this.versionName;
   }
   
 
 
 
 
   public String getLatestFileLink()
   {
     waitForThread();
     return this.versionLink;
   }
   
 
 
 
   private void waitForThread()
   {
     if ((this.thread != null) && (this.thread.isAlive())) {
       try {
         this.thread.join();
       } catch (InterruptedException e) {
         this.plugin.getLogger().log(Level.SEVERE, null, e);
       }
     }
   }
   
   /* Error */
   private void saveFile(File folder, String file, String link)
   {
     // Byte code:
     //   0: aload_1
     //   1: invokevirtual 165	java/io/File:exists	()Z
     //   4: ifne +8 -> 12
     //   7: aload_1
     //   8: invokevirtual 169	java/io/File:mkdir	()Z
     //   11: pop
     //   12: aconst_null
     //   13: astore 4
     //   15: aconst_null
     //   16: astore 5
     //   18: new 244	java/net/URL
     //   21: dup
     //   22: aload_3
     //   23: invokespecial 251	java/net/URL:<init>	(Ljava/lang/String;)V
     //   26: astore 6
     //   28: aload 6
     //   30: invokevirtual 336	java/net/URL:openConnection	()Ljava/net/URLConnection;
     //   33: invokevirtual 340	java/net/URLConnection:getContentLength	()I
     //   36: istore 7
     //   38: new 346	java/io/BufferedInputStream
     //   41: dup
     //   42: aload 6
     //   44: invokevirtual 348	java/net/URL:openStream	()Ljava/io/InputStream;
     //   47: invokespecial 352	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
     //   50: astore 4
     //   52: new 355	java/io/FileOutputStream
     //   55: dup
     //   56: new 190	java/lang/StringBuilder
     //   59: dup
     //   60: aload_1
     //   61: invokevirtual 197	java/io/File:getAbsolutePath	()Ljava/lang/String;
     //   64: invokestatic 357	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
     //   67: invokespecial 194	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
     //   70: getstatic 360	java/io/File:separator	Ljava/lang/String;
     //   73: invokevirtual 200	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
     //   76: aload_2
     //   77: invokevirtual 200	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
     //   80: invokevirtual 204	java/lang/StringBuilder:toString	()Ljava/lang/String;
     //   83: invokespecial 363	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
     //   86: astore 5
     //   88: sipush 1024
     //   91: newarray <illegal type>
     //   93: astore 8
     //   95: aload_0
     //   96: getfield 98	com/github/supavitax/itemlorestats/Misc/Updater:announce	Z
     //   99: ifeq +35 -> 134
     //   102: aload_0
     //   103: getfield 94	com/github/supavitax/itemlorestats/Misc/Updater:plugin	Lorg/bukkit/plugin/Plugin;
     //   106: invokeinterface 186 1 0
     //   111: new 190	java/lang/StringBuilder
     //   114: dup
     //   115: ldc_w 364
     //   118: invokespecial 194	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
     //   121: aload_0
     //   122: getfield 320	com/github/supavitax/itemlorestats/Misc/Updater:versionName	Ljava/lang/String;
     //   125: invokevirtual 200	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
     //   128: invokevirtual 204	java/lang/StringBuilder:toString	()Ljava/lang/String;
     //   131: invokevirtual 366	java/util/logging/Logger:info	(Ljava/lang/String;)V
     //   134: lconst_0
     //   135: lstore 10
     //   137: goto +96 -> 233
     //   140: lload 10
     //   142: iload 9
     //   144: i2l
     //   145: ladd
     //   146: lstore 10
     //   148: aload 5
     //   150: aload 8
     //   152: iconst_0
     //   153: iload 9
     //   155: invokevirtual 369	java/io/FileOutputStream:write	([BII)V
     //   158: lload 10
     //   160: ldc2_w 373
     //   163: lmul
     //   164: iload 7
     //   166: i2l
     //   167: ldiv
     //   168: l2i
     //   169: istore 12
     //   171: aload_0
     //   172: getfield 98	com/github/supavitax/itemlorestats/Misc/Updater:announce	Z
     //   175: ifeq +58 -> 233
     //   178: iload 12
     //   180: bipush 10
     //   182: irem
     //   183: ifne +50 -> 233
     //   186: aload_0
     //   187: getfield 94	com/github/supavitax/itemlorestats/Misc/Updater:plugin	Lorg/bukkit/plugin/Plugin;
     //   190: invokeinterface 186 1 0
     //   195: new 190	java/lang/StringBuilder
     //   198: dup
     //   199: ldc_w 375
     //   202: invokespecial 194	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
     //   205: iload 12
     //   207: invokevirtual 248	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
     //   210: ldc_w 377
     //   213: invokevirtual 200	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
     //   216: iload 7
     //   218: invokevirtual 248	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
     //   221: ldc_w 379
     //   224: invokevirtual 200	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
     //   227: invokevirtual 204	java/lang/StringBuilder:toString	()Ljava/lang/String;
     //   230: invokevirtual 366	java/util/logging/Logger:info	(Ljava/lang/String;)V
     //   233: aload 4
     //   235: aload 8
     //   237: iconst_0
     //   238: sipush 1024
     //   241: invokevirtual 381	java/io/BufferedInputStream:read	([BII)I
     //   244: dup
     //   245: istore 9
     //   247: iconst_m1
     //   248: if_icmpne -108 -> 140
     //   251: new 126	java/io/File
     //   254: dup
     //   255: aload_0
     //   256: getfield 94	com/github/supavitax/itemlorestats/Misc/Updater:plugin	Lorg/bukkit/plugin/Plugin;
     //   259: invokeinterface 119 1 0
     //   264: invokevirtual 385	java/io/File:getParent	()Ljava/lang/String;
     //   267: aload_0
     //   268: getfield 117	com/github/supavitax/itemlorestats/Misc/Updater:updateFolder	Ljava/lang/String;
     //   271: invokespecial 388	java/io/File:<init>	(Ljava/lang/String;Ljava/lang/String;)V
     //   274: invokevirtual 391	java/io/File:listFiles	()[Ljava/io/File;
     //   277: dup
     //   278: astore 15
     //   280: arraylength
     //   281: istore 14
     //   283: iconst_0
     //   284: istore 13
     //   286: goto +33 -> 319
     //   289: aload 15
     //   291: iload 13
     //   293: aaload
     //   294: astore 12
     //   296: aload 12
     //   298: invokevirtual 395	java/io/File:getName	()Ljava/lang/String;
     //   301: ldc_w 398
     //   304: invokevirtual 400	java/lang/String:endsWith	(Ljava/lang/String;)Z
     //   307: ifeq +9 -> 316
     //   310: aload 12
     //   312: invokevirtual 403	java/io/File:delete	()Z
     //   315: pop
     //   316: iinc 13 1
     //   319: iload 13
     //   321: iload 14
     //   323: if_icmplt -34 -> 289
     //   326: new 126	java/io/File
     //   329: dup
     //   330: new 190	java/lang/StringBuilder
     //   333: dup
     //   334: aload_1
     //   335: invokevirtual 197	java/io/File:getAbsolutePath	()Ljava/lang/String;
     //   338: invokestatic 357	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
     //   341: invokespecial 194	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
     //   344: getstatic 360	java/io/File:separator	Ljava/lang/String;
     //   347: invokevirtual 200	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
     //   350: aload_2
     //   351: invokevirtual 200	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
     //   354: invokevirtual 204	java/lang/StringBuilder:toString	()Ljava/lang/String;
     //   357: invokespecial 406	java/io/File:<init>	(Ljava/lang/String;)V
     //   360: astore 12
     //   362: aload 12
     //   364: invokevirtual 395	java/io/File:getName	()Ljava/lang/String;
     //   367: ldc_w 398
     //   370: invokevirtual 400	java/lang/String:endsWith	(Ljava/lang/String;)Z
     //   373: ifeq +12 -> 385
     //   376: aload_0
     //   377: aload 12
     //   379: invokevirtual 407	java/io/File:getCanonicalPath	()Ljava/lang/String;
     //   382: invokespecial 410	com/github/supavitax/itemlorestats/Misc/Updater:unzip	(Ljava/lang/String;)V
     //   385: aload_0
     //   386: getfield 98	com/github/supavitax/itemlorestats/Misc/Updater:announce	Z
     //   389: ifeq +103 -> 492
     //   392: aload_0
     //   393: getfield 94	com/github/supavitax/itemlorestats/Misc/Updater:plugin	Lorg/bukkit/plugin/Plugin;
     //   396: invokeinterface 186 1 0
     //   401: ldc_w 413
     //   404: invokevirtual 366	java/util/logging/Logger:info	(Ljava/lang/String;)V
     //   407: goto +85 -> 492
     //   410: astore 6
     //   412: aload_0
     //   413: getfield 94	com/github/supavitax/itemlorestats/Misc/Updater:plugin	Lorg/bukkit/plugin/Plugin;
     //   416: invokeinterface 186 1 0
     //   421: ldc_w 415
     //   424: invokevirtual 417	java/util/logging/Logger:warning	(Ljava/lang/String;)V
     //   427: aload_0
     //   428: getstatic 420	com/github/supavitax/itemlorestats/Misc/Updater$UpdateResult:FAIL_DOWNLOAD	Lcom/github/supavitax/itemlorestats/Misc/Updater$UpdateResult;
     //   431: putfield 92	com/github/supavitax/itemlorestats/Misc/Updater:result	Lcom/github/supavitax/itemlorestats/Misc/Updater$UpdateResult;
     //   434: aload 4
     //   436: ifnull +8 -> 444
     //   439: aload 4
     //   441: invokevirtual 423	java/io/BufferedInputStream:close	()V
     //   444: aload 5
     //   446: ifnull +71 -> 517
     //   449: aload 5
     //   451: invokevirtual 426	java/io/FileOutputStream:close	()V
     //   454: goto +63 -> 517
     //   457: astore 17
     //   459: goto +58 -> 517
     //   462: astore 16
     //   464: aload 4
     //   466: ifnull +8 -> 474
     //   469: aload 4
     //   471: invokevirtual 423	java/io/BufferedInputStream:close	()V
     //   474: aload 5
     //   476: ifnull +13 -> 489
     //   479: aload 5
     //   481: invokevirtual 426	java/io/FileOutputStream:close	()V
     //   484: goto +5 -> 489
     //   487: astore 17
     //   489: aload 16
     //   491: athrow
     //   492: aload 4
     //   494: ifnull +8 -> 502
     //   497: aload 4
     //   499: invokevirtual 423	java/io/BufferedInputStream:close	()V
     //   502: aload 5
     //   504: ifnull +13 -> 517
     //   507: aload 5
     //   509: invokevirtual 426	java/io/FileOutputStream:close	()V
     //   512: goto +5 -> 517
     //   515: astore 17
     //   517: return
     // Line number table:
     //   Java source line #304	-> byte code offset #0
     //   Java source line #305	-> byte code offset #7
     //   Java source line #307	-> byte code offset #12
     //   Java source line #308	-> byte code offset #15
     //   Java source line #311	-> byte code offset #18
     //   Java source line #312	-> byte code offset #28
     //   Java source line #313	-> byte code offset #38
     //   Java source line #314	-> byte code offset #52
     //   Java source line #316	-> byte code offset #88
     //   Java source line #318	-> byte code offset #95
     //   Java source line #319	-> byte code offset #102
     //   Java source line #321	-> byte code offset #134
     //   Java source line #322	-> byte code offset #137
     //   Java source line #323	-> byte code offset #140
     //   Java source line #324	-> byte code offset #148
     //   Java source line #325	-> byte code offset #158
     //   Java source line #326	-> byte code offset #171
     //   Java source line #327	-> byte code offset #186
     //   Java source line #322	-> byte code offset #233
     //   Java source line #331	-> byte code offset #251
     //   Java source line #332	-> byte code offset #296
     //   Java source line #333	-> byte code offset #310
     //   Java source line #331	-> byte code offset #316
     //   Java source line #337	-> byte code offset #326
     //   Java source line #338	-> byte code offset #362
     //   Java source line #340	-> byte code offset #376
     //   Java source line #342	-> byte code offset #385
     //   Java source line #343	-> byte code offset #392
     //   Java source line #345	-> byte code offset #407
     //   Java source line #346	-> byte code offset #412
     //   Java source line #347	-> byte code offset #427
     //   Java source line #350	-> byte code offset #434
     //   Java source line #351	-> byte code offset #439
     //   Java source line #353	-> byte code offset #444
     //   Java source line #354	-> byte code offset #449
     //   Java source line #356	-> byte code offset #454
     //   Java source line #348	-> byte code offset #462
     //   Java source line #350	-> byte code offset #464
     //   Java source line #351	-> byte code offset #469
     //   Java source line #353	-> byte code offset #474
     //   Java source line #354	-> byte code offset #479
     //   Java source line #356	-> byte code offset #484
     //   Java source line #358	-> byte code offset #489
     //   Java source line #350	-> byte code offset #492
     //   Java source line #351	-> byte code offset #497
     //   Java source line #353	-> byte code offset #502
     //   Java source line #354	-> byte code offset #507
     //   Java source line #356	-> byte code offset #512
     //   Java source line #359	-> byte code offset #517
     // Local variable table:
     //   start	length	slot	name	signature
     //   0	518	0	this	Updater
     //   0	518	1	folder	File
     //   0	518	2	file	String
     //   0	518	3	link	String
     //   13	485	4	in	BufferedInputStream
     //   16	492	5	fout	FileOutputStream
     //   26	17	6	url	URL
     //   410	3	6	ex	Exception
     //   36	181	7	fileLength	int
     //   93	143	8	data	byte[]
     //   140	14	9	count	int
     //   245	3	9	count	int
     //   135	24	10	downloaded	long
     //   169	37	12	percent	int
     //   294	17	12	xFile	File
     //   360	18	12	dFile	File
     //   284	40	13	i	int
     //   281	43	14	j	int
     //   278	12	15	arrayOfFile	File[]
     //   462	28	16	localObject	Object
     //   457	1	17	localException1	Exception
     //   487	1	17	localException2	Exception
     //   515	1	17	localException3	Exception
     // Exception table:
     //   from	to	target	type
     //   18	407	410	java/lang/Exception
     //   434	454	457	java/lang/Exception
     //   18	434	462	finally
     //   464	484	487	java/lang/Exception
     //   492	512	515	java/lang/Exception
   }
   
/*        private void unzip(String file)
        {
          try
          {
 368        File fSourceZip = new File(file);
 369        String zipPath = file.substring(0, file.length() - 4);
 370        ZipFile zipFile = new ZipFile(fSourceZip);
 371        Enumeration<? extends ZipEntry> e = zipFile.entries();
 372        int b; String name; while (e.hasMoreElements()) {
 373          ZipEntry entry = (ZipEntry)e.nextElement();
 374          destinationFilePath = new File(zipPath, entry.getName());
 375          destinationFilePath.getParentFile().mkdirs();
 376          if (!entry.isDirectory())
              {
      
 379            bis = new BufferedInputStream(zipFile.getInputStream(entry));
                
 381            byte[] buffer = new byte[1];
 382            FileOutputStream fos = new FileOutputStream(destinationFilePath);
 383            BufferedOutputStream bos = new BufferedOutputStream(fos, 1024);
 384            while ((b = bis.read(buffer, 0, 1024)) != -1) { int b;
 385              bos.write(buffer, 0, b);
                }
 387            bos.flush();
 388            bos.close();
 389            bis.close();
 390            name = destinationFilePath.getName();
 391            if ((name.endsWith(".jar")) && (pluginFile(name))) {
 392              destinationFilePath.renameTo(new File(this.plugin.getDataFolder().getParent(), this.updateFolder + File.separator + name));
                }
                
 395            entry = null;
 396            destinationFilePath = null;
              } }
 398        e = null;
 399        zipFile.close();
 400        zipFile = null;
            
      
 403        BufferedInputStream bis = (b = new File(zipPath).listFiles()).length; for (File destinationFilePath = 0; destinationFilePath < bis; destinationFilePath++) { File dFile = b[destinationFilePath];
 404          if ((dFile.isDirectory()) && 
 405            (pluginFile(dFile.getName()))) {
 406            File oFile = new File(this.plugin.getDataFolder().getParent(), dFile.getName());
 407            File[] contents = oFile.listFiles();
 408            File[] arrayOfFile1; String str1 = (arrayOfFile1 = dFile.listFiles()).length; for (name = 0; name < str1; name++) { File cFile = arrayOfFile1[name];
                  
 410              boolean found = false;
 411              File[] arrayOfFile2; int j = (arrayOfFile2 = contents).length; for (int i = 0; i < j; i++) { File xFile = arrayOfFile2[i];
                    
 413                if (xFile.getName().equals(cFile.getName())) {
 414                  found = true;
 415                  break;
                    }
                  }
 418              if (!found)
                  {
 420                cFile.renameTo(new File(oFile.getCanonicalFile() + File.separator + cFile.getName()));
                  }
                  else {
 423                cFile.delete();
                  }
                }
              }
              
 428          dFile.delete();
            }
 430        new File(zipPath).delete();
 431        fSourceZip.delete();
          } catch (IOException e) {
 433        this.plugin.getLogger().log(Level.SEVERE, "The auto-updater tried to unzip a new update file, but was unsuccessful.", e);
 434        this.result = UpdateResult.FAIL_DOWNLOAD;
          }
 436      new File(file).delete();
        }*/
   
 
 
   private boolean pluginFile(String name)
   {
     File[] arrayOfFile;
     
 
     int j = (arrayOfFile = new File("plugins").listFiles()).length; for (int i = 0; i < j; i++) { File file = arrayOfFile[i];
       if (file.getName().equals(name)) {
         return true;
       }
     }
     return false;
   }
   
 
 
 
 
 
   private boolean versionCheck(String title)
   {
     if (this.type != UpdateType.NO_VERSION_CHECK) {
       String localVersion = this.plugin.getDescription().getVersion();
       if (title.split("^v|[\\s_-]v").length == 2) {
         String remoteVersion = title.split("^v|[\\s_-]v")[1].split(" ")[0];
         
         if ((hasTag(localVersion)) || (!shouldUpdate(localVersion, remoteVersion)))
         {
           this.result = UpdateResult.NO_UPDATE;
           return false;
         }
       }
       else {
         String authorInfo = " (" + (String)this.plugin.getDescription().getAuthors().get(0) + ")";
         this.plugin.getLogger().warning("The author of this plugin" + authorInfo + " has misconfigured their Auto Update system");
         this.plugin.getLogger().warning("File versions should follow the format 'PluginName vVERSION'");
         this.plugin.getLogger().warning("Please notify the author of this error.");
         this.result = UpdateResult.FAIL_NOVERSION;
         return false;
       }
     }
     return true;
   }
   
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
   public boolean shouldUpdate(String localVersion, String remoteVersion)
   {
     return !localVersion.equalsIgnoreCase(remoteVersion);
   }
   
 
 
   private boolean hasTag(String version)
   {
     String[] arrayOfString;
     
 
     int j = (arrayOfString = NO_UPDATE_TAG).length; for (int i = 0; i < j; i++) { String string = arrayOfString[i];
       if (version.contains(string)) {
         return true;
       }
     }
     return false;
   }
   
 
 
 
   private boolean read()
   {
     try
     {
       URLConnection conn = this.url.openConnection();
       conn.setConnectTimeout(5000);
       
       if (this.apiKey != null) {
         conn.addRequestProperty("X-API-Key", this.apiKey);
       }
       conn.addRequestProperty("User-Agent", "Updater (by Gravity)");
       
       conn.setDoOutput(true);
       
       BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
       String response = reader.readLine();
       
       JSONArray array = (JSONArray)JSONValue.parse(response);
       
       if (array.size() == 0) {
         this.plugin.getLogger().warning("The updater could not find any files for the project id " + this.id);
         this.result = UpdateResult.FAIL_BADID;
         return false;
       }
       
       this.versionName = ((String)((JSONObject)array.get(array.size() - 1)).get("name"));
       this.versionLink = ((String)((JSONObject)array.get(array.size() - 1)).get("downloadUrl"));
       this.versionType = ((String)((JSONObject)array.get(array.size() - 1)).get("releaseType"));
       this.versionGameVersion = ((String)((JSONObject)array.get(array.size() - 1)).get("gameVersion"));
       
       return true;
     } catch (IOException e) {
       if (e.getMessage().contains("HTTP response code: 403")) {
         this.plugin.getLogger().severe("dev.bukkit.org rejected the API key provided in plugins/Updater/config.yml");
         this.plugin.getLogger().severe("Please double-check your configuration to ensure it is correct.");
         this.result = UpdateResult.FAIL_APIKEY;
       } else {
         this.plugin.getLogger().severe("The updater could not contact dev.bukkit.org for updating.");
         this.plugin.getLogger().severe("If you have not recently modified your configuration and this is the first time you are seeing this message, the site may be experiencing temporary downtime.");
         this.result = UpdateResult.FAIL_DBO;
       }
       this.plugin.getLogger().log(Level.SEVERE, null, e); }
     return false;
   }
   
   private class UpdateRunnable implements Runnable
   {
     private UpdateRunnable() {}
     
     public void run() {
       if (Updater.this.url != null)
       {
         if ((Updater.this.read()) && 
           (Updater.this.versionCheck(Updater.this.versionName))) {
           if ((Updater.this.versionLink != null) && (Updater.this.type != Updater.UpdateType.NO_DOWNLOAD)) {
             String name = Updater.this.file.getName();
             
             if (Updater.this.versionLink.endsWith(".zip")) {
               String[] split = Updater.this.versionLink.split("/");
               name = split[(split.length - 1)];
             }
             Updater.this.saveFile(new File(Updater.this.plugin.getDataFolder().getParent(), Updater.this.updateFolder), name, Updater.this.versionLink);
           } else {
             Updater.this.result = Updater.UpdateResult.UPDATE_AVAILABLE;
           }
         }
       }
     }
   }
 }

