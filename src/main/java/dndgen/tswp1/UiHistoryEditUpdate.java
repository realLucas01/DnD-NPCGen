package dndgen.tswp1;

import dndgen.database.DBMS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.security.SecureRandom;
import java.util.*;

/**
 * The type Ui history edit update.
 */
/*
  *   0: ID //muss auch wieder zurückgegeben werden
        1: irgendwas von Lucas

        2: name ;
        3: level ;
        4: genClass ;
        5: race ;
        6: job ;
        7: armorClass ;
        8: healthPoints ;
        9: movementSpeedList ;
        10: languages ;
        11: abilityScores ;
        12: savingThrows ;
        13: armor ;
        14: weaponList ;
        15: skillList ;
        16: senses ;
        17: spellList ;
        18: spellAbility ;
        19: description ;
        20: actionList ;
        21: bonusActionList ;
        22: profBonus ;

        23: abilityModifier ;
        24: gender ;
        25: age ;
        26: alignment ;
        27: deity ;
        28: bonusTextList ;

        length - 1: Datetime
 */
public class UiHistoryEditUpdate {
    /**
     * The App ctx.
     */
    /* @author Lucas
     *
     * Aufrufen der GENAUEN DBMS bzw. SpringApplication die gestartet wurde, jede
     * neu-konstruktion von DBMS wäre sonst leer.
     */
    final ApplicationContext appCtx = DBMS.getApplicationContext();
    /**
     * The Dbms.
     */
    final DBMS dbms = appCtx.getBean(DBMS.class);
    /**
     * The Rnd numb.
     */
    Random rndNumb = new SecureRandom();
    /**
     * The Rand gen.
     */
    final RandomGenerator randGen = new RandomGenerator();
    /**
     * The Rand npc.
     */
	@Autowired
    final RandomNpc randNpc = new RandomNpc(dbms);

    /**
     * Update history edit npc class.
     *
     * @param changes the changes
     * @param oldNpc  the old npc
     * @return the npc class
     */
	/*
    changes = {race, class, alignment}
     */
    public NpcClass updateHistoryEdit (List<String> changes, NpcClass oldNpc ){
        new NpcClass();
        NpcClass newNpc;
		newNpc = oldNpc;
        String newRace = changes.get(0);
        String newCharClass = changes.get(1);
        String newAlignment = changes.get(2);
		String newName = changes.get(3);

		newNpc.setName(newName);

        //race change
        if(!Objects.equals(newRace, oldNpc.getRace())){
        	raceChanges(oldNpc,newNpc,newRace);
        }
		//class change
		if (!Objects.equals(newCharClass, oldNpc.getGenClass())) {
            // class geändert
            // Änderungen: HP, (profWeapon,profArmor(+AC): nur Löschen wenn nicht mehr geht.), Skills (überprüfen welche bleiben können, welche nicht), AbilityScoreDistribution(von Luca holen), Spells(nuke), features(class features ersetzen, siehe funktion zum Überprüfen welche eligible sind)
        	newNpc.setGenClass(newCharClass);
			classChange(newNpc,newCharClass, oldNpc);

        }
		//Alignment Change
        if ((!Objects.equals(newAlignment, oldNpc.getAlignment()))){
			newNpc.setAlignment(newAlignment);

			alignmentChanges(newAlignment, newNpc, oldNpc);
        }
        //geänderten wird dem userinterface zurückgegeben
        return newNpc;
    }

