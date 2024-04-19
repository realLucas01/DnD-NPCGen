package DnDGen.TSWP1;

import java.util.*;
import org.springframework.context.ApplicationContext;
import DnDGen.DB.DBMS;
import static DnDGen.TSWP1.RandomGenerator.bigStringToDividedString;

public class RandomNPC {
	/* @author Lucas
	 * 
	 * Aufrufen der GENAUEN DBMS bzw. SpringApplication die gestartet wurde, jede
	 * neu-konstruktion von DBMS wäre sonst leer.
	 */
    Random rndNumb =  new Random();
    RandomGenerator randGen = new RandomGenerator();
    ApplicationContext appCtx = DBMS.getApplicationContext();
    DBMS dbms = appCtx.getBean(DBMS.class);

    /*
    * Diese Funktion dient dazu einen NPC Stat Block zufällig generieren zu lassen.
    * Die Eingabe Werte sind:
    * Race, Class, Job, Level, AbilityScore, Gender
    *
    * Zurück erhält man eine ArrayList, diese ist wie folgt aufgebaut:
    * (race, level, job, class, AbilityScore(Str, Dex, Con, Int, Wis, Cha), AbilityModifier(Str, Dex, Con, Int, Wis, Cha), HP, Armor, AC, Weapon(), Skills(), Gender, Age, Name, Description, Alignment, Deity, Languages())
     */

