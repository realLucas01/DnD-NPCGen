package DnDGen.TSWP1;

import DnDGen.DB.DBMS;
import org.springframework.context.ApplicationContext;

import java.security.SecureRandom;
import java.util.*;

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
    /* @author Lucas
     *
     * Aufrufen der GENAUEN DBMS bzw. SpringApplication die gestartet wurde, jede
     * neu-konstruktion von DBMS wäre sonst leer.
     */
    ApplicationContext appCtx = DBMS.getApplicationContext();
    DBMS dbms = appCtx.getBean(DBMS.class);
    
    Random rndNumb = new SecureRandom();
    RandomGenerator randGen = new RandomGenerator();
    RandomNPC randNPC = new RandomNPC();
    EditNPC editNPC = new EditNPC();

    /*
    changes = {race, class, alignment}
     */
    public NPCClass updateHistoryEdit (List<String> changes, NPCClass oldNPC ){
        NPCClass newNPC = oldNPC;
        String newRace = changes.get(0);
        String newCharClass = changes.get(1);
        String newAlignment = changes.get(2);

        //race change
        if(!Objects.equals(newRace, oldNPC.getRace())){
        	//race geändert
            //Änderungen: RaceAbilityScoreAddierung, Features, Skills, Language, Speed, Description:(Age, height, size), Senses
			ArrayList<Integer> abilityScore =(ArrayList<Integer>) oldNPC.getAbilityScores();
			newNPC.setRace(newRace);
            abilityScore = (ArrayList<Integer>) editNPC.removeRaceAbilityAddition(oldNPC.getRace(), abilityScore);
            abilityScore = (ArrayList<Integer>) randNPC.addRaceModifierToAbilityScore(newRace, abilityScore);
			newNPC.setBonusTextList(randNPC.determineFeatures(newRace,"bonus text")); //Features
			newNPC.setActionList(randNPC.determineFeatures(newRace,"action")); //Features
			newNPC.setBonusActionList(randNPC.determineFeatures(newRace,"bonus action")); //Features
			newNPC.setMovementSpeedList(randNPC.determineMovementSpeed(newRace));
			newNPC.setSenses(randGen.getVision(newRace) + ", Passive Perception " + randGen.passivePerception(newNPC.getAbilityModifier(), newNPC.getSkillList(), newNPC.getProfBonus()));
			newNPC.setAbilityScores(abilityScore);
			List<String> newDesc = oldNPC.getDescription();
			newDesc.set(newDesc.size()-2,randGen.randSize(newRace)); // size
			newDesc.set(newDesc.size()-1,randGen.randSize(newRace)); // height
			newNPC.setDescription(newDesc);
			newNPC.setAge(randGen.randAge(newRace));//age

			//Languages
			List<String> raceLanguage = randGen.bigStringToDividedString(dbms.getRow("race", newRace).get(10));
			raceLanguage.removeLast();
			List<String> oldRaceLanguage = randGen.bigStringToDividedString(dbms.getRow("race", oldNPC.getRace()).get(10));
			oldRaceLanguage.removeLast();
			List<String> oldNPCRaceLanguage = oldNPC.getLanguages();
			oldNPCRaceLanguage.removeAll(oldRaceLanguage);
			raceLanguage.addAll(oldNPCRaceLanguage);
			newNPC.setLanguages(raceLanguage);

			if (!newNPC.getClass().equals("Commoner")) {     // Fall Commoner?
				newNPC.setSkillList(randNPC.addAbilityScoreToSkillList(newNPC.getAbilityModifier(),randNPC.determineSkillList(newRace, newNPC.getJob(), newNPC.getGenClass()), newNPC.getProfBonus()));
			} else {
				newNPC.setSkillList(Collections.singletonList("none"));
			}

        }
		//class change
		if (!Objects.equals(newCharClass, oldNPC.getGenClass())) {
            // class geändert
            // Änderungen: HP, (profWeapon,profArmor(+AC): nur Löschen wenn nicht mehr geht.), Skills(überprüfen welche bleiben können, welche nicht), AbilityScoreDistribution(von Luca holen), Spells(nuke), features(class features ersetzen, siehe funktion zum überprüfen welche egligable sind)
        	newNPC.setGenClass(newCharClass);
			classChange(newNPC,newCharClass, oldNPC);

        }
        if ((!Objects.equals(newAlignment, oldNPC.getAlignment()))){
			newNPC.setAlignment(newAlignment);

			alignmentChanges(newAlignment, newNPC, oldNPC);
        }
        //geänderten wird dem UI zurückgegeben
        return newNPC;
    }

    /*
    Option um nicht die HP manuell ausrechnen zu müssen, kann die Option angeboten werden diese auch wieder zufällig generieren zu lassen
     */
    private int newRolledHP(int level, String genClass, List<Integer> abilityModifier){
        return randNPC.calculateHP(genClass, level, abilityModifier);
    }

	private void alignmentChanges(String newAlignment, NPCClass newNPC, NPCClass oldNPC){
		//Deity
		String deity = oldNPC.getDeity();
		if(!Objects.equals(dbms.getRow("deity", deity).get(2), newAlignment)){
			newNPC.setDeity("---");
		}
	}

    private void classChange(NPCClass newNPC,String newClass,NPCClass currentNPC){
    	//Hp änderung
    	newNPC.setHealthPoints(randNPC.calculateHP(newClass, newNPC.getLevel(), newNPC.getAbilityModifier()));
    	
    	//AbiliSc
		List<Integer> abilityScore = newNPC.getAbilityScores();
        abilityScore = editNPC.removeRaceAbilityAddition(newNPC.getRace(), (ArrayList<Integer>) abilityScore);//Race, ABiliSc
        abilityScore = randNPC.distributeAbilityScore(abilityScore,newClass);
        abilityScore = randNPC.addRaceModifierToAbilityScore(newNPC.getRace(),abilityScore);
    	newNPC.setAbilityScores(abilityScore);
    	
    	//Armor+AC
    	List<String> newArmorPossibi = randGen.figureOutProfList("armor", newClass, newNPC.getRace());
    	String oldArmor = currentNPC.getArmor();
    		for (String newArmor : newArmorPossibi) {
    			if (newArmor.equals(oldArmor)) {
    				break;
    			}else if (newArmor.equals(newArmorPossibi.getLast())){
    				oldArmor = null;
					newNPC.setArmorClass(0);
				}
    		}
    	newNPC.setArmor(oldArmor);

    	//Weapons
    	List<String> newWeaponPossibi = randGen.figureOutProfList("weapon", newClass, newNPC.getRace());
    	List<String> oldWeapons = currentNPC.getWeaponList();
		oldWeapons.removeIf(oldSkill -> !newWeaponPossibi.contains(oldSkill));
		/*
    	for (String oldWeapon : oldWeapons) {
    		for (String newWeapon : newWeaponPossibi) {
    			if (newWeapon.equals(oldWeapon)) {
    				break;
    			}else if (newWeapon.equals(newWeaponPossibi.getLast())){
    				oldWeapons.remove(oldWeapon);
    			}
    		}
    	}
    	*/

    	//direkt neu befüllen? Oder (potentiell) leer lassen?
    	int weaponCount = rndNumb.nextInt(4)+2;
    	while (oldWeapons.size() < weaponCount) {
    		int newRandWeap = rndNumb.nextInt(newWeaponPossibi.size());
    		oldWeapons.add(newWeaponPossibi.get(newRandWeap));
    		newWeaponPossibi.remove(newRandWeap);
    	}
    	newNPC.setWeaponList(oldWeapons);

    	//Spells
    	newNPC.setSpellAbility(null);
    	newNPC.setSpellList(null);
    	
    	//Skills
    	List<String> possibleSkillList = randGen.bigStringToDividedString(dbms.getRow("class", newClass).get(6));
    	List<String> oldSkills = currentNPC.getSkillList();
    	/*
    		for (String possibSkill : possibleSkillList) {
    			if (possibSkill.equals(oldSkill)) {
    				break;
    			}else if (possibSkill.equals(possibleSkillList.getLast())){
    				oldSkills.remove(oldSkill);
    			}
    		}
    		*/
        oldSkills.removeIf(oldSkill -> !possibleSkillList.contains(oldSkill));
    	newNPC.setSkillList(oldSkills);
    	
    	//Features
		//WIP TODO Features
    }






}
