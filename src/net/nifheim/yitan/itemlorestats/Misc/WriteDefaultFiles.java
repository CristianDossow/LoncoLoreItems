package net.nifheim.yitan.itemlorestats.Misc;

import net.nifheim.yitan.itemlorestats.Main;
import java.io.File;
import java.text.DecimalFormat;
import java.util.logging.Level;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class WriteDefaultFiles implements org.bukkit.event.Listener {

    private File PlayerDataFile;
    private FileConfiguration PlayerDataConfig;
    DecimalFormat format = new DecimalFormat("0.00");

//    private String formatVersion(double version) {
//        Locale forceLocale = new Locale("en", "UK");
//        String decimalPattern = "0.00";
//
//        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(forceLocale);
//        decimalFormat.applyPattern(decimalPattern);
//
//        String format = decimalFormat.format(version);
//
//        return format;
//    }
    public void checkExistence() {
        if (!new File(Main.plugin.getDataFolder() + File.separator + "SavedItems").exists()) {
            new File(Main.plugin.getDataFolder() + File.separator + "SavedItems").mkdirs();
        }
        if (!new File(Main.plugin.getDataFolder() + File.separator + "SavedMobs").exists()) {
            new File(Main.plugin.getDataFolder() + File.separator + "SavedMobs").mkdirs();
        }
        if (!new File(Main.plugin.getDataFolder() + File.separator + "SavedSpells").exists()) {
            new File(Main.plugin.getDataFolder() + File.separator + "SavedSpells").mkdirs();
        }
        if (!new File(Main.plugin.getDataFolder() + File.separator + "PlayerData").exists()) {
            new File(Main.plugin.getDataFolder() + File.separator + "PlayerData").mkdirs();
        }
        if (!new File(Main.plugin.getDataFolder() + File.separator + "language-en.yml").exists()) {
            writeLanguageEN();
        }
        new File(Main.plugin.getDataFolder() + File.separator + "language-de.yml").exists();

        new File(Main.plugin.getDataFolder() + File.separator + "language-pl.yml").exists();

        if (!new File(Main.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Fireball.yml").exists()) {
            writeDefaultSpell("Fireball", "SmallFireball", 2, "Mobspawner_Flames", 15, 0, 0, 0, 5, 2, 2);
        }
        if (!new File(Main.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Fireball I.yml").exists()) {
            writeDefaultSpell("Fireball I", "SmallFireball", 2, "Mobspawner_Flames", 15, 0, 0, 0, 8, 5, 2);
        }
        if (!new File(Main.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Fireball II.yml").exists()) {
            writeDefaultSpell("Fireball II", "SmallFireball", 2, "Mobspawner_Flames", 15, 0, 0, 0, 11, 8, 3);
        }
        if (!new File(Main.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Fireball III.yml").exists()) {
            writeDefaultSpell("Fireball III", "SmallFireball", 2, "Mobspawner_Flames", 15, 0, 0, 0, 14, 10, 3);
        }

        if (!new File(Main.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Frostbolt.yml").exists()) {
            writeDefaultSpell("Frostbolt", "Snowball", 2, "Potion_Break", 15, 0, 0, 0, 5, 2, 2);
        }
        if (!new File(Main.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Frostbolt I.yml").exists()) {
            writeDefaultSpell("Frostbolt I", "Snowball", 2, "Potion_Break", 15, 0, 0, 0, 8, 5, 2);
        }
        if (!new File(Main.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Frostbolt II.yml").exists()) {
            writeDefaultSpell("Frostbolt II", "Snowball", 2, "Potion_Break", 15, 0, 0, 0, 11, 8, 3);
        }
        if (!new File(Main.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Frostbolt III.yml").exists()) {
            writeDefaultSpell("Frostbolt III", "Snowball", 2, "Potion_Break", 15, 0, 0, 0, 14, 10, 3);
        }

        if (!new File(Main.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Minor Heal.yml").exists()) {
            writeDefaultSpell("Minor Heal", "Snowball", 3, "Ender_Signal", 4, 4, 0, 0, 0, 0, 0);
        }
        if (!new File(Main.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Minor Heal I.yml").exists()) {
            writeDefaultSpell("Minor Heal I", "Snowball", 3, "Ender_Signal", 4, 5, 0, 0, 0, 0, 0);
        }
        if (!new File(Main.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Minor Heal II.yml").exists()) {
            writeDefaultSpell("Minor Heal II", "Snowball", 3, "Ender_Signal", 4, 6, 0, 0, 0, 0, 0);
        }
        if (!new File(Main.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Minor Heal III.yml").exists()) {
            writeDefaultSpell("Minor Heal III", "Snowball", 3, "Ender_Signal", 4, 7, 0, 0, 0, 0, 0);
        }

        if (!new File(Main.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Major Heal.yml").exists()) {
            writeDefaultSpell("Major Heal", "Snowball", 3, "Ender_Signal", 8, 10, 0, 0, 0, 0, 0);
        }
        if (!new File(Main.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Major Heal I.yml").exists()) {
            writeDefaultSpell("Major Heal I", "Snowball", 3, "Ender_Signal", 8, 11, 0, 0, 0, 0, 0);
        }
        if (!new File(Main.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Major Heal II.yml").exists()) {
            writeDefaultSpell("Major Heal II", "Snowball", 3, "Ender_Signal", 8, 12, 0, 0, 0, 0, 0);
        }
        if (!new File(Main.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Major Heal III.yml").exists()) {
            writeDefaultSpell("Major Heal III", "Snowball", 3, "Ender_Signal", 8, 13, 0, 0, 0, 0, 0);
        }

        if (!new File(Main.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Minor AOE Heal.yml").exists()) {
            writeDefaultSpell("Minor AOE Heal", "Snowball", 8, "Ender_Signal", 8, 8, 8, 2, 0, 0, 0);
        }
        if (!new File(Main.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Minor AOE Heal I.yml").exists()) {
            writeDefaultSpell("Minor AOE Heal I", "Snowball", 8, "Ender_Signal", 8, 9, 9, 2, 0, 0, 0);
        }
        if (!new File(Main.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Minor AOE Heal II.yml").exists()) {
            writeDefaultSpell("Minor AOE Heal II", "Snowball", 8, "Ender_Signal", 8, 10, 10, 3, 0, 0, 0);
        }
        if (!new File(Main.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Minor AOE Heal III.yml").exists()) {
            writeDefaultSpell("Minor AOE Heal III", "Snowball", 8, "Ender_Signal", 8, 11, 11, 3, 0, 0, 0);
        }

        if (!new File(Main.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Major AOE Heal.yml").exists()) {
            writeDefaultSpell("Major AOE Heal", "Snowball", 8, "Ender_Signal", 16, 14, 14, 3, 0, 0, 0);
        }
        if (!new File(Main.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Major AOE Heal I.yml").exists()) {
            writeDefaultSpell("Major AOE Heal I", "Snowball", 8, "Ender_Signal", 16, 15, 15, 3, 0, 0, 0);
        }
        if (!new File(Main.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Major AOE Heal II.yml").exists()) {
            writeDefaultSpell("Major AOE Heal II", "Snowball", 8, "Ender_Signal", 16, 16, 16, 4, 0, 0, 0);
        }
        if (!new File(Main.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Major AOE Heal III.yml").exists()) {
            writeDefaultSpell("Major AOE Heal III", "Snowball", 8, "Ender_Signal", 16, 17, 17, 4, 0, 0, 0);
        }

        if (!new File(Main.plugin.getDataFolder() + File.separator + "SavedItems" + File.separator + "SetBonuses.yml").exists()) {
            writeSetBonuses();
        }
    }

    public void writeDefaultSpell(String fileName, String projType, int projVelocity, String projHitEff, int cooldown, int dHA, int aHA, int aHR, int dDA, int aDA, int aDR) {
        try {
            this.PlayerDataConfig = new YamlConfiguration();
            this.PlayerDataFile = new File(Main.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + fileName + ".yml");

            this.PlayerDataConfig.set("projectile-type", projType);
            this.PlayerDataConfig.set("projectile-velocity", (projVelocity));
            this.PlayerDataConfig.set("projectile-hit-effect", projHitEff);
            this.PlayerDataConfig.set("cooldown", (cooldown));
            this.PlayerDataConfig.set("heal.direct-heal-amount", (dHA));
            this.PlayerDataConfig.set("heal.aoe-heal-amount", (aHA));
            this.PlayerDataConfig.set("heal.aoe-heal-range", (aHR));
            this.PlayerDataConfig.set("damage.direct-damage-amount", (dDA));
            this.PlayerDataConfig.set("damage.aoe-damage-amount", (aDA));
            this.PlayerDataConfig.set("damage.aoe-damage-range", (aDR));

            this.PlayerDataConfig.save(this.PlayerDataFile);
        } catch (Exception e) {
            Main.plugin.getLogger().log(Level.WARNING, "Failed to save the default spell config for {0}.", fileName);
        }
    }

    public void writeSetBonuses() {
        try {
            this.PlayerDataConfig = new YamlConfiguration();
            this.PlayerDataFile = new File(Main.plugin.getDataFolder() + File.separator + "SavedItems" + File.separator + "SetBonuses.yml");

            this.PlayerDataConfig.set("Sets", null);

            this.PlayerDataConfig.save(this.PlayerDataFile);
        } catch (Exception e) {
            System.out.println("*********** Failed to save default SetBonuses file! ***********");
        }
    }

    public void writeLanguageEN() {
        try {
            this.PlayerDataConfig = new YamlConfiguration();
            this.PlayerDataFile = new File(Main.plugin.getDataFolder() + File.separator + "language-en.yml");

            this.PlayerDataConfig.set("ErrorMessages", null);
            this.PlayerDataConfig.set("ErrorMessages.PermissionDeniedError", "&cYou do not have permission to perform that command.");
            this.PlayerDataConfig.set("ErrorMessages.IngameOnlyError", "You can only run that command in-game!");
            this.PlayerDataConfig.set("ErrorMessages.DoesntExistError", "&4{value1} &cdoesn't exist!");
            this.PlayerDataConfig.set("ErrorMessages.PlayerNotOnlineError", "&4{value1} &cis not online!");
            this.PlayerDataConfig.set("ErrorMessages.NotEnoughSpaceError", "&4{value1} &cdoes not have enough space in their inventory.");
            this.PlayerDataConfig.set("ErrorMessages.IncludeItemNameError", "&cYou need to include an item name!. For example, /ils name &4Hand of God");
            this.PlayerDataConfig.set("ErrorMessages.EnterPlayerNameError", "&cYou need to enter a players name!");
            this.PlayerDataConfig.set("ErrorMessages.ItemAlreadyExistsError", "&cThat item already exists. Please choose a different name for the item.");
            this.PlayerDataConfig.set("ErrorMessages.NullItemInHandError", "&cYou need to be holding an item to use that command.");
            this.PlayerDataConfig.set("ErrorMessages.NoLoreError", "&cThat item doesn't contain any lore.");
            this.PlayerDataConfig.set("ErrorMessages.EnterTypeError", "&cYou need to enter a type &4Armour&c, &4Hand &cor &4All.");
            this.PlayerDataConfig.set("ErrorMessages.ShiftClickDisabled", "&cShift-clicking armour is currently disabled.");
            this.PlayerDataConfig.set("ErrorMessages.InventoryFull", "&4Dropping the item as your inventory is full.");
            this.PlayerDataConfig.set("ErrorMessages.AddedToConfig", "&2{value1} &ahas been added to the config.");

            this.PlayerDataConfig.set("CustomItemMessages", null);
            this.PlayerDataConfig.set("CustomItemMessages.HoldCustomItem", "&cYou need to hold the item you want to add to the config.");
            this.PlayerDataConfig.set("CustomItemMessages.CustomEquipmentType", "&cYou need to enter the equipment type of the item. Either &4tool &cor &4armour&c.");
            this.PlayerDataConfig.set("CustomItemMessages.CustomItemType", "&cYou need to enter the type of item. E.g &4Diamond Sword &cor &4Gold Chestplate&c.");
            this.PlayerDataConfig.set("CustomItemMessages.CustomArmourType", "&cYou need to enter the type of Armour as shown; &4helmet&c, &4chest&c, &4legs&c, &4boots&c.");

            this.PlayerDataConfig.set("ClassRequirementMessages", null);
            this.PlayerDataConfig.set("ClassRequirementMessages.NotRequiredClass", "&cYou are not the required class to equip that item.");
            this.PlayerDataConfig.set("ClassRequirementMessages.NotRequiredClassForItemInHand", "&cYou are not the required class to hold that item.");
            this.PlayerDataConfig.set("ClassRequirementMessages.InventoryFullOnPickup", "&4Your inventory is full and you're not the required class to hold that item.");

            this.PlayerDataConfig.set("LevelRequirementMessages", null);
            this.PlayerDataConfig.set("LevelRequirementMessages.LevelTooLow", "&cYou are not the required level to equip that item.");
            this.PlayerDataConfig.set("LevelRequirementMessages.LevelTooLowForItemInHand", "&cYou are not the required level to hold that item.");
            this.PlayerDataConfig.set("LevelRequirementMessages.InventoryFullOnPickup", "&4Your inventory is full and you're not the required level to hold that item.");

            this.PlayerDataConfig.set("SoulboundMessages", null);
            this.PlayerDataConfig.set("SoulboundMessages.BoundToSomeoneElse", "&cThis item is bound to someone else and you're unable to equip it.");
            this.PlayerDataConfig.set("SoulboundMessages.BoundToSomeoneElseForItemInHand", "&cThis item is bound to someone else and you're unable to hold it.");
            this.PlayerDataConfig.set("SoulboundMessages.InventoryFullOnPickup", "&4Your inventory is full and that item is bound to someone else.");

            this.PlayerDataConfig.set("DamageMessages", null);
            this.PlayerDataConfig.set("DamageMessages.DamageDone", "      &dYou hit &r{defender} &dfor &6{value2} &ddamage.");
            this.PlayerDataConfig.set("DamageMessages.DamageDoneWithoutVowel", "      &dYou hit a &r{value1} &dfor &6{value2} &ddamage.");
            this.PlayerDataConfig.set("DamageMessages.DamageDoneWithVowel", "      &dYou hit an &r{value1} &dfor &6{value2} &ddamage.");
            this.PlayerDataConfig.set("DamageMessages.DamageTaken", "      &r{attacker} &dhit you for &6{value2} &ddamage.");
            this.PlayerDataConfig.set("DamageMessages.CriticalStrikeSuccess", "&aCritical Strike!");
            this.PlayerDataConfig.set("DamageMessages.EnemyCriticalStrikeSuccess", "&4{attacker} &ccrit you!");
            this.PlayerDataConfig.set("DamageMessages.DodgeSuccess", "&aDodge!");
            this.PlayerDataConfig.set("DamageMessages.EnemyDodgeSuccess", "&4{defender} &cdodged your attack!");
            this.PlayerDataConfig.set("DamageMessages.BlockSuccess", "&aBlocked!");
            this.PlayerDataConfig.set("DamageMessages.EnemyBlockSuccess", "&4{defender} &cblocked your attack!");
            this.PlayerDataConfig.set("DamageMessages.LifeStealSuccess", "&2{value1} &ahealth stolen from &2{defender}&a!");
            this.PlayerDataConfig.set("DamageMessages.EnemyLifeStealSuccess", "&4{attacker} &cstole &4{value1} &cof your health!");
            this.PlayerDataConfig.set("DamageMessages.ReflectSuccess", "&aReflected!");
            this.PlayerDataConfig.set("DamageMessages.EnemyReflectSuccess", "&4{defender} &creflected your attack!");
            this.PlayerDataConfig.set("DamageMessages.FireSuccess", "&2{defender} &aset alight!");
            this.PlayerDataConfig.set("DamageMessages.EnemyFireSuccess", "&4{attacker} &cset you on fire!");
            this.PlayerDataConfig.set("DamageMessages.IceSuccess", "&2{defender} &aslowed!");
            this.PlayerDataConfig.set("DamageMessages.EnemyIceSuccess", "&4{attacker} &cslowed you!");
            this.PlayerDataConfig.set("DamageMessages.PoisonSuccess", "&2{defender} &apoisoned!");
            this.PlayerDataConfig.set("DamageMessages.EnemyPoisonSuccess", "&4{attacker} &cpoisoned you!");
            this.PlayerDataConfig.set("DamageMessages.WitherSuccess", "&2{defender} &awithered!");
            this.PlayerDataConfig.set("DamageMessages.EnemyWitherSuccess", "&4{attacker} &cwithered you!");
            this.PlayerDataConfig.set("DamageMessages.HarmSuccess", "&2{defender} &aharmed!");
            this.PlayerDataConfig.set("DamageMessages.EnemyHarmSuccess", "&4{attacker} &charmed you!");
            this.PlayerDataConfig.set("DamageMessages.BlindSuccess", "&2{defender} &ablinded!");
            this.PlayerDataConfig.set("DamageMessages.EnemyBlindSuccess", "&4{attacker} &cblinded you!");

            this.PlayerDataConfig.set("SpellMessages", null);
            this.PlayerDataConfig.set("SpellMessages.CastSpell.ItemInHand", "&eRight-click to cast");
            this.PlayerDataConfig.set("SpellMessages.CastSpell.CooldownRemaining", "&6{value1} &dseconds remaining.");
            this.PlayerDataConfig.set("SpellMessages.CastSpell.Heal", "&2{attacker} &ahealed you for &2{value1} &aHealth.");
            this.PlayerDataConfig.set("SpellMessages.CastSpell.Damaged", "      &dYou hit &r{defender} &dfor &6{value2} &ddamage.");

            this.PlayerDataConfig.set("RepairMessages", null);
            this.PlayerDataConfig.set("RepairMessages.RepairSuccessfulMaterial", "&aSuccessfully repaired &2{value1}&c.");
            this.PlayerDataConfig.set("RepairMessages.RepairSuccessfulCurrency", "&aSuccessfully repaired &2{value1} &afor &2{value2} Dollars&a.");
            this.PlayerDataConfig.set("RepairMessages.DoesntNeedRepair", "&4{value1} &cdoesn't need repairing.");
            this.PlayerDataConfig.set("RepairMessages.NotEnoughMoney", "&cYou need &4{value2} Dollars &cto repair &4{value1}&c.");
            this.PlayerDataConfig.set("RepairMessages.NotEnoughLeather", "&cNot enough Leather to repair &4{value1}&c.");
            this.PlayerDataConfig.set("RepairMessages.NotEnoughWood", "&cNot enough Wood to repair &4{value1}&c.");
            this.PlayerDataConfig.set("RepairMessages.NotEnoughCobblestone", "&cNot enough Cobblestone to repair &4{value1}&c.");
            this.PlayerDataConfig.set("RepairMessages.NotEnoughIron", "&cNot enough Iron Ingots to repair &4{value1}&c.");
            this.PlayerDataConfig.set("RepairMessages.NotEnoughGold", "&cNot enough Gold Ingots to repair &4{value1}&c.");
            this.PlayerDataConfig.set("RepairMessages.NotEnoughDiamond", "&cNot enough Diamonds to repair &4{value1}&c.");
            this.PlayerDataConfig.set("RepairMessages.NotEnoughSticks", "&cNot enough Sticks to repair &4{value1}&c.");
            this.PlayerDataConfig.set("RepairMessages.NotEnoughFlint", "&cNot enough Flint to repair &4{value1}&c.");
            this.PlayerDataConfig.set("RepairMessages.NotEnoughWood", "&cNot enough Wooden Planks to repair &4{value1}&c.");
            this.PlayerDataConfig.set("RepairMessages.NotEnoughString", "&cNot enough String to repair &4{value1}&c.");
            this.PlayerDataConfig.set("RepairMessages.NotEnoughFishingRod", "&cYou need a Fishing Rod to repair &4{value1}&c.");
            this.PlayerDataConfig.set("RepairMessages.NotEnoughCarrots", "&cYou need a Carrot to repair &4{value1}&c.");

            this.PlayerDataConfig.set("UpgradeMessages", null);
            this.PlayerDataConfig.set("UpgradeMessages.UpgradeSuccessful", "&2{value1} &ahas been upgraded.");
            this.PlayerDataConfig.set("UpgradeMessages.UpgradeCapReached", "&2{value1} &ahas reached the cap and can no longer be upgraded.");

            this.PlayerDataConfig.set("SellMessages", null);
            this.PlayerDataConfig.set("SellMessages.SellSuccessful", "&aYou sold &2{value1} &afor &2{value2} Dollars&a.");
            this.PlayerDataConfig.set("SellMessages.UnableToSell", "&cYou are unable to sell &2{value1}&c.");
            this.PlayerDataConfig.set("SellMessages.NoItemInHand", "&cYour hand is empty.");

            this.PlayerDataConfig.set("ItemType", null);
            this.PlayerDataConfig.set("ItemType.Tool.Sword", "Sword");
            this.PlayerDataConfig.set("ItemType.Tool.Pickaxe", "Pickaxe");
            this.PlayerDataConfig.set("ItemType.Tool.Axe", "Axe");
            this.PlayerDataConfig.set("ItemType.Tool.Spade", "Spade");
            this.PlayerDataConfig.set("ItemType.Tool.Hoe", "Hoe");
            this.PlayerDataConfig.set("ItemType.Tool.Bow", "Bow");
            this.PlayerDataConfig.set("ItemType.Tool.Stick", "Stick");
            this.PlayerDataConfig.set("ItemType.Tool.Blaze_Rod", "Blaze Rod");
            this.PlayerDataConfig.set("ItemType.Tool.String", "String");
            this.PlayerDataConfig.set("ItemType.Armour.Skull_Helmet", "Helmet");
            this.PlayerDataConfig.set("ItemType.Armour.Helmet", "Helmet");
            this.PlayerDataConfig.set("ItemType.Armour.Chestplate", "Chestplate");
            this.PlayerDataConfig.set("ItemType.Armour.Leggings", "Leggings");
            this.PlayerDataConfig.set("ItemType.Armour.Boots", "Boots");

            this.PlayerDataConfig.set("Item", null);
            this.PlayerDataConfig.set("Item.Looted", "&bYou looted &6{value1}&b!");

            this.PlayerDataConfig.save(this.PlayerDataFile);
        } catch (Exception e) {
            System.out.println("*********** Failed to save default language-EN file! ***********");
        }
    }
}
