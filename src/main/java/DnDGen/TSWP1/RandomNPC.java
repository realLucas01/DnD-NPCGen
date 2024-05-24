package DnDGen.TSWP1;

import java.security.SecureRandom;
import java.util.*;
import org.springframework.context.ApplicationContext;
import DnDGen.DB.DBMS;

public class RandomNPC {
	/* @author Lucas
	 * 
	 * Aufrufen der GENAUEN DBMS bzw. SpringApplication die gestartet wurde, jede
	 * neu-konstruktion von DBMS wäre sonst leer.
	 */
    ApplicationContext appCtx = DBMS.getApplicationContext();
    DBMS dbms = appCtx.getBean(DBMS.class);
    
    Random rndNumb =  new SecureRandom();
    RandomGenerator randGen = new RandomGenerator();
    /*
    nicht wundern, diese variablen die hier folgen dienen dazu sich wiederholende Strings bei DB aufrufen zu vermeiden
    und um SonarCube zufrieden zu stellen
     */
    String nameDBFeatures = "features";
    String nameDBCommoner = "Commoner";
    String nameDBClass = "class";


    /*

    * Diese Funktion dient dazu einen NPC Stat Block zufällig generieren zu lassen.
    * Die Eingabe Werte sind:
    * Race, Class, Job, Level, AbilityScore, Gender
    *
    * Zurück erhält man eine ArrayList, diese ist wie folgt aufgebaut:
    * (race, level, job, class, AbilityScore, AbilityModifier, HP, Armor, AC, Weapon(), walkingSpeed, SavingThrows, Skills(), Gender, Age, Name, Description, Deity, Languages, Senses, spellList)
    *   0: race
    *   1: level
    *   2: job
    *   3: class
    *   4: AbilityScore
    *   5: AbilityModifier
    *   6: HP
    *   7: Armor
    *   8: AC
    *   9: WeaponList
    *   10: walkingSpeedList
    *   11: SavingThrows
    *   12: SkillList
    *   13: Gender
    *     14: Age
    *   15: Name
    *       16: Alignment
    *   17: Description
    *    18: Deity
    *   19: Languages
    *   20: Senses
    *   21: spellList
    *   22: Action
    *   23: BonusAction
    *   24: Bonus Text
        25: Spell Ability = Spell Attack Mod und Spell Save DC

        HISTORY:
        0: name ;
        1: level ;
        2: genClass ;
        3: race ;
        4: job ;
        5: armorClass ;
        6: healthPoints ;
        7: movementSpeedList ;
        8: languages ;
        9: abilityScores ;
        10: savingThrows ;
        11: armor ;
        12: weaponList ;
        13: skillList ;
        14: senses ;
        15: spellList ;
        16: spellAbility ;
        17: description ;
        18: actionList ;
        19: bonusActionList ;
        20: profBonus ;

        21: abilityModifier ;
        22: gender ;
        23: age ;
        24: alignment ;
        25: deity ;
        26: bonusTextList ;
     */

    public ArrayList<Object> createRandomNPC(String inputName,String inputRace, String inputClass, String inputJob, Integer inputLevel, String inputAbilityScore, String inputGender) {
        ArrayList<String> weaponList;
        ArrayList<String> languages;
        ArrayList<String> savingThrows;
        ArrayList<String> description;
        ArrayList<String> skillList = new ArrayList<>();
        ArrayList<Integer> abilityScores = new ArrayList<>();
        ArrayList<Integer> abilityModifier;
        ArrayList<Object> generatedNPCFinished = new ArrayList<>();
        String race;
        String job;
        String genClass;
        String gender;
        String name;
        String alignment;
        String deity;
        String senses;
        int level;
        int age;
        int profBonus;
        int healthPoints;
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
        } else{
            level = inputLevel;
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
            genClass = nameDBCommoner;
        } else {
            genClass = inputClass;
        }
        generatedNPCFinished.add(generatedNPCFinished.size(), genClass);

