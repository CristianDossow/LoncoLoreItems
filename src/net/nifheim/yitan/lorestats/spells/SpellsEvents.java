package net.nifheim.yitan.lorestats.spells;

import net.nifheim.yitan.lorestats.GearStats;
import net.nifheim.yitan.lorestats.Main;
import net.nifheim.yitan.lorestats.utils.Util_GetResponse;
import java.io.File;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SpellsEvents implements org.bukkit.event.Listener {

    GearStats gearStats = new GearStats();
    SpellCreator spellCreator = new SpellCreator();
    Util_GetResponse util_GetResponse = new Util_GetResponse();
    private FileConfiguration PlayerDataConfig;

    public boolean hasCooldown(String playerName, int getSeconds) {
        if (getSeconds == 0) {
            return false;
        }
        if (Main.getInstance().spellCooldowns.get(playerName) != null) {
            if (((Long) Main.getInstance().spellCooldowns.get(playerName)).longValue() < System.currentTimeMillis() - getSeconds * 1000) {
                return false;
            }
            long currentTime = System.currentTimeMillis();
            long oldTime = ((Long) Main.getInstance().spellCooldowns.get(playerName)).longValue();

            String remainingCooldown = String.valueOf(Math.abs(currentTime - (oldTime + getSeconds * 1000)));
            String modifiedPlayerName = playerName.split("\\.")[0];

            Player player = Bukkit.getServer().getPlayer(modifiedPlayerName);

            if (remainingCooldown.length() > 7) {
                player.sendMessage("      " + this.util_GetResponse.getResponse("SpellMessages.CastSpell.CooldownRemaining", player, player, new StringBuilder(String.valueOf(String.valueOf(Math.abs(currentTime - (oldTime + getSeconds * 1000))).substring(0, 5))).append(".").append(String.valueOf(Math.abs(currentTime - (oldTime + getSeconds * 1000))).substring(5, 7)).toString(), new StringBuilder(String.valueOf(String.valueOf(Math.abs(currentTime - (oldTime + getSeconds * 1000))).substring(0, 5))).append(".").append(String.valueOf(Math.abs(currentTime - (oldTime + getSeconds * 1000))).substring(5, 7)).toString()));
            } else if (remainingCooldown.length() > 6) {
                player.sendMessage("      " + this.util_GetResponse.getResponse("SpellMessages.CastSpell.CooldownRemaining", player, player, new StringBuilder(String.valueOf(String.valueOf(Math.abs(currentTime - (oldTime + getSeconds * 1000))).substring(0, 4))).append(".").append(String.valueOf(Math.abs(currentTime - (oldTime + getSeconds * 1000))).substring(4, 6)).toString(), new StringBuilder(String.valueOf(String.valueOf(Math.abs(currentTime - (oldTime + getSeconds * 1000))).substring(0, 4))).append(".").append(String.valueOf(Math.abs(currentTime - (oldTime + getSeconds * 1000))).substring(4, 6)).toString()));
            } else if (remainingCooldown.length() > 5) {
                player.sendMessage("      " + this.util_GetResponse.getResponse("SpellMessages.CastSpell.CooldownRemaining", player, player, new StringBuilder(String.valueOf(String.valueOf(Math.abs(currentTime - (oldTime + getSeconds * 1000))).substring(0, 3))).append(".").append(String.valueOf(Math.abs(currentTime - (oldTime + getSeconds * 1000))).substring(3, 5)).toString(), new StringBuilder(String.valueOf(String.valueOf(Math.abs(currentTime - (oldTime + getSeconds * 1000))).substring(0, 3))).append(".").append(String.valueOf(Math.abs(currentTime - (oldTime + getSeconds * 1000))).substring(3, 5)).toString()));
            } else if (remainingCooldown.length() > 4) {
                player.sendMessage("      " + this.util_GetResponse.getResponse("SpellMessages.CastSpell.CooldownRemaining", player, player, new StringBuilder(String.valueOf(String.valueOf(Math.abs(currentTime - (oldTime + getSeconds * 1000))).substring(0, 2))).append(".").append(String.valueOf(Math.abs(currentTime - (oldTime + getSeconds * 1000))).substring(2, 4)).toString(), new StringBuilder(String.valueOf(String.valueOf(Math.abs(currentTime - (oldTime + getSeconds * 1000))).substring(0, 2))).append(".").append(String.valueOf(Math.abs(currentTime - (oldTime + getSeconds * 1000))).substring(2, 4)).toString()));
            } else if (remainingCooldown.length() > 3) {
                player.sendMessage("      " + this.util_GetResponse.getResponse("SpellMessages.CastSpell.CooldownRemaining", player, player, new StringBuilder(String.valueOf(String.valueOf(Math.abs(currentTime - (oldTime + getSeconds * 1000))).substring(0, 1))).append(".").append(String.valueOf(Math.abs(currentTime - (oldTime + getSeconds * 1000))).substring(1, 3)).toString(), new StringBuilder(String.valueOf(String.valueOf(Math.abs(currentTime - (oldTime + getSeconds * 1000))).substring(0, 1))).append(".").append(String.valueOf(Math.abs(currentTime - (oldTime + getSeconds * 1000))).substring(1, 3)).toString()));
            }

            return true;
        }

        return false;
    }

    public void spellSelection(Player player) {
        if (Main.getInstance().util_WorldGuard != null) {
//            if (!ItemLoreStats.plugin.util_WorldGuard.playerInPVPRegion(player)) {
            try {
                if (this.gearStats.getSpellName(player.getInventory().getItemInMainHand()) == null) {
                    return;
                }
                String spellName = this.gearStats.getSpellName(player.getInventory().getItemInMainHand());

                if (spellName == null) {
                    return;
                }
                int spellCooldown = this.spellCreator.getCooldown(spellName);

                if (hasCooldown(player.getName() + "." + spellName.replaceAll(" ", "").toLowerCase(), spellCooldown)) {
                    return;
                }
                this.spellCreator.spellBuilder(spellName, player);
                executeCommandList(player, spellName);
                Main.getInstance().spellCooldowns.put(player.getName() + "." + spellName.replaceAll(" ", "").toLowerCase(), Long.valueOf(System.currentTimeMillis()));

            } catch (Exception e) {
                Main.getInstance().getLogger().log(Level.WARNING, player.getName() + " tried to cast " + this.gearStats.getSpellName(player.getInventory().getItemInMainHand()) + " but ItemLoreStats was unable to find the config for that spell.");
            }
        }
//        } else {
        try {
            if (this.gearStats.getSpellName(player.getInventory().getItemInMainHand()) != null) {
                String spellName = this.gearStats.getSpellName(player.getInventory().getItemInMainHand());

                if (spellName != null) {
                    int spellCooldown = this.spellCreator.getCooldown(spellName);

                    if (!hasCooldown(player.getName() + "." + spellName.replaceAll(" ", "").toLowerCase(), spellCooldown)) {
                        this.spellCreator.spellBuilder(spellName, player);
                        executeCommandList(player, spellName);
                        Main.getInstance().spellCooldowns.put(player.getName() + "." + spellName.replaceAll(" ", "").toLowerCase(), Long.valueOf(System.currentTimeMillis()));
                    }
                }
            }
        } catch (Exception e) {
            Main.getInstance().getLogger().log(Level.WARNING, player.getName() + " tried to cast " + this.gearStats.getSpellName(player.getInventory().getItemInMainHand()) + " but ItemLoreStats was unable to find the config for that spell.");
        }
//        }
//        }
    }

    public void executeCommandList(Player player, String spellFileName) {
        try {
            this.PlayerDataConfig = new org.bukkit.configuration.file.YamlConfiguration();
            this.PlayerDataConfig.load(new File(Main.getInstance().getDataFolder() + File.separator + "SavedSpells" + File.separator + spellFileName + ".yml"));

            if ((this.PlayerDataConfig.getKeys(false).toString().contains("commands"))
                    && (this.PlayerDataConfig.getList("commands") != null)) {
                for (int i = 0; i < this.PlayerDataConfig.getList("commands").size(); i++) {
                    Bukkit.dispatchCommand(player, this.PlayerDataConfig.getList("commands").get(i).toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Main.getInstance().getLogger().log(Level.WARNING, player.getName() + " tried to execute the command list from the " + this.gearStats.getSpellName(player.getInventory().getItemInMainHand()) + " spell.");
        }
    }
}
