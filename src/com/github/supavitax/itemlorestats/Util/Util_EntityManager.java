 package com.github.supavitax.itemlorestats.Util;
 
 import org.bukkit.Material;
 import org.bukkit.entity.Entity;
 import org.bukkit.entity.LivingEntity;
 import org.bukkit.entity.Player;
 import org.bukkit.inventory.ItemStack;
 
 public class Util_EntityManager
 {
   public ItemStack returnItemStackHelmet(Entity entity)
   {
     if ((entity instanceof Player)) {
       ItemStack stack = ((Player)entity).getInventory().getHelmet();
       
       if (stack != null) return stack;
     } else if ((entity instanceof LivingEntity)) {
       ItemStack stack = ((LivingEntity)entity).getEquipment().getHelmet();
       
       if (stack != null) { return stack;
       }
     }
     return new ItemStack(Material.POTATO);
   }
   
   public ItemStack returnItemStackChestplate(Entity entity)
   {
     if ((entity instanceof Player)) {
       ItemStack stack = ((Player)entity).getInventory().getChestplate();
       
       if (stack != null) return stack;
     } else if ((entity instanceof LivingEntity)) {
       ItemStack stack = ((LivingEntity)entity).getEquipment().getChestplate();
       
       if (stack != null) { return stack;
       }
     }
     return new ItemStack(Material.POTATO);
   }
   
   public ItemStack returnItemStackLeggings(Entity entity)
   {
     if ((entity instanceof Player)) {
       ItemStack stack = ((Player)entity).getInventory().getLeggings();
       
       if (stack != null) return stack;
     } else if ((entity instanceof LivingEntity)) {
       ItemStack stack = ((LivingEntity)entity).getEquipment().getLeggings();
       
       if (stack != null) { return stack;
       }
     }
     return new ItemStack(Material.POTATO);
   }
   
   public ItemStack returnItemStackBoots(Entity entity)
   {
     if ((entity instanceof Player)) {
       ItemStack stack = ((Player)entity).getInventory().getBoots();
       
       if (stack != null) return stack;
     } else if ((entity instanceof LivingEntity)) {
       ItemStack stack = ((LivingEntity)entity).getEquipment().getBoots();
       
       if (stack != null) { return stack;
       }
     }
     return new ItemStack(Material.POTATO);
   }
   
   public ItemStack returnItemStackInMainHand(Entity entity)
   {
     if ((entity instanceof Player)) {
       ItemStack stack = ((Player)entity).getInventory().getItemInMainHand();
       
       if (stack != null) return stack;
     } else if ((entity instanceof LivingEntity)) {
       ItemStack stack = ((LivingEntity)entity).getEquipment().getItemInMainHand();
       
       if (stack != null) { return stack;
       }
     }
     return new ItemStack(Material.POTATO);
   }
   
   public ItemStack returnItemStackInOffHand(Entity entity)
   {
     if ((entity instanceof Player)) {
       ItemStack stack = ((Player)entity).getInventory().getItemInOffHand();
       
       if (stack != null) return stack;
     } else if ((entity instanceof LivingEntity)) {
       ItemStack stack = ((LivingEntity)entity).getEquipment().getItemInOffHand();
       
       if (stack != null) { return stack;
       }
     }
     return new ItemStack(Material.POTATO);
   }
   
   public String returnEntityName(Entity entity) {
     if ((entity instanceof Player)) {
       String name = ((Player)entity).getName();
       
       return name; }
     if ((entity instanceof LivingEntity)) {
       if (((LivingEntity)entity).getCustomName() != null) {
         String name = ((LivingEntity)entity).getCustomName();
         
         return name;
       }
       String name = ((LivingEntity)entity).getType().toString().substring(0, 1) + ((LivingEntity)entity).getType().toString().substring(1, ((LivingEntity)entity).getType().toString().length()).toLowerCase().replace("_", " ");
       
       return name;
     }
     
 
     return null;
   }
   
   public double returnEntityCurrentHealth(Entity entity)
   {
     if ((entity instanceof Player)) {
       double health = ((Player)entity).getHealth();
       
       return health; }
     if ((entity instanceof LivingEntity)) {
       double health = ((LivingEntity)entity).getHealth();
       
       return health;
     }
     
     return 0.0D;
   }
   
 
   public double returnEntityMaxHealth(Entity entity)
   {
     if ((entity instanceof Player)) {
       double health = ((Player)entity).getMaxHealth();
       
       return health; }
     if ((entity instanceof LivingEntity)) {
       double health = ((LivingEntity)entity).getMaxHealth();
       
       return health;
     }
     
     return 0.0D;
   }
   
   public void setEntityCurrentHealth(Entity entity, double health)
   {
     double newHealth = health;
     
     if ((entity instanceof Player)) {
       ((Player)entity).setHealth(newHealth);
     } else if ((entity instanceof LivingEntity)) {
       ((LivingEntity)entity).setHealth(newHealth);
     }
   }
   
   public void setEntityMaxHealth(Entity entity, double health)
   {
     double newHealth = health;
     
     if ((entity instanceof Player)) {
       ((Player)entity).setMaxHealth(newHealth);
     } else if ((entity instanceof LivingEntity)) {
       ((LivingEntity)entity).setMaxHealth(newHealth);
     }
   }
 }