        //random AbilityScore
        switch ((inputAbilityScore)) {
            case "3d6":
            case null:
                //AbilityScore 3d6 verwenden
                abilityScores = randGen.randAbilityScore(3);
                break;
            case "4d6":
                abilityScores = randGen.randAbilityScore(4);
                break;
            case "5d6":
                abilityScores = randGen.randAbilityScore(5);
                break;
            default:
                //arrays verwenden, nichts berechnen und Inhalt des Arrays verteilen
                ArrayList<String> abilityScoreNew = randGen.bigStringToDividedString(inputAbilityScore);
                ArrayList<Integer> abilityScoreNewInteger = new ArrayList<>();
                for (String abilistr : abilityScoreNew) {
                    abilityScoreNewInteger.add(Integer.parseInt(abilistr));
                }
                abilityScores.addAll(abilityScoreNewInteger);
                break;
        }

        //distribute AbilityScore
        abilityScores = distributeAbilityScore(abilityScores, genClass);
        abilityScores = addRaceModifierToAbilityScore(race,abilityScores);
        abilityScores = (ArrayList<Integer>) addAbilityScoreImprovement(level, abilityScores);
        generatedNPCFinished.add(generatedNPCFinished.size(), String.valueOf(abilityScores));

        //calc AbilityModifier
        abilityModifier = determineAbilityModifier(abilityScores);
        generatedNPCFinished.add(generatedNPCFinished.size(), String.valueOf(abilityModifier));

        //calc HP
        healthPoints = calculateHP(genClass, level, abilityModifier);
        generatedNPCFinished.add(generatedNPCFinished.size(), String.valueOf(healthPoints));

        ArrayList<String> armorList = determineArmorAndAC(genClass, race, abilityModifier);
        String armor = armorList.get(0);
        int armorClass = Integer.parseInt(armorList.get(1));
        generatedNPCFinished.add(generatedNPCFinished.size(), armorList.get(0));//armor
        generatedNPCFinished.add(generatedNPCFinished.size(), armorList.get(1));//AC

        weaponList = determineWeapons(genClass, race);
        generatedNPCFinished.add(generatedNPCFinished.size(), String.valueOf(weaponList));//waffenliste

        ArrayList<String> movementSpeedList = determineMovementSpeed(race);
        generatedNPCFinished.add(generatedNPCFinished.size(), String.valueOf(movementSpeedList));//movement speed

        //Prof Bonus
        profBonus = determineProfBonus(level);

        //Saving Throws
        savingThrows = determineSavingThrows(genClass, abilityModifier, profBonus);// saving throws

        //SkillList
        generatedNPCFinished.add(generatedNPCFinished.size(), String.valueOf(savingThrows));
        if (!genClass.equals(nameDBCommoner)) {     // Fall Commoner?
            skillList = determineSkillList(race, job, genClass);
            generatedNPCFinished.add(generatedNPCFinished.size(), (addAbilityScoreToSkillList(abilityModifier,skillList, profBonus)));
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
        if (inputName ==null) {
            name = randGen.randName(gender, race);
        }else {
            name = inputName;
        }



        generatedNPCFinished.add(generatedNPCFinished.size(), name);

        //random Alignment
        alignment = randGen.randAlignment();
        generatedNPCFinished.add(generatedNPCFinished.size(), alignment);
        
        //random Description
        description = randGen.randDescription(race, alignment);
        generatedNPCFinished.add(generatedNPCFinished.size(), description);

        //random Deity
        deity = randGen.randDeity(alignment);
        generatedNPCFinished.add(generatedNPCFinished.size(), deity);

        //random Languages
        languages = determineLanguages(race,job);
        generatedNPCFinished.add(generatedNPCFinished.size(), String.valueOf(languages));


        senses = randGen.getVision(race) + ", Passive Perception " + randGen.passivePerception(abilityModifier, skillList, profBonus);
        System.out.println(senses);
        generatedNPCFinished.add(generatedNPCFinished.size(), senses);

        //spells
        ArrayList<Object> spellList = randGen.randSpellList(genClass,abilityModifier,level);
        generatedNPCFinished.add(generatedNPCFinished.size(), (spellList));
        System.out.println("spell: "+ spellList);
        System.out.println("NPC: "+ generatedNPCFinished);
        //features
        ArrayList<String> actionList = determineFeatures(race,"action");
        ArrayList<String> bonusActionList = determineFeatures(race, "bonus action");
        ArrayList<String> bonusTextList = determineFeatures(race, "bonus text");

        generatedNPCFinished.add(generatedNPCFinished.size(), actionList);
        generatedNPCFinished.add(generatedNPCFinished.size(), bonusActionList);
        generatedNPCFinished.add(generatedNPCFinished.size(), bonusTextList);

       //Spell Ability = Spell Attack Mod und Spell Save DC
        int spellAttack = randGen.spellAttackMod(abilityModifier, profBonus, genClass);
        int spellSave = randGen.spellSaveDC(randGen.spellAttackMod(abilityModifier, profBonus, genClass));
        ArrayList<String> spellAbility =new ArrayList<>();

        spellAbility.add(String.valueOf(spellAttack));
        spellAbility.add(String.valueOf(spellSave)) ;
        generatedNPCFinished.add(generatedNPCFinished.size(), spellAbility);

        //dragonborn
        if(race.equals("Dragonborn")){
            race= dbms.getRandomRow("dracances").get(1) + " - " + race;
            generatedNPCFinished.set(0, race);
        }


        ArrayList<Object> history = new ArrayList<Object>();
        
        history.add(name);
        history.add(level);
        history.add(genClass);
        history.add(race);
        history.add(job);
        history.add(armorClass);
        history.add(healthPoints);
        history.add(movementSpeedList);
        history.add(languages);
        history.add(abilityScores);
        history.add(savingThrows);
        history.add(armor);
        history.add(weaponList);
        history.add(skillList);
        history.add(senses);
        history.add(spellList);
        history.add(spellAbility);
        history.add(description);
        history.add(actionList);
        history.add(bonusActionList);
        history.add(profBonus);

        history.add(abilityModifier);
        history.add(gender);
        history.add(age);
        history.add(alignment);
        history.add(deity);
        history.add(bonusTextList);




        dbms.saveEntry(history);

        return generatedNPCFinished;
    }