	private void raceChanges(NpcClass oldNpc, NpcClass newNpc, String newRace){
		//race geändert
		//Änderungen	: RaceAbilityScore addition, Features, Skills, Language, Speed, Description:(Age, height, size), Senses
		List<Integer> abilityScore =oldNpc.getAbilityScores();
		newNpc.setRace(newRace);
		abilityScore = removeRaceAbilityAddition(oldNpc.getRace(),  abilityScore);
		abilityScore = randNpc.addRaceModifierToAbilityScore(newRace, abilityScore);
		newNpc.setAbilityScores(abilityScore);

		newNpc.setBonusTextList(randNpc.determineFeatures(newRace,"bonus text")); //Features
		newNpc.setActionList(randNpc.determineFeatures(newRace,"action")); //Features
		newNpc.setBonusActionList(randNpc.determineFeatures(newRace,"bonus action")); //Features
		newNpc.setMovementSpeedList(randNpc.determineMovementSpeed(newRace));
		newNpc.setSenses(randGen.getVision(newRace) + ", Passive Perception " + randGen.passivePerception(newNpc.getAbilityModifier(), newNpc.getSkillList(), newNpc.getProfBonus()));
		//Description
		List<String> newDesc = oldNpc.getDescription();
		newDesc.set(newDesc.size()-2,randGen.randSize(newRace)); // size
		newDesc.set(newDesc.size()-1,randGen.randSize(newRace)); // height
		newNpc.setDescription(newDesc);
		newNpc.setAge(randGen.randAge(newRace));//age

		//Languages
		List<String> raceLanguage = randGen.bigStringToDividedString(dbms.getRowWithPref("race", newRace).get(10));
		raceLanguage.removeLast();
		List<String> oldRaceLanguage = randGen.bigStringToDividedString(dbms.getRowWithPref("race", oldNpc.getRace()).get(10));
		oldRaceLanguage.removeLast();
		List<String> oldNpcRaceLanguage = oldNpc.getLanguages();
		oldNpcRaceLanguage.removeAll(oldRaceLanguage);
		raceLanguage.addAll(oldNpcRaceLanguage);
		newNpc.setLanguages(raceLanguage);

		if (!newNpc.getGenClass().equals("Commoner")) {     // Fall Commoner?
			newNpc.setSkillList(randNpc.addAbilityScoreToSkillList(newNpc.getAbilityModifier(),randNpc.determineSkillList(newRace, newNpc.getJob(), newNpc.getGenClass()), newNpc.getProfBonus()));
		} else {
			newNpc.setSkillList(Collections.singletonList("none"));
		}
	}

	private void alignmentChanges(String newAlignment, NpcClass newNpc, NpcClass oldNpc){
		//Deity
		String deity = oldNpc.getDeity();
		if(!Objects.equals(dbms.getRowWithPref("deity", deity).get(2), newAlignment)){
			newNpc.setDeity("---");
		}
	}

    private void classChange(NpcClass newNpc,String newClass,NpcClass currentNpc){
    	//Hp änderung
    	newNpc.setHealthPoints(randNpc.calculateHP(newClass, newNpc.getLevel(), newNpc.getAbilityModifier()));
    	
    	//AbilitySc
		abilityScChanges(newNpc, newClass);
    	//Armor+AC
    	armorChanges(newNpc, newClass, currentNpc);
    	//Weapons
    	weaponChanges(currentNpc, newNpc, newClass);
		//spells
		spellListChanges(newNpc,currentNpc);

    	//Skills
    	skillChanges(currentNpc, newClass, newNpc);
    	//Features
    }

	private void abilityScChanges(NpcClass newNpc, String newClass){
		List<Integer> abilityScore = newNpc.getAbilityScores();
		abilityScore = removeRaceAbilityAddition(newNpc.getRace(), abilityScore);//Race, ABiliSc
		abilityScore = randNpc.distributeAbilityScore(abilityScore,newClass);
		abilityScore = randNpc.addRaceModifierToAbilityScore(newNpc.getRace(),abilityScore);
		newNpc.setAbilityScores(abilityScore);
	}

	private void armorChanges(NpcClass newNpc, String newClass, NpcClass currentNpc ) {
		List<String> newArmorPossible = randGen.figureOutProfList("armor", newClass, newNpc.getRace());
		String oldArmor = currentNpc.getArmor();
		boolean hasShield = false;
		if(oldArmor.contains("Shield")) {
			oldArmor =oldArmor.substring(0,oldArmor.indexOf("+")-1); // "+ shield " entfernen
			hasShield = true;
		}
		String dbReturn = dbms.getRowWithPref("armor", oldArmor).get(2);

		if(!newArmorPossible.contains(oldArmor) && !newArmorPossible.contains(dbReturn)) oldArmor = null;
		randNpc.calculateArmorClass(oldArmor,newNpc,hasShield);

		if(hasShield && newArmorPossible.contains("Shield")) oldArmor = oldArmor + "+ Shield";

		newNpc.setArmor(oldArmor);

	}

	private void weaponChanges(NpcClass currentNpc, NpcClass newNpc, String newClass){
		List<String> newWeaponPossible = randGen.figureOutProfList("weapon", newClass, newNpc.getRace());
		List<String> oldWeapons = currentNpc.getWeaponList();
		List<String> alteredOldWeapons = new ArrayList<>(oldWeapons);
		String dbReturn;
		for(String s: oldWeapons){
			dbReturn = dbms.getRowWithPref("weapon", s).get(5); //ob prof mit der Waffen art

			if(!newWeaponPossible.contains(s) && !newWeaponPossible.contains(dbReturn)) alteredOldWeapons.remove(s);
		}

		newNpc.setWeaponList(alteredOldWeapons);
	}

