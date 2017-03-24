package net.nifheim.yitan.itemlorestats.Util;

import net.nifheim.yitan.itemlorestats.ItemLoreStats;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.entity.Entity;

public class Util_Citizens {

    ItemLoreStats main;

    public Util_Citizens(ItemLoreStats instance) {
        this.main = instance;
    }

    public boolean checkVulnerability(Entity entity) {
        NPCRegistry registry = CitizensAPI.getNPCRegistry();
        NPC npc = registry.getNPC(entity);

        if (entity != null) {
            if (npc.data().has("protected")) {
                return true;
            }
            return false;
        }

        return false;
    }
}
