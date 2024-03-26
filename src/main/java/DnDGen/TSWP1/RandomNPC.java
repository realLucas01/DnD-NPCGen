package DnDGen.TSWP1;

import java.lang.reflect.Array;
import java.util.*;

public class RandomNPC {

    public RandomGenerator randGen;
    public DBMS DBMS;

    public String createRandomNPC(String inputRace, String inputClass, String inputJob, Integer inputLevel, String inputAbilityScore){
        ArrayList<String> generatingNPC, skillList, weaponList, armorList, weaponProfList, armorProfList, knownSkillList = new ArrayList<>();
        String race, job, genClass;
        Integer level, numberSkill;

        //random Race
        if (inputRace.equals(null)){
           race =  randGen.randRace();
        } else {
            race= inputRace;
        }
        //random Level
        if(inputLevel.equals(null)){
            level = randGen.randLevel();
        }else {
            level = inputLevel;
        }
        //random Job
        if(inputJob.equals(null)){
            job = randGen.randJob();
        }else {
            job = inputJob;
        }
        //random Class
        if((inputClass.equals(null)) && (level != 0)){
            genClass = randGen.randCharClass();
        } else if (level == 0) {
            genClass = "Commoner";    // WUHU Edge Case -> Class == commoner
        } else {
            genClass = inputClass;
        }

        //random AbilityScore
        switch ((inputAbilityScore)){
            case null:
                //AbilityScore 3d6 verwenden
            case "3d6", "4d6", "5d6":
                //Ability Score berechnen mit vorgegebenen Würfel
            default:
                //arrays verwenden, nichts berechnen und Inhalt des Arrays verteilen
        }

        //distribute AbilityScore
            //Lenlen arbeitet daran

        //calc HP
            //wer will?

        armorProfList = randGen.figureOutProfList("armor", genClass, race);
        //randomArmor
            // Luca ist noch ein EdgeCase eingefallen, deswegen noch leer
        armorList = randGen.randArmor(armorProfList); // LUCA FIX


        weaponProfList = randGen.figureOutProfList("weapon", genClass, race);
        //randomWeapon
        weaponList = randGen.randWeapon(weaponProfList); // LUCA FIX

        //random Skills
        knownSkillList = DBMS.getRow("class", genClass).get();//LUCA SCHAU NACH
        knownSkillList.add(DBMS.getRow("race", race).get());// LUCA SCHAU NACH
        skillList = randGen.randSkillProf(numberSkill, knownSkillList);// LUA ÜBERLEG DIR WAS

        //random Gender

        //random Age

        //RandomName

        //random Description

        //random Alignment

        //random Deity

        //random Languages


        return "Hi";
    }
    public ArrayList<Integer> distributeAbilityScore (ArrayList<Integer> rolledAbilityScore, String charClass) {
        return null;
    }

    public ArrayList<String> chooseSpell(String charClass, String race, Integer level){
        return null;
    }

    public ArrayList<String> chooseAction (String charClass, String race, Integer level){
        return null;
    }
/*
    public ArrayList<String> editNPC(String inputStuff?){

    }
*/




}
