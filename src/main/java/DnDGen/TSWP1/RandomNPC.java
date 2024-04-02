package DnDGen.TSWP1;

import org.hibernate.mapping.Any;
import org.hibernate.mapping.Array;

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
        ArrayList<String> languages = null;
        String armorList, raceSkillList;
        ArrayList<String> weaponProfList;
        ArrayList<String> armorProfList;
        ArrayList<String> armorBoni;
        ArrayList<String> currentArmor;
        Random rndNumb = new Random();
        String race, job, genClass, gender, name, description, alignment, deity, languagesRace, languagesJob, kindOfArmor;
        int level, numberSkill, age, count,weaponCount, armorClass = 0;
        Pattern number = Pattern.compile("[0-9]", Pattern.CASE_INSENSITIVE); //zum Überprüfen, ob im String eine Zahl steckt
        Matcher matcherNumber;

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
        armorList = randGen.randArmor(armorProfList); // LUCA FIX

        //Berechnung AC
        currentArmor= DBMS.getRow("armor",armorList);
        armorClass = Integer.parseInt(currentArmor.get(3));

        /*
        checken ob Armor "normale" ist oder natürliche, bzw. ClassArmor
         */
        kindOfArmor = currentArmor.get(2);
        if((kindOfArmor.equals("Natural Armor"))||(kindOfArmor.equals("Class Armor"))){
            armorBoni = randGen.bigStringToDividedString(currentArmor.get(5));
            for(String s: armorBoni){
                //für jeden AbilityScore Boni, diesen hinzufügen
                switch(s){
                    case "Dex":

                }
            }
            if(armorClass> Integer.parseInt(currentArmor.get(4))){//überprüfen ob AC im toleranten Bereich liegt
                armorClass= Integer.parseInt(currentArmor.get(4)); // wenn nein, dann diesen festlegen
            }
        }
        if(armorProfList.contains("shield")){
            int shieldPercent= rndNumb.nextInt(2);
            if(shieldPercent == 1){
                armorList = armorList + " + " + "shield";
                armorClass = armorClass +2;
            }
        }

        weaponProfList = randGen.figureOutProfList("weapon", genClass, race);
        //randomWeapon
        weaponCount = rndNumb.nextInt(4);
        for(int i=0; i<weaponCount;i++){
            weaponList.add(randGen.randWeapon(weaponProfList));
        }

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
        languagesRace = DBMS.getRow("race",race).get(10);   // Sprachen der Rasse
        languagesJob = DBMS.getRow("job", job).get(3); // Anzahl der möglichen Sprachen durch den Job
        languages = randGen.bigStringToDividedString(languagesRace); //aufteilung des Strings

        count = Integer.parseInt(languages.getLast()) + Integer.parseInt(languagesJob); // Berechnung der Anzahl an Sprachen
        languages.removeLast(); //Nummer wird entfernt aus der ArrayList
        for ( int i=0; i<count;i++){
            languages.add(randGen.randLanguages()); //for schleife
        }

        Set<String> removeDuplicatedLanguages = new HashSet<>(languages); // die ArrayList wird in ein Set gestopft, um Duplicate innerhalb, zu entfernen
        languages.clear();
        languages.addAll(removeDuplicatedLanguages);

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
