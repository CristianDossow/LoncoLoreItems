 package com.github.supavitax.itemlorestats.Util;
 
 import org.bukkit.Material;
 
 public class Util_Material
 {
   public double materialToDamage(Material mat) {
     if ((mat == Material.WOOD_HOE) || (mat == Material.GOLD_HOE) || (mat == Material.STONE_HOE) || (mat == Material.IRON_HOE) || (mat == Material.DIAMOND_HOE))
       return 1.0D;
     if ((mat == Material.WOOD_PICKAXE) || (mat == Material.GOLD_PICKAXE))
       return 2.0D;
     if ((mat == Material.WOOD_SPADE) || (mat == Material.GOLD_SPADE))
       return 2.5D;
     if (mat == Material.STONE_SPADE)
       return 3.5D;
     if ((mat == Material.WOOD_SWORD) || (mat == Material.GOLD_SWORD) || (mat == Material.IRON_PICKAXE))
       return 4.0D;
     if ((mat == Material.STONE_SWORD) || (mat == Material.DIAMOND_PICKAXE))
       return 5.0D;
     if (mat == Material.DIAMOND_SPADE)
       return 5.5D;
     if (mat == Material.IRON_SWORD)
       return 6.0D;
     if ((mat == Material.WOOD_AXE) || (mat == Material.GOLD_AXE) || (mat == Material.DIAMOND_SWORD))
       return 7.0D;
     if ((mat == Material.STONE_AXE) || (mat == Material.IRON_AXE) || (mat == Material.DIAMOND_AXE)) {
       return 9.0D;
     }
     
 
 
     return 1.0D;
   }
   
   public String materialToId(Material mat)
   {
     String customMaterial = mat.toString().split(":")[0];
     
     if (mat != null) {
       if (mat == Material.IRON_SPADE)
         return "256";
       if (mat == Material.IRON_PICKAXE)
         return "257";
       if (mat == Material.IRON_AXE)
         return "258";
       if (mat == Material.FLINT_AND_STEEL)
         return "259";
       if (mat == Material.BOW)
         return "261";
       if (mat == Material.IRON_SWORD)
         return "267";
       if (mat == Material.WOOD_SWORD)
         return "268";
       if (mat == Material.WOOD_SPADE)
         return "269";
       if (mat == Material.WOOD_PICKAXE)
         return "270";
       if (mat == Material.WOOD_AXE)
         return "271";
       if (mat == Material.STONE_SWORD)
         return "272";
       if (mat == Material.STONE_SPADE)
         return "273";
       if (mat == Material.STONE_PICKAXE)
         return "274";
       if (mat == Material.STONE_AXE)
         return "275";
       if (mat == Material.DIAMOND_SWORD)
         return "276";
       if (mat == Material.DIAMOND_SPADE)
         return "277";
       if (mat == Material.DIAMOND_PICKAXE)
         return "278";
       if (mat == Material.DIAMOND_AXE)
         return "279";
       if (mat == Material.STICK)
         return "280";
       if (mat == Material.GOLD_SWORD)
         return "283";
       if (mat == Material.GOLD_SPADE)
         return "284";
       if (mat == Material.GOLD_PICKAXE)
         return "285";
       if (mat == Material.GOLD_AXE)
         return "286";
       if (mat == Material.STRING)
         return "287";
       if (mat == Material.WOOD_HOE)
         return "290";
       if (mat == Material.STONE_HOE)
         return "291";
       if (mat == Material.IRON_HOE)
         return "292";
       if (mat == Material.DIAMOND_HOE)
         return "293";
       if (mat == Material.GOLD_HOE)
         return "294";
       if (mat == Material.LEATHER_HELMET)
         return "298";
       if (mat == Material.LEATHER_CHESTPLATE)
         return "299";
       if (mat == Material.LEATHER_LEGGINGS)
         return "300";
       if (mat == Material.LEATHER_BOOTS)
         return "301";
       if (mat == Material.CHAINMAIL_HELMET)
         return "302";
       if (mat == Material.CHAINMAIL_CHESTPLATE)
         return "303";
       if (mat == Material.CHAINMAIL_LEGGINGS)
         return "304";
       if (mat == Material.CHAINMAIL_BOOTS)
         return "305";
       if (mat == Material.IRON_HELMET)
         return "306";
       if (mat == Material.IRON_CHESTPLATE)
         return "307";
       if (mat == Material.IRON_LEGGINGS)
         return "308";
       if (mat == Material.IRON_BOOTS)
         return "309";
       if (mat == Material.DIAMOND_HELMET)
         return "310";
       if (mat == Material.DIAMOND_CHESTPLATE)
         return "311";
       if (mat == Material.DIAMOND_LEGGINGS)
         return "312";
       if (mat == Material.DIAMOND_BOOTS)
         return "313";
       if (mat == Material.GOLD_HELMET)
         return "314";
       if (mat == Material.GOLD_CHESTPLATE)
         return "315";
       if (mat == Material.GOLD_LEGGINGS)
         return "316";
       if (mat == Material.GOLD_BOOTS)
         return "317";
       if (mat == Material.SHEARS)
         return "359";
       if (mat == Material.BLAZE_ROD) {
         return "369";
       }
       return customMaterial;
     }
     
 
     return "ERROR";
   }
   
   public Material idToMaterial(String id)
   {
     String customMaterial = id.toString().split(":")[0];
     
     if (id != null) {
       if (id.equals("256"))
         return Material.IRON_HOE;
       if (id.equals("257"))
         return Material.IRON_PICKAXE;
       if (id.equals("258"))
         return Material.IRON_AXE;
       if (id.equals("261"))
         return Material.BOW;
       if (id.equals("267"))
         return Material.IRON_SWORD;
       if (id.equals("268"))
         return Material.WOOD_SWORD;
       if (id.equals("269"))
         return Material.WOOD_SPADE;
       if (id.equals("270"))
         return Material.WOOD_PICKAXE;
       if (id.equals("271"))
         return Material.WOOD_AXE;
       if (id.equals("272"))
         return Material.STONE_SWORD;
       if (id.equals("273"))
         return Material.STONE_SPADE;
       if (id.equals("274"))
         return Material.STONE_PICKAXE;
       if (id.equals("275"))
         return Material.STONE_AXE;
       if (id.equals("276"))
         return Material.DIAMOND_SWORD;
       if (id.equals("277"))
         return Material.DIAMOND_SPADE;
       if (id.equals("278"))
         return Material.DIAMOND_PICKAXE;
       if (id.equals("279"))
         return Material.DIAMOND_AXE;
       if (id.equals("280"))
         return Material.STICK;
       if (id.equals("283"))
         return Material.GOLD_SWORD;
       if (id.equals("284"))
         return Material.GOLD_SPADE;
       if (id.equals("285"))
         return Material.GOLD_PICKAXE;
       if (id.equals("286"))
         return Material.GOLD_AXE;
       if (id.equals("287"))
         return Material.STRING;
       if (id.equals("290"))
         return Material.WOOD_HOE;
       if (id.equals("291"))
         return Material.STONE_HOE;
       if (id.equals("292"))
         return Material.IRON_HOE;
       if (id.equals("293"))
         return Material.DIAMOND_HOE;
       if (id.equals("294"))
         return Material.GOLD_HOE;
       if (id.equals("298"))
         return Material.LEATHER_HELMET;
       if (id.equals("299"))
         return Material.LEATHER_CHESTPLATE;
       if (id.equals("300"))
         return Material.LEATHER_LEGGINGS;
       if (id.equals("301"))
         return Material.LEATHER_BOOTS;
       if (id.equals("302"))
         return Material.CHAINMAIL_HELMET;
       if (id.equals("303"))
         return Material.CHAINMAIL_CHESTPLATE;
       if (id.equals("304"))
         return Material.CHAINMAIL_LEGGINGS;
       if (id.equals("305"))
         return Material.CHAINMAIL_BOOTS;
       if (id.equals("306"))
         return Material.IRON_HELMET;
       if (id.equals("307"))
         return Material.IRON_CHESTPLATE;
       if (id.equals("308"))
         return Material.IRON_LEGGINGS;
       if (id.equals("309"))
         return Material.IRON_BOOTS;
       if (id.equals("310"))
         return Material.DIAMOND_HELMET;
       if (id.equals("311"))
         return Material.DIAMOND_CHESTPLATE;
       if (id.equals("312"))
         return Material.DIAMOND_LEGGINGS;
       if (id.equals("313"))
         return Material.DIAMOND_BOOTS;
       if (id.equals("314"))
         return Material.GOLD_HELMET;
       if (id.equals("315"))
         return Material.GOLD_CHESTPLATE;
       if (id.equals("316"))
         return Material.GOLD_LEGGINGS;
       if (id.equals("317"))
         return Material.GOLD_BOOTS;
       if (id.equals("359"))
         return Material.SHEARS;
       if (id.equals("369"))
         return Material.BLAZE_ROD;
       if (id.equals("397")) {
         return Material.SKULL_ITEM;
       }
       return Material.getMaterial(customMaterial);
     }
     
 
     return Material.POTATO;
   }
 }