    public ArrayList<String> createRandomNPC(String inputRace, String inputClass, String inputJob, Integer inputLevel, String inputAbilityScore, String inputGender) {
        ArrayList<String> skillList = new ArrayList<>();
        ArrayList<String> weaponList = new ArrayList<>();
        ArrayList<String> possibleSkillList;
        ArrayList<String> languages;
        String raceSkillList;
        ArrayList<String> weaponProfList;
        ArrayList<String> savingThrowList;
        ArrayList<String> savingThrows = new ArrayList<>();
        ArrayList<String> movementSpeed;
        ArrayList<String> description;
        ArrayList<Integer> abilityScores = null;
        ArrayList<Integer> abilityModifier;
        ArrayList<String> generatedNPCFinished = new ArrayList<>();
        Random rndNumb = new Random();
        String race, job, genClass, gender, name, alignment, deity, languagesRace, languagesJob, kindOfArmor;
        int level, numberSkill, age, profBonus, count, weaponCount, healthPoints, armorClass;
String inputName = null;
        //random Race
        if (inputRace == null) {
            race = randGen.randRace();
        } else {
            race = inputRace;
        }
        generatedNPCFinished.addFirst(race);
        //random Level
        if (inputLevel == null) {
            level = randGen.randLevel();
        } else {
            level = inputLevel;
        }
        if (Objects.equals(inputClass, "Commoner")) {
            level = 0;
        }
        generatedNPCFinished.add(generatedNPCFinished.size(), String.valueOf(level));
        //random Job
        if (inputJob == null) {
            job = randGen.randJob();
        } else {
            job = inputJob;
        }
        generatedNPCFinished.add(generatedNPCFinished.size(), job);
        //random Class
        if ((inputClass == null) && (level != 0)) {
            genClass = randGen.randCharClass();
        } else if (level == 0) {
            genClass = "Commoner";    // WUHU Edge Case -> Class == commoner
        } else {
            genClass = inputClass;
        }
        generatedNPCFinished.add(generatedNPCFinished.size(), genClass);

        //random AbilityScore
        switch ((inputAbilityScore)) {
            case null:
                //AbilityScore 3d6 verwenden
                abilityScores = randGen.randAbilityScore();
                break;
            case "4d6":
                //Ability Score berechnen mit vorgegebenen Würfel
                ArrayList<Integer> scoreFour = new ArrayList<>();
                ArrayList<Integer> finishedSetForAbilityScores4 = new ArrayList<>();

                for (int i = 0; i < 6; i++) {
                    scoreFour.add(randGen.diceThrower(1, 6));
                    scoreFour.add(randGen.diceThrower(1, 6));
                    scoreFour.add(randGen.diceThrower(1, 6));
                    scoreFour.add(randGen.diceThrower(1, 6));
                    scoreFour.add(randGen.diceThrower(1, 6));
                    Collections.sort(scoreFour, Collections.reverseOrder());
                    int abilityScore = scoreFour.get(0) + scoreFour.get(1) + scoreFour.get(2);
                    finishedSetForAbilityScores4.add(abilityScore);
                    scoreFour.clear();
                }
                Collections.sort(finishedSetForAbilityScores4, Collections.reverseOrder());
                abilityScores = finishedSetForAbilityScores4;
                break;
            case "5d6":
                randGen.diceThrower(5, 6);
                ArrayList<Integer> scoreFive = new ArrayList<>();
                ArrayList<Integer> finishedSetForAbilityScores5 = new ArrayList<>();

                for (int i = 0; i < 6; i++) {
                    scoreFive.add(randGen.diceThrower(1, 6));
                    scoreFive.add(randGen.diceThrower(1, 6));
                    scoreFive.add(randGen.diceThrower(1, 6));
                    scoreFive.add(randGen.diceThrower(1, 6));
                    scoreFive.add(randGen.diceThrower(1, 6));
                    scoreFive.add(randGen.diceThrower(1, 6));
                    Collections.sort(scoreFive, Collections.reverseOrder());

                    int abilityScore = scoreFive.get(0) + scoreFive.get(1) + scoreFive.get(2);

                    finishedSetForAbilityScores5.add(abilityScore);

                    scoreFive.clear();
                }
                Collections.sort(finishedSetForAbilityScores5, Collections.reverseOrder());
                break;
            default:
                //arrays verwenden, nichts berechnen und Inhalt des Arrays verteilen
                ArrayList<String> abilityScoreNew = new ArrayList<String>();
                abilityScoreNew = bigStringToDividedString(inputAbilityScore);
                ArrayList<Integer> abilityScoreNewInteger = new ArrayList<Integer>();
                for (String abilistr : abilityScoreNew) {
                    abilityScoreNewInteger.add(Integer.parseInt(abilistr));
                }
                //dbms.getRowPref();
        }

        //distribute AbilityScore
        abilityScores = distributeAbilityScore(abilityScores, genClass);
        generatedNPCFinished.add(generatedNPCFinished.size(), String.valueOf(abilityScores));

        //calc AbilityModifier
        abilityModifier = determinAbilityModifier(abilityScores);
        generatedNPCFinished.add(generatedNPCFinished.size(), String.valueOf(abilityModifier));

        //calc HP
        healthPoints = calculateHP(genClass, level, abilityModifier);
        generatedNPCFinished.add(generatedNPCFinished.size(), String.valueOf(healthPoints));

        ArrayList<String> armorList = determineArmorAndAC(genClass, race, abilityModifier);
        generatedNPCFinished.add(generatedNPCFinished.size(), armorList.get(0));//armor
        generatedNPCFinished.add(generatedNPCFinished.size(), armorList.get(1));//AC

        weaponList = determineWeapons(genClass, race);
        generatedNPCFinished.add(generatedNPCFinished.size(), String.valueOf(weaponList));//waffenliste

        ArrayList<String> movementSpeedList = determineMovementSpeed(race);
        generatedNPCFinished.add(generatedNPCFinished.size(), String.valueOf(movementSpeedList));//movement speed

        //Prof Bonus
        profBonus = determineProfBonus(level);

        savingThrows = determineSavingThrows(genClass, abilityModifier, profBonus);// saving throws
        //Saving Throws

        generatedNPCFinished.add(generatedNPCFinished.size(), String.valueOf(savingThrows));
        if (!genClass.equals("Commoner")) {     // Fall Commoner?
            generatedNPCFinished.add(generatedNPCFinished.size(), String.valueOf(addAbilityScoreToSkillList(abilityModifier,determineSkillList(race, job, genClass), profBonus)));
        } else {
            generatedNPCFinished.add(generatedNPCFinished.size(), "none");
        }

        //random Gender
        if (inputGender == null) {
            gender = randGen.randGender();
        } else {
            gender = inputGender;
        }
        generatedNPCFinished.add(generatedNPCFinished.size(), gender);

        //random Age
        age = randGen.randAge(race);
        generatedNPCFinished.add(generatedNPCFinished.size(), String.valueOf(age));

        //RandomName
        /*
        if (inputName == null) {
            String raceName;
            if (race.contains("Half")) {
                raceName = race.replace("Half-", "");
            } else {
                raceName = race;
            }
            name = randGen.randName(gender, raceName);
        }else{
            name = inputName;
        }*/
        name = "Olaf";
        generatedNPCFinished.add(generatedNPCFinished.size(), name);

        //random Description
        description = randGen.randDescription();
        generatedNPCFinished.add(generatedNPCFinished.size(), String.valueOf(description));

        //random Alignment
        alignment = randGen.randAlignment();
        generatedNPCFinished.add(generatedNPCFinished.size(), alignment);

        //random Deity
        deity = randGen.randDeity(alignment);
        generatedNPCFinished.add(generatedNPCFinished.size(), deity);

        //random Languages
        languagesRace = dbms.getRow("race", race).get(10);   // Sprachen der Rasse
        languagesJob = dbms.getRow("job", job).get(3); // Anzahl der möglichen Sprachen durch den Job

        languages = bigStringToDividedString(languagesRace); //aufteilung des Strings

        count = Integer.parseInt(languages.getLast()) + Integer.parseInt(languagesJob); // Berechnung der Anzahl an Sprachen

        languages.removeLast(); //Nummer wird entfernt aus der ArrayList
        for (int i = 0; i < count; i++) {
            languages.add(randGen.randLanguages()); //for schleife
        }

        Set<String> removeDuplicatedLanguages = new HashSet<>(languages); // die ArrayList wird in ein Set gestopft, um Duplicate innerhalb, zu entfernen
        languages.clear();
        languages.addAll(removeDuplicatedLanguages);
        generatedNPCFinished.add(generatedNPCFinished.size(), String.valueOf(languages));
        return generatedNPCFinished;
    }

