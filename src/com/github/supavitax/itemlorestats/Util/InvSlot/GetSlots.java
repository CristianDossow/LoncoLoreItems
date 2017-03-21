 package com.github.supavitax.itemlorestats.Util.InvSlot;
import org.bukkit.Bukkit;
 
 import org.bukkit.entity.Entity;
 import org.bukkit.entity.LivingEntity;
 import org.bukkit.entity.Player;
 import org.bukkit.inventory.ItemStack;
 
 public class GetSlots
 {
   public boolean isArmourSlot(String slot)
   {
     String[] inventorySlots = slot.split(",");
     String[] arrayOfString1;
     int j = (arrayOfString1 = inventorySlots).length; for (int i = 0; i < j; i++) { String theSlot = arrayOfString1[i];
       if ((Integer.parseInt(theSlot.trim()) >= 5) && (Integer.parseInt(theSlot.trim()) <= 8)) {
         return true;
       }
     }
     return false;
   }


   public boolean isOffHandSlot(String slot)
   {
	
     String[] inventorySlots = slot.split(",");
     String[] arrayOfString1;
     int j = (arrayOfString1 = inventorySlots).length; for (int i = 0; i < j; i++) { String theSlot = arrayOfString1[i];
      
                  if ((Integer.parseInt(theSlot.trim()) == 45) ) {
         return true;
       }
     }
     return false;
   }

   
   public boolean isHotbarSlot(String slot) {
     String[] inventorySlots = slot.split(",");
     String[] arrayOfString1;
     int j = (arrayOfString1 = inventorySlots).length; for (int i = 0; i < j; i++) { String theSlot = arrayOfString1[i];
       if ((Integer.parseInt(theSlot.trim()) >= 36) && (Integer.parseInt(theSlot.trim()) <= 44)) {
         return true;
       }
     }
     return false;
   }
   
   public int getRawHeldItemSlot(String slot) {
     String[] inventorySlots = slot.split(",");
     String[] arrayOfString1;
     int j = (arrayOfString1 = inventorySlots).length; for (int i = 0; i < j; i++) { String theSlot = arrayOfString1[i];
       if ((Integer.parseInt(theSlot.trim()) >= 36) && (Integer.parseInt(theSlot.trim()) <= 44)) {
         if (Integer.parseInt(theSlot.trim()) == 36)
           return 0;
         if (Integer.parseInt(theSlot.trim()) == 37)
           return 1;
         if (Integer.parseInt(theSlot.trim()) == 38)
           return 2;
         if (Integer.parseInt(theSlot.trim()) == 39)
           return 3;
         if (Integer.parseInt(theSlot.trim()) == 40)
           return 4;
         if (Integer.parseInt(theSlot.trim()) == 41)
           return 5;
         if (Integer.parseInt(theSlot.trim()) == 42)
           return 6;
         if (Integer.parseInt(theSlot.trim()) == 43)
           return 7;
         if (Integer.parseInt(theSlot.trim()) == 44) {
           return 8;
         }
       }
     }
     
     return -1;
   }
   
   public ItemStack returnItemInMainHand(Entity entity)
   {
     if ((entity instanceof Player)) {
       ItemStack item = ((Player)entity).getInventory().getItemInMainHand();
       
       return item; }
     if ((entity instanceof LivingEntity)) {
       ItemStack item = ((LivingEntity)entity).getEquipment().getItemInMainHand();
       
       return item;
     }
     
     return new ItemStack(org.bukkit.Material.POTATO);
   }
   
 
   public ItemStack returnItemInOffHand(Entity entity)
   {
     if ((entity instanceof Player)) {
       ItemStack item = ((Player)entity).getInventory().getItemInOffHand();
       
       return item; }
     if ((entity instanceof LivingEntity)) {
       ItemStack item = ((LivingEntity)entity).getEquipment().getItemInOffHand();
       
       return item;
     }
     
     return new ItemStack(org.bukkit.Material.POTATO);
   }
   
 
   public ItemStack returnHelmet(Entity entity)
   {
     if ((entity instanceof Player)) {
       ItemStack item = ((Player)entity).getInventory().getHelmet();
       
       return item; }
     if ((entity instanceof LivingEntity)) {
       ItemStack item = ((LivingEntity)entity).getEquipment().getHelmet();
       
       return item;
     }
     
     return new ItemStack(org.bukkit.Material.POTATO);
   }
   
 
   public ItemStack returnChestplate(Entity entity)
   {
     if ((entity instanceof Player)) {
       ItemStack item = ((Player)entity).getInventory().getChestplate();
       
       return item; }
     if ((entity instanceof LivingEntity)) {
       ItemStack item = ((LivingEntity)entity).getEquipment().getChestplate();
       
       return item;
     }
     
     return new ItemStack(org.bukkit.Material.POTATO);
   }
   
 
   public ItemStack returnLeggings(Entity entity)
   {
     if ((entity instanceof Player)) {
       ItemStack item = ((Player)entity).getInventory().getLeggings();
       
       return item; }
     if ((entity instanceof LivingEntity)) {
       ItemStack item = ((LivingEntity)entity).getEquipment().getLeggings();
       
       return item;
     }
     
     return new ItemStack(org.bukkit.Material.POTATO);
   }
   
 
   public ItemStack returnBoots(Entity entity)
   {
     if ((entity instanceof Player)) {
       ItemStack item = ((Player)entity).getInventory().getBoots();
       
       return item; }
     if ((entity instanceof LivingEntity)) {
       ItemStack item = ((LivingEntity)entity).getEquipment().getBoots();
       
       return item;
     }
     
     return new ItemStack(org.bukkit.Material.POTATO);
   }
 }

