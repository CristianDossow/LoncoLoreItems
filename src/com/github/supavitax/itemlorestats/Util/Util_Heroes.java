 package com.github.supavitax.itemlorestats.Util;
 
 import com.github.supavitax.itemlorestats.ItemLoreStats;
 import com.herocraftonline.heroes.Heroes;
 import com.herocraftonline.heroes.characters.CharacterManager;
 import com.herocraftonline.heroes.characters.Hero;
 import org.bukkit.entity.Player;
 
 public class Util_Heroes
 {
   com.github.supavitax.itemlorestats.GearStats gearStats = new com.github.supavitax.itemlorestats.GearStats();
   ItemLoreStats main;
   
   public Util_Heroes(ItemLoreStats instance)
   {
     this.main = instance;
   }
   
   public int getHeroBaseHealth(Player player) {
     int baseMax = 20;
     
     Hero hero = Heroes.getInstance().getCharacterManager().getHero(player);
     baseMax = hero.getHeroClass().getBaseMaxHealth();
     
     return baseMax;
   }
   
   public double getHeroHealthPerLevel(Player player) {
     double healthPerLevel = 0.0D;
     
     Hero hero = Heroes.getInstance().getCharacterManager().getHero(player);
     healthPerLevel = hero.getHeroClass().getMaxHealthPerLevel();
     
     return healthPerLevel;
   }
   
   public int getHeroLevel(Player player) {
     int level = 0;
     
     Hero hero = Heroes.getInstance().getCharacterManager().getHero(player);
     level = hero.getLevel(hero.getHeroClass());
     
     return level;
   }
   
   public int getHeroExperience(Player player) {
     int experience = 0;
     
     Hero hero = Heroes.getInstance().getCharacterManager().getHero(player);
     experience = (int)hero.getExperience(hero.getHeroClass());
     
     return experience;
   }
   
   public int getHeroMaxMana(Player player) {
     int maxMana = 0;
     
     Hero hero = Heroes.getInstance().getCharacterManager().getHero(player);
     maxMana = hero.getMaxMana();
     
     return maxMana;
   }
   
   public void addHeroMaxManaStat(Player player, int manaStat)
   {
     Hero hero = Heroes.getInstance().getCharacterManager().getHero(player);
     
     hero.addMaxMana("ILS", manaStat);
   }
   
   public void removeHeroMaxManaStat(Player player)
   {
     Hero hero = Heroes.getInstance().getCharacterManager().getHero(player);
     
     hero.removeMaxMana("ILS");
   }
 }

