package net.nifheim.yitan.loncoloremagics;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Effect;

public class SpellsList {

	static Spell magicDard(){
		Spell spell = new Spell("Dardo Mágico",1,1,1,Effect.DRAGON_BREATH);
		spell.directDamageAmount=5;
		return spell;
	}
	
	static List<Spell> getSpellList(){
		List<Spell> list = new ArrayList<>();
		list.add(magicDard());
		return list;
	}
}
