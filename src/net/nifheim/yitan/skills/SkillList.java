package net.nifheim.yitan.skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import net.nifheim.yitan.lorestats.Main;
import net.nifheim.yitan.lorestats.PlayerStats;
import net.nifheim.yitan.skills.List.WindStep;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SkillList {

    List<Skill> skills;
    static public HashMap<UUID, HashMap<Skill, Long>> skillCooldowns = new HashMap();
    static public HashMap<Skill, Long> skillCooldownsNode = new HashMap();
    static String languageRegex = "[^A-Za-zñÑáéíóúÁÉÍÓÚ_]";

    public SkillList() {
        super();
        this.skills = new ArrayList<>();
        skills.add(new WindStep());
    }

    public void RunSkills(Player player, ItemStack item) {
        for (Skill skill : skills) {
            for (String lore : item.getItemMeta().getLore()) {
                String loreline = ChatColor.stripColor(lore);
                if (skill.name.replaceAll(languageRegex, "").equals(loreline.replaceAll(languageRegex, ""))) {
                    if (skillCooldowns.containsKey(player.getUniqueId())) {
                        HashMap<Skill, Long> node = skillCooldowns.get(player.getUniqueId());
                        if (node.containsKey(skill)) {
                            if (node.get(skill) + skill.getCd() > System.currentTimeMillis()) {
                                return;
                            }
                        }
                    }
                    PlayerStats ps = Main.getInstance().getPlayerStats(player);
                    skill.Execute(ps);
                    if (skillCooldowns.containsKey(player.getUniqueId())) {
                        skillCooldowns.get(player.getUniqueId()).put(skill, System.currentTimeMillis());
                    } else {
                        skillCooldowns.put(player.getUniqueId(), new HashMap());
                        skillCooldowns.get(player.getUniqueId()).put(skill, System.currentTimeMillis());
                    }
                }
            }
        }
    }

}
