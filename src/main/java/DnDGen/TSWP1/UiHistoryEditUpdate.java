package DnDGen.TSWP1;

import DnDGen.DB.DBMS;
import org.springframework.context.ApplicationContext;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
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
        NPCClass currentNPC = oldNPC;
        NPCClass newNPC = currentNPC;
        String newRace = changes.get(0);
        String newCharClass = changes.get(1);
        String newAlignment = changes.get(2);


        //Übergangs Variablen werden wahrscheinlich später entfernt oder geändert
        ArrayList<Integer> abilityScore =(ArrayList<Integer>) oldNPC.getAbilityScores();
        String oldRace = oldNPC.getRace();

        //race change
        if(newRace == null){
        	//race geändert
            // Änderungen: RaceAbilityScoreAddierung, Features, Skills, Language, Speed, Description:(Age, height, size, creature Type), Senses

            abilityScore = (ArrayList<Integer>) editNPC.removeRaceAbilityAddition(oldRace, abilityScore);
            abilityScore = (ArrayList<Integer>) randNPC.addRaceModifierToAbilityScore(newRace, abilityScore);

            //class change
        } else if (newCharClass == null) {
            // class geändert
            // Änderungen: HP, (profWeapon,profArmor(+AC): nur Löschen wenn nicht mehr geht.), Skills(überprüfen welche bleiben können, welche nicht), AbilityScoreDistribution(von Luca holen), Spells(nuke), features(class features ersetzen, siehe funktion zum überprüfen welche egligable sind)
        	newNPC = classChange(newNPC,newCharClass,currentNPC);

        } else if ((changes.getFirst() != null) || (changes.getLast() != null )) {
            //alles hat sich geändert
        	newNPC = classChange(newNPC,newCharClass,currentNPC);

        } else if ((newAlignment!=null)){
            //Deity
            String deity = oldNPC.getDeity();
            if(!Objects.equals(dbms.getRow("deity", deity).get(2), newAlignment)){
                oldNPC.setDeity("---");
        }
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

    private NPCClass classChange(NPCClass newNPC,String newClass,NPCClass currentNPC){
    	newNPC = currentNPC;
    	//Hp änderung
    	newNPC.setHealthPoints(randNPC.calculateHP(newClass, newNPC.getLevel(), newNPC.getAbilityModifier()));;
    	
    	//AbiliSc
    	ArrayList<Integer> abilityScore = new ArrayList<>();
        abilityScore = (ArrayList<Integer>) editNPC.removeRaceAbilityAddition(newNPC.getRace(), abilityScore);//Race, ABiliSc
        abilityScore = (ArrayList<Integer>) randNPC.distributeAbilityScore(abilityScore,newClass);
        abilityScore = (ArrayList<Integer>) randNPC.addRaceModifierToAbilityScore(newNPC.getRace(),abilityScore);
    	newNPC.setAbilityScores(abilityScore);
    	
    	//Armor+AC
    	List<String> newArmorPossibi = randGen.figureOutProfList("armor", newClass, newNPC.getRace());
    	String oldArmor = currentNPC.getArmor();
    		for (String newArmor : newArmorPossibi) {
    			if (newArmor == oldArmor) {
    				break;
    			}else if (newArmor == newArmorPossibi.getLast()){
    				oldArmor = null;
    			}
    		}
    	newNPC.setArmor(oldArmor);
    	if (oldArmor == null) {
    		newNPC.setArmorClass((Integer) null);
    	}
    	
    	//Weapons
    	List<String> newWeaponPossibi = randGen.figureOutProfList("weapon", newClass, newNPC.getRace());
    	List<String> oldWeapons = currentNPC.getWeaponList();
    	for (String oldWeapon : oldWeapons) {
    		for (String newWeapon : newWeaponPossibi) {
    			if (newWeapon == oldWeapon) {
    				break;
    			}else if (newWeapon == newWeaponPossibi.getLast()){
    				oldWeapons.remove(oldWeapon);
    			}
    		}
    	}
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
    	for (String oldSkill : oldSkills) {
    		for (String possibSkill : possibleSkillList) {
    			if (possibSkill == oldSkill) {
    				break;
    			}else if (possibSkill == possibleSkillList.getLast()){
    				oldSkills.remove(oldSkill);
    			}
    		}
    	}
    	newNPC.setSkillList(oldSkills);;
    	
    	//Features
    	
    	return newNPC;
    }






}
