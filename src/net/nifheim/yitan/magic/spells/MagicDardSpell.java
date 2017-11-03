/*
 * Copyright 2017 Cristian Dossow.
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
package net.nifheim.yitan.magic.spells;

import de.slikey.effectlib.util.ParticleEffect;
import net.nifheim.yitan.magic.Spell;
import org.bukkit.ChatColor;
import org.bukkit.Sound;

/**
 *
 * @author YitanTribal
 */
public class MagicDardSpell extends Spell {

    private Spell spell = this;

    public MagicDardSpell() {
        super("Dardo Mágico", 1, 1, 1, ParticleEffect.FIREWORKS_SPARK);
        spell.directDamageAmount = 1;
        spell.particleEffectSphere.add(ParticleEffect.FIREWORKS_SPARK);
        spell.particleEffectSphereradio = 0.3;
        spell.manaCost = 25;
        spell.cooldown = 3000;
        spell.lore.add("Hechizo básico");
        spell.colorName = ChatColor.RED;
        spell.soundOnCast = Sound.ENTITY_FIREWORK_LAUNCH;
        spell.soundOnHit = Sound.ENTITY_PLAYER_BREATH;
    }

}