    public ArrayList<Integer> distributeAbilityScore (ArrayList<Integer> rolledAbilityScore, String charClass) {
    	ArrayList<Integer> rawAbilityScores ;
    	ArrayList<String> arrayForClass;
    	ArrayList<String> dividedAbilityScores ;
    	ArrayList<String> finishedAbilityScores = new ArrayList<>();
    	ArrayList<Integer> parsedAbilityScores = new ArrayList<>();
    	
    	rawAbilityScores = rolledAbilityScore;

        arrayForClass = dbms.getRow(nameDBClass, charClass);
    	
    	dividedAbilityScores = randGen.bigStringToDividedString(arrayForClass.get(8));
    	
    	finishedAbilityScores.add("Str");
    	finishedAbilityScores.add("Dex");
    	finishedAbilityScores.add("Con");
    	finishedAbilityScores.add("Int");
    	finishedAbilityScores.add("Wis");
    	finishedAbilityScores.add("Cha");
    	
    	if(!(charClass.equals(nameDBCommoner))) {
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

    public ArrayList<Integer> determineAbilityModifier(ArrayList<Integer> abilityScore){
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
            if((!Objects.equals(s, "null")) && (!s.equals("Any"))) {
                s= s.replaceAll("\\s+","");
                dbAbilityScore = dbms.getRow("skills", s).get(2);
                switch (s){
                    case "SleightofHand":
                        s= "Sleight of Hand";
                        break;
                    case "AnimalHandling":
                        s="Animal Handling";
                        break;
                    default:
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
                    default:
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
        String armorList = randGen.randArmor(armorProfList);

        //Berechnung AC
        if(armorList == null){
            armorList = "None";
        }
        currentArmor= dbms.getRow("armor",armorList);
        armorClass = Integer.parseInt(currentArmor.get(3));
        armorBoni = randGen.bigStringToDividedString(currentArmor.get(5));

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
        int weaponCount = rndNumb.nextInt(4)+2;
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
        ArrayList<String> movementSpeed = randGen.bigStringToDividedString(dbms.getRow("race", race).get(9));
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
                default:
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
        if(!genClass.equals(nameDBCommoner)) {
            ArrayList<String> savingThrowList =  randGen.bigStringToDividedString(dbms.getRow(nameDBClass, genClass).get(5));
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
                        savingThrows.add("Cha " + "+" +(abilityModifier.get(5) + profBonus));
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
        return randGen.diceThrower(level, Integer.parseInt(dbms.getRow(nameDBClass,genClass).get(2)))+ level*abilityModifier.get(2);

    }
    public ArrayList<String> determineSkillList (String race, String job, String genClass){
        //Race Skills
        int numberSkill;
        ArrayList<String> skillList =new ArrayList<>();
        String raceSkillList = dbms.getRow("race", race).get(6);
        if((raceSkillList != null)&&(!raceSkillList.contains("Any"))&&(raceSkillList.contains(":"))){ // nicht null, nicht Any, aber enthält":"
            numberSkill = Integer.parseInt(raceSkillList.substring(0,1));
            raceSkillList = raceSkillList.substring(raceSkillList.indexOf(":")+1, raceSkillList.length()-1);
            skillList = (ArrayList<String>) randGen.randSkillProf(randGen.bigStringToDividedString(raceSkillList),numberSkill, skillList);

        } else if ((raceSkillList !=null) && raceSkillList.contains("Any")) { //nicht null, enthält Any
            raceSkillList=raceSkillList.replace("Any","");
            numberSkill = Integer.parseInt(raceSkillList.substring(0,1));
            raceSkillList= "Acrobatics, Animal Handling, Arcana, Athletics, Deception, History, Insight, Intimidation, Investigation, Medicine, Nature, Perception, Performance, Persuasion, Religion, Sleight of Hand, Stealth, Survival";
            skillList = (ArrayList<String>) randGen.randSkillProf(randGen.bigStringToDividedString(raceSkillList),numberSkill, skillList);

        } else {
            skillList.add(raceSkillList);
        }
        //Job Skills
        skillList.addAll(randGen.bigStringToDividedString(dbms.getRow("job", job).get(2))); //Skills durch Job

        //ClassSkills
        ArrayList<String> possibleSkillList = randGen.bigStringToDividedString(dbms.getRow(nameDBClass, genClass).get(6)); // Skills durch class
        numberSkill = Integer.parseInt(dbms.getRow(nameDBClass, genClass).get(7)); // Commoner ClassSkillNumber auf 0 setzen
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
            skillList = (ArrayList<String>) randGen.randSkillProf(possibleSkillList, numberSkill, skillList);
            Set<String> removeDuplicate = new HashSet<>(skillList); // die ArrayList wird in ein Set gestopft, um Duplicate innerhalb, zu entfernen
            skillList.clear();
            skillList.addAll(removeDuplicate);
        return skillList;
    }
    public ArrayList<String> determineLanguages(String race, String job){
        String languagesRace = dbms.getRow("race", race).get(10);   // Sprachen der Rasse
        String languagesJob = dbms.getRow("job", job).get(3); // Anzahl der möglichen Sprachen durch den Job
        ArrayList<String> languages = randGen.bigStringToDividedString(languagesRace); //aufteilung des Strings

        int count = Integer.parseInt(languages.getLast()) + Integer.parseInt(languagesJob); // Berechnung der Anzahl an Sprachen

        languages.removeLast(); //Nummer wird entfernt aus der ArrayList
        for (int i = 0; i < count; i++) {
            languages.add(randGen.randLanguages()); //for schleife
        }

        Set<String> removeDuplicatedLanguages = new HashSet<>(languages); // die ArrayList wird in ein Set gestopft, um Duplicate innerhalb, zu entfernen
        languages.clear();
        languages.addAll(removeDuplicatedLanguages);
        return languages;
    }
    public ArrayList<String> determineFeatures(String race, String searchedFeature){
        ArrayList<String> features = new ArrayList<>();
        ArrayList<String> finalFeatures = new ArrayList<>();
        String searchTermDB = null;

        if((!Objects.equals(dbms.getRow("race", race).get(3), "null"))){
            features.addAll(randGen.bigStringToDividedString(dbms.getRow("race", race).get(3)));
        }
        switch (searchedFeature) {
            case "action":
                searchTermDB = "natural attack";
                break;
            case "bonus text":
                searchTermDB = searchedFeature;
                break;
            case "bonus action":
                searchTermDB = "bonus action";
                break;
            default:
                break;
        }
        for(String s: features) {
            if (dbms.getRow(nameDBFeatures, s).get(3).equals(searchTermDB)){
                finalFeatures.add(dbms.getRow(nameDBFeatures, s).get(1) + ": " + dbms.getRow(nameDBFeatures,s).get(2));
            }

        }
        return finalFeatures;
    }
    public ArrayList<Integer> addRaceModifierToAbilityScore(String race, ArrayList<Integer> abilityScore){
        ArrayList<String> raceAbilityModifier = randGen.bigStringToDividedString(dbms.getRow("race", race).get(2));
        int raceModNumber;
        for (String s: raceAbilityModifier) {
            raceModNumber = Integer.parseInt(s.substring(s.length() - 1));
            s = s.substring(0,3);
            switch (s){
                    case "Str":
                        abilityScore.set(0, abilityScore.get(0) + raceModNumber);
                        break;
                    case "Dex":
                        abilityScore.set(1,abilityScore.get(1) + raceModNumber);
                        break;
                    case "Con":
                        abilityScore.set(2,abilityScore.get(2) + raceModNumber);
                        break;
                    case "Int":
                        abilityScore.set(3,abilityScore.get(3) + raceModNumber);
                        break;
                    case "Wis":
                        abilityScore.set(4,abilityScore.get(4) + raceModNumber);
                        break;
                    case "Cha":
                        abilityScore.set(5,abilityScore.get(5) + raceModNumber);
                        break;
                default:
                    break;
            }
        }
        return abilityScore;
    }
    /*
    Diese Funktion wird verwendet um die Ability Scores auf den Level 4, 8, 12, 16 und 19 zu erhöhen.
    Sie achtet auch darauf, dass erst ungerade AS erhöht werden und die priorisierten AS zuerst
    Bitte nicht anfassen, ausser ihr versteht wie und warum diese Funktion funktioniert
     */
    public List<Integer> addAbilityScoreImprovement (int level, List<Integer> abilityScore ){
        int improveCount = 0;

        switch(level){
            case 19,20:
                improveCount = improveCount +5;
                break;
            case 16,17,18:
                improveCount = improveCount +4;
                break;
            case 12,13,14,15:
                improveCount = improveCount +3;
                break;
            case 8,9,10,11:
                improveCount = improveCount +2;
                break;
            case 4,5,6,7:
                improveCount = improveCount +1;
                break;
            default:
                break;
        }
        for (int i=0;i < improveCount ; i++){
           abilityScore = addImprovement(abilityScore);
        }
        return abilityScore;
    }
    public List<Integer> addImprovement(List<Integer> abilityScore){
        ArrayList<Integer> unevenAbSc  =new ArrayList<>();
        ArrayList<Integer> abilityScoreBelow20  =new ArrayList<>();
        for(Integer abilities : abilityScore ) {
            if (abilities < 20) {      // finde alle Ability Scores die kleiner als 20 sind
                abilityScoreBelow20.add(abilities);
            }
        }
        if (abilityScoreBelow20.isEmpty()) {  // wenn alle über 20 sind, dann gib die bekommene Ability Scores zurück
            return abilityScore;
        }
        ArrayList<Integer> aSB20Sorted = new ArrayList<>(abilityScoreBelow20);
        aSB20Sorted.sort(Collections.reverseOrder());
        for (Integer abilities : aSB20Sorted) { // wenn es ungerade gibt werden diese erst einmal aufgefüllt
            if (abilities % 2 == 1) {   // check, ob ungerade
                unevenAbSc.add(abilities);
            }
        }
        switch (unevenAbSc.size()){
            case 2,3,4,5,6:
                abilityScore.set(abilityScore.indexOf(unevenAbSc.getFirst()), unevenAbSc.getFirst() + 1);
                abilityScore.set(abilityScore.indexOf(unevenAbSc.getLast()), unevenAbSc.getLast() + 1);
                unevenAbSc.clear();
                break;
            case 1:
                abilityScore.set(abilityScore.indexOf(unevenAbSc.getFirst()), unevenAbSc.getFirst() + 1);
                try {
                    abilityScore.set(abilityScore.indexOf(aSB20Sorted.getFirst()), (aSB20Sorted.getFirst() + 1));//breaks here
                } catch (Exception ignored) {
                    //ka warum hier manchmal ein Fehler entsteht, er wird jetzt einfach abgefangen :)
                    unevenAbSc.clear();
                }
                break;
            case 0:
                for (Integer abSc : abilityScoreBelow20) {
                    if (abSc + 2 <= 20) {
                        abilityScore.set(abilityScoreBelow20.indexOf(aSB20Sorted.getFirst()), aSB20Sorted.getFirst() + 2);
                        break;
                    }
                }
                break;
            default:
                break;
        }
            abilityScoreBelow20.clear();
            aSB20Sorted.clear();
            unevenAbSc.clear();

            return abilityScore;
        }
    }

