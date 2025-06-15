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
    ApplicationContext appCtx = DBMS.getApplicationContext();
    /**
     * The Dbms.
     */
    DBMS dbms = appCtx.getBean(DBMS.class);
    /**
     * The Rnd numb.
     */
    Random rndNumb = new SecureRandom();
    /**
     * The Rand gen.
     */
    RandomGenerator randGen = new RandomGenerator();
    /**
     * The Rand npc.
     */
	@Autowired
	private RandomNpc randNpc;
    //RandomNpc randNpc = new RandomNpc();
    /**
     * The Edit npc.
     */
    EditNpc editNpc = new EditNpc();

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
        NpcClass newNpc = oldNpc;
        String newRace = changes.get(0);
        String newCharClass = changes.get(1);
        String newAlignment = changes.get(2);

        //race change
        if(!Objects.equals(newRace, oldNpc.getRace())){
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
			List<String> raceLanguage = randGen.bigStringToDividedString(dbms.getRow("race", newRace).get(10));
			raceLanguage.removeLast();
			List<String> oldRaceLanguage = randGen.bigStringToDividedString(dbms.getRow("race", oldNpc.getRace()).get(10));
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

	private void alignmentChanges(String newAlignment, NpcClass newNpc, NpcClass oldNpc){
		//Deity
		String deity = oldNpc.getDeity();
		if(!Objects.equals(dbms.getRow("deity", deity).get(2), newAlignment)){
			newNpc.setDeity("---");
		}
	}

    private void classChange(NpcClass newNpc,String newClass,NpcClass currentNpc){
    	//Hp änderung
    	newNpc.setHealthPoints(randNpc.calculateHP(newClass, newNpc.getLevel(), newNpc.getAbilityModifier()));
    	
    	//AbilitySc
		List<Integer> abilityScore = newNpc.getAbilityScores();
        abilityScore = removeRaceAbilityAddition(newNpc.getRace(), abilityScore);//Race, ABiliSc
        abilityScore = randNpc.distributeAbilityScore(abilityScore,newClass);
        abilityScore = randNpc.addRaceModifierToAbilityScore(newNpc.getRace(),abilityScore);
    	newNpc.setAbilityScores(abilityScore);
    	
    	//Armor+AC
    	List<String> newArmorPossible = randGen.figureOutProfList("armor", newClass, newNpc.getRace());
    	String oldArmor = currentNpc.getArmor();
    		for (String newArmor : newArmorPossible) {
    			if (newArmor.equals(oldArmor)) {
    				break;
    			}else if (newArmor.equals(newArmorPossible.getLast())){
    				oldArmor = null;
					newNpc.setArmorClass(0);
				}
    		}
    	newNpc.setArmor(oldArmor);

    	//Weapons
    	List<String> newWeaponPossible = randGen.figureOutProfList("weapon", newClass, newNpc.getRace());
    	List<String> oldWeapons = currentNpc.getWeaponList();
		oldWeapons.removeIf(oldSkill -> !newWeaponPossible.contains(oldSkill));

    	//direkt neu befüllen? Oder (potenziell) leer lassen?
    	int weaponCount = rndNumb.nextInt(4)+2;
    	while (oldWeapons.size() < weaponCount) {
    		int newRandWeapon = rndNumb.nextInt(newWeaponPossible.size());
    		oldWeapons.add(newWeaponPossible.get(newRandWeapon));
    		newWeaponPossible.remove(newRandWeapon);
    	}
    	newNpc.setWeaponList(oldWeapons);

    	//Spells
    	newNpc.setSpellAbility(null);
    	newNpc.setSpellList(null);
    	
    	//Skills
    	List<String> possibleSkillList = randGen.bigStringToDividedString(dbms.getRow("class", newClass).get(6));
    	List<String> oldSkills = currentNpc.getSkillList();

        oldSkills.removeIf(oldSkill -> !possibleSkillList.contains(oldSkill));
    	newNpc.setSkillList(oldSkills);
    	
    	//Features

    }

	/**
	 * Remove race ability addition list.
	 *
	 * @param race         the race
	 * @param abilityScore the ability score
	 * @return the list
	 */
	public List<Integer> removeRaceAbilityAddition(String race, List<Integer> abilityScore) {
		List<String> raceAbilityModifier = randGen.bigStringToDividedString(dbms.getRow("race", race).get(2));
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
