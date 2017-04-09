package net.nifheim.yitan.loncoloremagics;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Effect;

public class SpellsList {
	static String languageRegex= "[^A-Za-zñÑáéíóúÁÉÍÓÚ_]";

	public static Spell magicDard(){
		Spell spell = new Spell("Dardo Mágico",1,1,1,Effect.DRAGON_BREATH);
		spell.directDamageAmount=5;
		spell.lore.add("Hechizo básico");
		return spell;
	}
	
	public static List<Spell> getSpellList(){
		List<Spell> list = new ArrayList<>();
		list.add(magicDard());
		return list;
	}
	public static Spell getSpell(String name){
		Spell finalspell = null;
		for(Spell spell : getSpellList()){
			if(spell.name.replaceAll(languageRegex, "").toLowerCase().equals(name.replaceAll(languageRegex,"").toLowerCase()))
				finalspell=spell;
		}
		return finalspell;
	}
}