	private void skillChanges(NpcClass currentNpc, String newClass, NpcClass newNpc){
		List<String> possibleSkillList = randGen.bigStringToDividedString(dbms.getRowWithPref("class", newClass).get(6));
		List<String> oldSkills = currentNpc.getSkillList();

		if(!oldSkills.isEmpty()||!oldSkills.contains("none")){
			oldSkills.removeIf(oldSkill -> !possibleSkillList.contains(oldSkill));
		}
		newNpc.setSkillList(oldSkills);

	}

	private void spellListChanges(NpcClass newNpc, NpcClass currentNpc){
		if(dbms.getRowWithPref("class",newNpc.getGenClass())!=null) { // sollte die neue Klasse nicht in der Lage sein Spells zu castern, muss dies nicht durchgelaufen zu werden
			//Spells
			int spellAttack = randGen.spellAttackMod(newNpc.getAbilityModifier(), newNpc.getProfBonus(), newNpc.getGenClass());
			int spellSave = randGen.spellSaveDC(randGen.spellAttackMod(newNpc.getAbilityModifier(), newNpc.getProfBonus(), newNpc.getGenClass()));
			List<String> spellAbility = new ArrayList<>();

			spellAbility.add(String.valueOf(spellAttack));
			spellAbility.add(String.valueOf(spellSave));
			newNpc.setSpellAbility(spellAbility);

			List<Object> alteredSpellList = newNpc.getSpellList();
			List<Object> newSpellList = new ArrayList<>();
			for(int i=0; i<10;i++){
				newSpellList.add(null);
			}
			int spellLevelIndex = 0;

			if (!alteredSpellList.isEmpty()) { //sollte die gesamte Spell liste leer sein
				for (Object spell : alteredSpellList) { // die einzelnen Spell listen durchlaufen
					if(spell == null) continue;
					if (spell.toString().equals(" ")) { // wenn ein spell der Spellliste nicht leer ist
						List<String> spellObjToStr = randGen.bigStringToDividedString(spell.toString());
						List<String> removedSpellList = new ArrayList<>(spellObjToStr);
						boolean whitespace = false;
						for (String subSpellToSpellListLevel : spellObjToStr) {
							if (subSpellToSpellListLevel.startsWith(" ")) {
								subSpellToSpellListLevel = subSpellToSpellListLevel.substring(1);
								whitespace = true;
							}
							//überprüfung ob der spell in der neuen möglichen Spellist existieren kann
							if (!(dbms.getRowWithPref("spells", subSpellToSpellListLevel)).getLast().equals(newNpc.getGenClass())) {
								//sollte der Zauber durch ein Leerzeichen angeführt werden, wird es hinzugefügt
								if(whitespace) removedSpellList.remove(" "+ subSpellToSpellListLevel);
								else removedSpellList.remove(subSpellToSpellListLevel);
							}
						}
						newSpellList.set(spellLevelIndex, removedSpellList);
					}
					spellLevelIndex++;
				}
			}
			currentNpc.setSpellList(newSpellList);
		}else{
			newNpc.initSpellList(); // spells auf null setzten
			randGen.addRaceCantripsAndSpells(newNpc); // sollte es spells durch die Rasse geben
		}
	}

	/**
	 * Remove race ability addition list.
	 *
	 * @param race         the race
	 * @param abilityScore the ability score
	 * @return the list
	 */
	public List<Integer> removeRaceAbilityAddition(String race, List<Integer> abilityScore) {
		List<String> raceAbilityModifier = randGen.bigStringToDividedString(dbms.getRowWithPref("race", race).get(2));
		int raceModNumber;
		for (String s : raceAbilityModifier) {
			raceModNumber = Integer.parseInt(s.substring(s.length() - 1));
			s = s.substring(0, 3);
			switch (s) {
				case "Str":
					abilityScore.set(0, abilityScore.getFirst() - raceModNumber);
					break;
				case "Dex":
					abilityScore.set(1, abilityScore.get(1) - raceModNumber);
					break;
				case "Con":
					abilityScore.set(2, abilityScore.get(2) - raceModNumber);
					break;
				case "Int":
					abilityScore.set(3, abilityScore.get(3) - raceModNumber);
					break;
				case "Wis":
					abilityScore.set(4, abilityScore.get(4) - raceModNumber);
					break;
				case "Cha":
					abilityScore.set(5, abilityScore.get(5) - raceModNumber);
					break;
				default:
					break;
			}
		}
		return abilityScore;
	}






}