    public ArrayList<Integer> distributeAbilityScore (ArrayList<Integer> rolledAbilityScore, String charClass) {
    	
    	ArrayList<Integer> rawAbilityScores ;
    	ArrayList<String> arrayForClass;
    	ArrayList<String> dividedAbilityScores ;
    	ArrayList<String> finishedAbilityScores = new ArrayList<>();
    	ArrayList<Integer> parsedAbilityScores = new ArrayList<>();
    	
    	rawAbilityScores = rolledAbilityScore;
    	
    	arrayForClass = dbms.getRow("class", charClass);
    	
    	dividedAbilityScores = bigStringToDividedString(arrayForClass.get(8));
    	
    	finishedAbilityScores.add("Str");
    	finishedAbilityScores.add("Dex");
    	finishedAbilityScores.add("Con");
    	finishedAbilityScores.add("Int");
    	finishedAbilityScores.add("Wis");
    	finishedAbilityScores.add("Cha");
    	
    	if(!(charClass.equals("Commoner"))) {
    	int i = 0;
    	int j = 0;

    	while(i <= 5 && j <= 5) {
    		String aScore = dividedAbilityScores.get(i);
    		Integer value = rawAbilityScores.get(i);
    		String aScoreFinished = finishedAbilityScores.get(j);

    		if(aScoreFinished.equals(aScore)  ) {
    			finishedAbilityScores.set(j, value.toString());
    			j++;
    			i = 0;
    		} else {
    			i++;
    		}
            aScore = null;
    	}
    	for(int k = 0; k < 6; k++) {
    		Integer parsedInt = Integer.parseInt(finishedAbilityScores.get(k));
    		parsedAbilityScores.add(parsedInt);
    	}
    	return parsedAbilityScores;
    	} else {
    		
    		Collections.shuffle(rawAbilityScores);

            return rawAbilityScores;
    	}
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
    public static ArrayList<Integer> determinAbilityModifier(ArrayList<Integer> abilityScore){
        ArrayList<Integer> abilityModifier = new ArrayList<>();
        float calcMod;
        for (Integer i: abilityScore){
            calcMod = (float) (i-10)/ 2 - 0.2f;
            abilityModifier.add(Math.round(calcMod));
        }
        return abilityModifier;
    }

    public ArrayList<String> addAbilityScoreToSkillList(ArrayList<Integer> abilityModifier, ArrayList<String> skillList, int profBonus ){
        ArrayList<String> skillListWithNumbers = new ArrayList<>();
        String dbAbilityScore ;
        for (String s : skillList){
            if((s != null) && (!s.equals("Any"))) {
                s= s.replaceAll("\\s+","");
                dbAbilityScore = dbms.getRow("skills", s).get(2);
                switch (s){
                    case "SleightofHand":
                        s= "Sleight of Hand";
                        break;
                    case "AnimalHandling":
                        s="Animal Handling";
                        break;
                }

                switch (dbAbilityScore) {
                    case "Str":
                        skillListWithNumbers.add(s + " +" + (abilityModifier.get(0) + profBonus));
                        break;
                    case "Dex":
                        skillListWithNumbers.add(s + " +" + (abilityModifier.get(1)+ profBonus));
                        break;
                    case "Con":
                        skillListWithNumbers.add(s + " +" + (abilityModifier.get(2)+ profBonus));
                        break;
                    case "Int":
                        skillListWithNumbers.add(s + " +" + (abilityModifier.get(3)+ profBonus));
                        break;
                    case "Wis":
                        skillListWithNumbers.add(s + " +" + (abilityModifier.get(4)+ profBonus));
                        break;
                    case "Cha":
                        skillListWithNumbers.add(s + " +" + (abilityModifier.get(5)+ profBonus));
                        break;
                }
            }
        }
        return skillListWithNumbers;
    }
    /*
    Diese Funktion wurde ausgelagert. Sie möchte, um glücklich zu sein, die klasse, rasse und die ability Modifier.
    Am Ende wird zurückgegeben eine ArrayList<> mit (Die ausgewählte Rüstung, die Armor Class vollständig berechnet)
     */
    public ArrayList<String> determineArmorAndAC(String genClass, String race, ArrayList<Integer> abilityModifier){
        ArrayList<String> armorProfList = randGen.figureOutProfList("armor", genClass, race);
        ArrayList<String> armorBoni;
        ArrayList<String> currentArmor;
        String kindOfArmor;
        int armorClass;
        boolean isThereAShield =armorProfList.contains("Shield");

        String armorList = randGen.randArmor(armorProfList); // LUCA FIX   // Sollte gefixt sein

        //Berechnung AC
        if(armorList == null){
            armorList = "None";
        }
        currentArmor= dbms.getRow("armor",armorList);
        armorClass = Integer.parseInt(currentArmor.get(3));
        armorBoni = bigStringToDividedString(currentArmor.get(5));

        kindOfArmor = currentArmor.get(2);
        if((kindOfArmor.equals("Natural Armor"))||(kindOfArmor.equals("Class Armor"))){
            for(String s: armorBoni){
                //für jeden AbilityScore Boni, diesen hinzufügen
                armorClass = switch (s) {
                    case "Str" -> armorClass + abilityModifier.get(0);
                    case "Dex" -> armorClass + abilityModifier.get(1);
                    case "Con" -> armorClass + abilityModifier.get(2);
                    case "Int" -> armorClass + abilityModifier.get(3);
                    case "Wis" -> armorClass + abilityModifier.get(4);
                    case "Cha" -> armorClass + abilityModifier.get(5);
                    default -> armorClass;
                };
            }
        }else if((armorBoni.size()==1)&&(armorBoni.contains("Dex"))) {
            armorClass = Integer.parseInt(currentArmor.get(3)) + abilityModifier.get(1);
        }
        if((armorClass> Integer.parseInt(currentArmor.get(4)))){//überprüfen ob AC im toleranten Bereich liegt
            armorClass= Integer.parseInt(currentArmor.get(4)); // wenn nein, dann diesen festlegen
        }
        if(isThereAShield){
            int shieldPercent= rndNumb.nextInt(2);
            if(shieldPercent == 1){
                armorList = armorList + " + " + "Shield";
                armorClass = armorClass +2;
            }
        }
        ArrayList<String> armorAndArmorClass = new ArrayList<>();
        armorAndArmorClass.add(armorList);
        armorAndArmorClass.add(String.valueOf(armorClass));
        return armorAndArmorClass;
    }
    public ArrayList<String> determineWeapons(String genClass, String race){
        ArrayList<String> weaponProfList = randGen.figureOutProfList("weapon", genClass, race);
        ArrayList<String> weaponList = new ArrayList<>();
        //randomWeapon
         int weaponCount = rndNumb.nextInt(4);
        for(int i=0; i<weaponCount;i++){
            weaponList.add(randGen.randWeapon(weaponProfList));
        }
        Set<String> removeDuplicateWeapons = new HashSet<>(weaponList); // die ArrayList wird in ein Set gestopft, um Duplicate innerhalb, zu entfernen
        weaponList.clear();
        weaponList.addAll(removeDuplicateWeapons);
        return weaponList;
    }
    public ArrayList<String> determineMovementSpeed(String race) {
        //creature Speed
        ArrayList<String> movementSpeed = (bigStringToDividedString(dbms.getRow("race", race).get(9)));
        ArrayList<String> movementSpeedList = new ArrayList<>();
        for (String s : movementSpeed) {
            switch (s.substring(s.length() - 1)) {

                case "w":
                    movementSpeedList.add(movementSpeed.get(movementSpeed.indexOf(s)).substring(0, 2) + " ft" + " walking Speed");
                    break;
                case "f":
                    movementSpeedList.add(movementSpeed.get(movementSpeed.indexOf(s)).substring(0, 2) + " ft" + " flying Speed");
                    break;
                case "b":
                    movementSpeedList.add(movementSpeed.get(movementSpeed.indexOf(s)).substring(0, 2) + " ft" + " burrowing Speed");
                    break;
            }

        }
        return movementSpeedList;
    }
    public int determineProfBonus(int level) {
       return Integer.parseInt(dbms.getRow("level", String.valueOf(level)).get(2));
    }
    public ArrayList<String> determineSavingThrows(String genClass, ArrayList<Integer> abilityModifier, int profBonus){
        ArrayList<String> savingThrows = new ArrayList<>();
        if(!genClass.equals("Commoner")) {
            ArrayList<String> savingThrowList = (bigStringToDividedString(dbms.getRow("class", genClass).get(5)));
            for (String s : savingThrowList) {
                //für jeden AbilityScore Boni, diesen hinzufügen
                switch (s) {
                    case "Str":
                        savingThrows.add("Str " + "+" +(abilityModifier.getFirst() + profBonus));
                        break;
                    case "Dex":
                        savingThrows.add("Dex " + "+" +(abilityModifier.get(1) + profBonus));
                        break;
                    case "Con":
                        savingThrows.add("Con " + "+" +(abilityModifier.get(2) + profBonus));
                        break;
                    case "Int":
                        savingThrows.add("Int " + "+" +(abilityModifier.get(3) + profBonus));
                        break;
                    case "Wis":
                        savingThrows.add("Wis " + "+" +(abilityModifier.get(4) + profBonus));
                        break;
                    case "Cha":
                        savingThrows.add("Cha" + "+" +(abilityModifier.get(5) + profBonus));
                        break;
                    default:
                        savingThrows.add("none");
                        break;
                }
            }
            savingThrows.remove("none");
        }else{
            savingThrows.add("none");
        }

        return savingThrows;
    }
    public int calculateHP(String genClass, int level, ArrayList<Integer> abilityModifier ){
        if (level == 0){
            level = 1;
        }
        return randGen.diceThrower(level, Integer.parseInt(dbms.getRow("class",genClass).get(2)))+ level*abilityModifier.get(2);

    }
    public ArrayList<String> determineSkillList (String race, String job, String genClass){
        //Race Skills
        int numberSkill;
        ArrayList<String> skillList =new ArrayList<>();
        String raceSkillList = dbms.getRow("race", race).get(6);
        if((raceSkillList != null)&&(!raceSkillList.contains("Any"))&&(raceSkillList.contains(":"))){ // nicht null, nicht Any, aber enthält":"
            numberSkill = Integer.parseInt(raceSkillList.substring(0,1));
            raceSkillList = raceSkillList.substring(raceSkillList.indexOf(":")+1, raceSkillList.length()-1);
            skillList = (ArrayList<String>) randGen.randSkillProf(bigStringToDividedString(raceSkillList),numberSkill, skillList);

        } else if ((raceSkillList !=null) && raceSkillList.contains("Any")) { //nicht null, enthält Any
            raceSkillList=raceSkillList.replace("Any","");
            numberSkill = Integer.parseInt(raceSkillList.substring(0,1));
            raceSkillList= "Acrobatics, Animal Handling, Arcana, Athletics, Deception, History, Insight, Intimidation, Investigation, Medicine, Nature, Perception, Performance, Persuasion, Religion, Sleight of Hand, Stealth, Survival ";
            skillList = (ArrayList<String>) randGen.randSkillProf(bigStringToDividedString(raceSkillList),numberSkill, skillList);

        } else {
            skillList.add(raceSkillList);
        }
        //Job Skills
        skillList.addAll(bigStringToDividedString(dbms.getRow("job", job).get(2))); //Skills durch Job

        //ClassSkills
        ArrayList<String> possibleSkillList = bigStringToDividedString(dbms.getRow("class", genClass).get(6)); // Skills durch class
        numberSkill = Integer.parseInt(dbms.getRow("class", genClass).get(7)); // Commoner ClassSkillNumber auf 0 setzen
            if (possibleSkillList.contains("Any")) { // fall barde
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
                possibleSkillList.add("Survival");    //alle skills hinzufügen
            }
            skillList = (ArrayList<String>) randGen.randSkillProf(possibleSkillList,numberSkill, skillList);
            Set<String> removeDuplicate = new HashSet<>(skillList); // die ArrayList wird in ein Set gestopft, um Duplicate innerhalb, zu entfernen
            skillList.clear();
            skillList.addAll(removeDuplicate);
        return skillList;
    }





}
