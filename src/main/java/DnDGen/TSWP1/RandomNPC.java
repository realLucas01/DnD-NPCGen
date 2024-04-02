package DnDGen.TSWP1;

import org.hibernate.mapping.Any;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RandomNPC {

    public RandomGenerator randGen;
    public DBMS DBMS;

    public String createRandomNPC(String inputRace, String inputClass, String inputJob, Integer inputLevel, String inputAbilityScore, String inputGender){
        ArrayList<String> generatingNPC;
        ArrayList<String> skillList = null;
        ArrayList<String> weaponList = null;
        ArrayList<String> possibleSkillList;
        String armorList, raceSkillList;
        ArrayList<String> weaponProfList;
        ArrayList<String> armorProfList;
        String race, job, genClass, gender, name, description, alignment, deity;
        int level, numberSkill, age;
        Pattern number = Pattern.compile("[0-9]", Pattern.CASE_INSENSITIVE); //zum überprüfen ob im String eine Zahl steckt
        boolean containsNumber;


        //random Race
        if (inputRace == null){
           race =  randGen.randRace();
        } else {
            race= inputRace;
        }
        //random Level
        if(inputLevel == null){
            level = randGen.randLevel();
        }else {
            level = inputLevel;
        }
        //random Job
        if(inputJob == null){
            job = randGen.randJob();
        }else {
            job = inputJob;
        }
        //random Class
        if((inputClass == null) && (level != 0)){
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
            // Luca ist noch ein EdgeCase eingefallen, deswegen noch leer:  Shield
        armorList = randGen.randArmor(armorProfList); // LUCA FIX

        weaponProfList = randGen.figureOutProfList("weapon", genClass, race);
        //randomWeapon
        weaponList.add(randGen.randWeapon(weaponProfList));

        //random Skills
        //Race Skills
        raceSkillList= DBMS.getRow("race", race).get(6);
        //Matcher matcherNumber = number.matcher(raceSkillList);
        //containsNumber = matcherNumber.find();
        switch (raceSkillList){
            case null:
                break;
            case ":":  // wenn Nummer innerhalb sich befinden
                numberSkill = Integer.parseInt(raceSkillList.substring(0,0)); //
                raceSkillList = raceSkillList.substring(raceSkillList.indexOf(":"), raceSkillList.length());

                skillList = (ArrayList<String>) randGen.randSkillProf(randGen.bigStringToDividedString(raceSkillList),numberSkill, skillList);
            default:
                skillList.addAll(randGen.bigStringToDividedString(raceSkillList));
        }

        //Job Skills
        skillList.addAll(randGen.bigStringToDividedString(DBMS.getRow("job", job).get(2))); //Skills durch Job

        //ClassSkills
        possibleSkillList = randGen.bigStringToDividedString(DBMS.getRow("class", genClass).get(6)); // Skills durch class
        numberSkill = Integer.parseInt(DBMS.getRow("class", genClass).get(7)); // Commoner ClassSkillNumber auf 0 setzen
        if(!possibleSkillList.contains(0)){     // Fall Commoner?
            // nichts machen und weiter gehen
        } else if (possibleSkillList.contains("Any")) { // fall barde
            possibleSkillList.add("Acrobatics");
            possibleSkillList.add("Animal Handling");
            possibleSkillList.add("Arcana");
            possibleSkillList.add("Athletics");
            possibleSkillList.add("Deception");
            possibleSkillList.add("History");
            possibleSkillList.add("Insight");
            possibleSkillList.add("Intimidation");
            possibleSkillList.add("Investigation");
            possibleSkillList.add("Medicine");
            possibleSkillList.add("Nature");
            possibleSkillList.add("Perception");
            possibleSkillList.add("Performance");
            possibleSkillList.add("Persuasion");
            possibleSkillList.add("Religion");
            possibleSkillList.add("Sleight of Hand");
            possibleSkillList.add("Stealth");
            possibleSkillList.add("Survival" );    //alle skills hinzufügen
        }

        skillList = (ArrayList<String>) randGen.randSkillProf(possibleSkillList,numberSkill, skillList);
        Set<String> removeDuplicate = new HashSet<>(skillList); // die ArrayList wird in ein Set gestopft, um Duplicate innerhalb, zu entfernen
        skillList.clear();
        skillList.addAll(removeDuplicate);

        //random Gender
        if (inputGender== null){
            gender = randGen.randGender();
        } else{
            gender = inputGender;
        }

        //random Age
        age = randGen.randAge(race);

        //RandomName
        name = randGen.randName(gender,race);

        //random Description
        description = randGen.randDescription(race);

        //random Alignment
        alignment = randGen.randAlignment();

        //random Deity
        deity = randGen.randDeity(alignment);

        //random Languages


        return "Hi";
    }
    public ArrayList<Integer> distributeAbilityScore (ArrayList<Integer> rolledAbilityScore, String charClass) {
        return null;
    }

    public ArrayList<String> chooseSpell(String charClass, String race, Integer level){ //für edit
        return null;
    }

    public ArrayList<String> chooseAction (String charClass, String race, Integer level){//für edit
        return null;
    }
/*
    public ArrayList<String> editNPC(String inputStuff?){

    }
*/




}
