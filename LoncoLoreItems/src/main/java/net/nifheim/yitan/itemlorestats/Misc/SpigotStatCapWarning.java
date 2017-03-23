 package net.nifheim.yitan.itemlorestats.Misc;
 
 import java.io.File;
 import org.bukkit.configuration.file.FileConfiguration;
 import org.bukkit.configuration.file.YamlConfiguration;
 
 
 public class SpigotStatCapWarning
 {
   private FileConfiguration PlayerDataConfig;
   String serverDir = new File("").getAbsolutePath();
   
   public boolean spigotConfigExists()
   {
     if (new File(this.serverDir + File.separator + "spigot.yml").isFile()) {
       return true;
     }
     
     return false;
   }
   
   public void updateSpigotValues()
   {
     if (spigotConfigExists()) {
       try {
         this.PlayerDataConfig = new YamlConfiguration();
         this.PlayerDataConfig.load(new File(this.serverDir + File.separator + "spigot.yml"));
         
         this.PlayerDataConfig.set("settings.attribute.maxHealth.max", Double.valueOf(2000000.0D));
         this.PlayerDataConfig.set("settings.attribute.movementSpeed.max", Double.valueOf(2000000.0D));
         this.PlayerDataConfig.set("settings.attribute.attackDamage.max", Double.valueOf(2000000.0D));
         
         this.PlayerDataConfig.save(new File(this.serverDir + File.separator + "spigot.yml"));
       }
       catch (Exception e) {
         e.printStackTrace();
       }
     }
   }
 }
