/*
 * Copyright 2017 Beelzebu.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.nifheim.beelzebu.utils;

import java.util.Arrays;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Beelzebu
 */
public class ItemSerializer {

    /**
     * Serialize an item to use later.
     *
     * @param is The item stack to serialize, can be deserialized with
     * {@link #deserialize(java.lang.String)}.
     * @return The item serialized with the format:
     * Material;Amount;Durability;Enchanment:level,Enchantment:level;Unbreakable;Name;Lore,Lore,Lore;
     */
    public static String serialize(ItemStack is) {
        String item = is.getType() + ";" + is.getAmount() + ";" + is.getDurability() + ";";
        if (is.getEnchantments().size() > 0) {
            item = is.getEnchantments().keySet().stream().map((ench) -> ench + ":" + is.getEnchantmentLevel(ench) + ",").reduce(item, String::concat);
            item = item.substring(0, item.length() - 1) + ";";
        } else {
            item += null + ";";
        }
        if (is.hasItemMeta()) {
            ItemMeta meta = is.getItemMeta();
            item += meta.isUnbreakable() + ";";
            if (meta.hasDisplayName()) {
                item += meta.getDisplayName() + ";";
            } else {
                item += false + ";";
            }
            if (meta.hasLore()) {
                item = meta.getLore().stream().map((line) -> line + ",").reduce(item, String::concat);
                item = item.substring(0, item.length() - 1) + ";";
            } else {
                item += null + ";";
            }
        } else {
            item += "false;null;null;";
        }
        return item;
    }

    /**
     * Deserialize an item.
     *
     * @param item The serialized item with
     * {@link #serialize(org.bukkit.inventory.ItemStack)}
     * @return The deserialized ItemStack
     */
    public static ItemStack deserialize(String item) {
        ItemStack is;
        String[] meta = item.split(";");
        is = new ItemStack(Material.valueOf(meta[0]), Integer.valueOf(meta[1]), Short.valueOf(meta[2]));
        ItemMeta isMeta = is.getItemMeta();
        if (!meta[3].equals("null")) {
            String[] enchants = meta[3].split(",");
            for (String ench : enchants) {
                String[] en = ench.split(":");
                is.addUnsafeEnchantment(Enchantment.getByName(en[0]), Integer.valueOf(en[1]));
            }
        }
        if (meta[4].equals(true)) {
            isMeta.setUnbreakable(true);
        }
        if (meta[5] != null) {
            isMeta.setDisplayName(meta[5]);
        }
        if (meta[6] != null) {
            String[] lore = meta[6].split(",");
            isMeta.getLore().addAll(Arrays.asList(lore));
        }
        is.setItemMeta(isMeta);
        return is;
    }
}
