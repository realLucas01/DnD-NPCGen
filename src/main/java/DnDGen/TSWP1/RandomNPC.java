package DnDGen.TSWP1;


import java.util.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.ApplicationContext;

import DnDGen.DB.DBMS;

import static DnDGen.TSWP1.RandomGenerator.bigStringToDividedString;


public class RandomNPC {
	//public RandomGenerator randGen;
	
	/* @author Lucas
	 * 
	 * Aufrufen der GENAUEN DBMS bzw. SpringApplication die gestartet wurde, jede
	 * neu-konstruktion von DBMS wäre sonst leer.
	 */
	ApplicationContext appCtx = DBMS.getApplicationContext();
    DBMS dbms = appCtx.getBean(DBMS.class);

    public String createRandomNPC(String inputRace, String inputClass, String inputJob, Integer inputLevel, String inputAbilityScore, String inputGender){
        RandomGenerator randGen = new RandomGenerator();
        ArrayList<String> generatingNPC;
        ArrayList<String> skillList = new ArrayList<>();
        ArrayList<String> weaponList = new ArrayList<>();
        ArrayList<String> possibleSkillList;
        ArrayList<String> languages;
        String armorList, raceSkillList;
        ArrayList<String> weaponProfList;
        ArrayList<String> armorProfList;
        ArrayList<String> armorBoni;
        ArrayList<String> currentArmor;
        ArrayList<Integer> abilityScores = new ArrayList<>();
        ArrayList<Integer> abilityModifier;
        Random rndNumb = new Random();
        String race, job, genClass, gender, name, description, alignment, deity, languagesRace, languagesJob, kindOfArmor;
        int level, numberSkill, age, count,weaponCount,healthPoints, armorClass = 0;
        Pattern number = Pattern.compile("[0-9]", Pattern.CASE_INSENSITIVE); //zum Überprüfen, ob im String eine Zahl steckt
        Matcher matcherNumber;

        //random Race
        if (inputRace == null){
           race =  randGen.randRace();
        } else {
            race= inputRace;
        }
         System.out.println("Race:" + race);
        //random Level
        if(inputLevel == null){
            level = randGen.randLevel();
        }else {
            level = inputLevel;
        }
         System.out.println("Level:" + level);
        //random Job
        if(inputJob == null){
            job = randGen.randJob();
        }else {
            job = inputJob;
        }
         System.out.println("Job:" + job);
        //random Class
        if((inputClass == null) && (level != 0)){
            genClass = randGen.randCharClass();
        } else if (level == 0) {
            genClass = "Commoner:";    // WUHU Edge Case -> Class == commoner
        } else {
            genClass = inputClass;
        }
         System.out.println("Class:" + genClass);


        //random AbilityScore
       /*switch ((inputAbilityScore)){
            case null:
                //AbilityScore 3d6 verwenden
                abilityScores = randGen.randAbilityScore();
                break;
            case "4d6":
                //Ability Score berechnen mit vorgegebenen Würfel
                ArrayList<Integer> scoreFour = new ArrayList<>();
                ArrayList<Integer> finishedSetForAbilityScores = new ArrayList<>();

                for (int i = 0; i < 6; i++) {
                    scoreFour.add(randGen.diceThrower(1, 6));
                    scoreFour.add(randGen.diceThrower(1, 6));
                    scoreFour.add(randGen.diceThrower(1, 6));
                    scoreFour.add(randGen.diceThrower(1, 6));
                    scoreFour.add(randGen.diceThrower(1, 6));
                    Collections.sort(scoreFour, Collections.reverseOrder());

                    int abilityScore = scoreFour.get(0) + scoreFour.get(1) + scoreFour.get(2);

                    finishedSetForAbilityScores.add(abilityScore);

                    scoreFour.clear();
                }
                Collections.sort(finishedSetForAbilityScores, Collections.reverseOrder());
                abilityScores = finishedSetForAbilityScores;
                break;
           case "5d6":
               randGen.diceThrower(5, 6);
               ArrayList<Integer> scoreFive = new ArrayList<>();
               ArrayList<Integer> finishedSetForAbilityScores = new ArrayList<>();

               for (int i = 0; i < 6; i++) {
                   scoreFive.add(randGen.diceThrower(1, 6));
                   scoreFive.add(randGen.diceThrower(1, 6));
                   scoreFive.add(randGen.diceThrower(1, 6));
                   scoreFive.add(randGen.diceThrower(1, 6));
                   scoreFive.add(randGen.diceThrower(1, 6));
                   scoreFive.add(randGen.diceThrower(1, 6));
                   Collections.sort(scoreFive, Collections.reverseOrder());

                   int abilityScore = scoreFive.get(0) + scoreFive.get(1) + scoreFive.get(2);

                   finishedSetForAbilityScores.add(abilityScore);

                   scoreFive.clear();
               }
               Collections.sort(finishedSetForAbilityScores, Collections.reverseOrder());
               break;
            default:
                //arrays verwenden, nichts berechnen und Inhalt des Arrays verteilen
                //dbms.getRowPref();
        }*/


        //muss dann oben richtig gemacht werden
        abilityScores.addAll(randGen.randAbilityScore());
         System.out.println("AbilityScore:" + abilityScores);

        //distribute AbilityScore
            //Lenlen arbeitet daran
        abilityScores.addAll(distributeAbilityScore(abilityScores,genClass));
        System.out.println("AbilityScoreSorted:" + abilityScores);


        //calc AbilityModifier
        abilityModifier=determinAbilityModifier(abilityScores) ;
        System.out.println("AbilityModifier:" + abilityModifier);


        //calc HP
        healthPoints = randGen.diceThrower(level, Integer.parseInt(dbms.getRow("class",genClass).get(2)))+ level*abilityModifier.get(2);
        System.out.println("HP:" + healthPoints);

        armorProfList = randGen.figureOutProfList("armor", genClass, race);
        //randomArmor
        armorList = randGen.randArmor(armorProfList); // LUCA FIX
        System.out.println("Armor:" + armorList);


        //Berechnung AC
        currentArmor= dbms.getRow("armor",armorList);
        armorClass = Integer.parseInt(currentArmor.get(3));

        kindOfArmor = currentArmor.get(2);
        if((kindOfArmor.equals("Natural Armor"))||(kindOfArmor.equals("Class Armor"))){
            armorBoni = bigStringToDividedString(currentArmor.get(5));
            for(String s: armorBoni){
                //für jeden AbilityScore Boni, diesen hinzufügen
                switch(s){
                    case "Str":
                        armorClass = armorClass + abilityModifier.get(0);
                        break;
                    case "Dex":
                        armorClass = armorClass + abilityModifier.get(1);
                        break;
                    case "Con":
                        armorClass = armorClass + abilityModifier.get(2);
                        break;
                    case "Int":
                        armorClass = armorClass + abilityModifier.get(3);
                        break;
                    case "Wis":
                        armorClass = armorClass + abilityModifier.get(4);
                        break;
                    case "Cha":
                        armorClass = armorClass + abilityModifier.get(5);
                        break;
                }
            }
            if((armorClass> Integer.parseInt(currentArmor.get(4)))&&(!kindOfArmor.equals("Shield"))){//überprüfen ob AC im toleranten Bereich liegt
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
        System.out.println("AC:" + armorClass);


        weaponProfList = randGen.figureOutProfList("weapon", genClass, race);
        //randomWeapon
        weaponCount = rndNumb.nextInt(4);
        for(int i=0; i<weaponCount;i++){
            weaponList.add(randGen.randWeapon(weaponProfList));
        }
        Set<String> removeDuplicateWeapons = new HashSet<>(weaponList); // die ArrayList wird in ein Set gestopft, um Duplicate innerhalb, zu entfernen
        weaponList.clear();
        weaponList.addAll(removeDuplicateWeapons);
        System.out.println("Weapon:" + weaponList);


        //random Skills
        //Race Skills
        raceSkillList= dbms.getRow("race", race).get(6);
        //Matcher matcherNumber = number.matcher(raceSkillList);
        //containsNumber = matcherNumber.find();
        switch (raceSkillList){
            case null:
                break;
            case ":":  // wenn Nummer innerhalb sich befinden
                numberSkill = Integer.parseInt(raceSkillList.substring(0,0)); //
                raceSkillList = raceSkillList.substring(raceSkillList.indexOf(":"), raceSkillList.length());

                skillList = (ArrayList<String>) randGen.randSkillProf(bigStringToDividedString(raceSkillList),numberSkill, skillList);
                break;
            default:
                skillList.addAll(bigStringToDividedString(raceSkillList));
                break;
        }


        //Job Skills
        skillList.addAll(bigStringToDividedString(dbms.getRow("job", job).get(2))); //Skills durch Job

        //ClassSkills
        possibleSkillList = bigStringToDividedString(dbms.getRow("class", genClass).get(6)); // Skills durch class
        numberSkill = Integer.parseInt(dbms.getRow("class", genClass).get(7)); // Commoner ClassSkillNumber auf 0 setzen
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

        System.out.println("SkillList:" + skillList);

        //random Gender
        if (inputGender== null){
            gender = randGen.randGender();
        } else{
            gender = inputGender;
        }
        System.out.println("Gender:" + gender);


        //random Age
        age = randGen.randAge(race);
        System.out.println("Age:" + age);


        //RandomName
        name = randGen.randName(gender,race);

        //random Description
        description = randGen.randDescription(race);

        //random Alignment
        alignment = randGen.randAlignment();

        //random Deity
        deity = randGen.randDeity(alignment);

        //random Languages
        languagesRace = dbms.getRow("race",race).get(10);   // Sprachen der Rasse
        languagesJob = dbms.getRow("job", job).get(3); // Anzahl der möglichen Sprachen durch den Job
        languages = bigStringToDividedString(languagesRace); //aufteilung des Strings

        count = Integer.parseInt(languages.get(languages.size()-1)) + Integer.parseInt(languagesJob); // Berechnung der Anzahl an Sprachen
        languages.remove(languages.size()-1); //Nummer wird entfernt aus der ArrayList
        for ( int i=0; i<count;i++){
            languages.add(randGen.randLanguages()); //for schleife
        }

        Set<String> removeDuplicatedLanguages = new HashSet<>(languages); // die ArrayList wird in ein Set gestopft, um Duplicate innerhalb, zu entfernen
        languages.clear();
        languages.addAll(removeDuplicatedLanguages);

        return "Hi";
    }

    public ArrayList<Integer> distributeAbilityScore (ArrayList<Integer> rolledAbilityScore, String charClass) {
    	
    	ArrayList<Integer> rawAbilityScores = new ArrayList<Integer>();
    	ArrayList<String> arrayForClass = new ArrayList<String>();
    	ArrayList<String> dividedAbilityScores = new ArrayList<String>();
    	ArrayList<String> finishedAbilityScores = new ArrayList<String>();
    	ArrayList<Integer> parsedAbilityScores = new ArrayList<Integer>();
    	
    	rawAbilityScores = rolledAbilityScore;
    	
    	arrayForClass = dbms.getRow("class", charClass);
    	
    	dividedAbilityScores = bigStringToDividedString(arrayForClass.get(8));
    	
    	finishedAbilityScores.add("Str");
    	finishedAbilityScores.add("Dex");
    	finishedAbilityScores.add("Con");
    	finishedAbilityScores.add("Int");
    	finishedAbilityScores.add("Wis");
    	finishedAbilityScores.add("Cha");
    	
    	
    	if(charClass != "Commoner") {
    	Integer i = 0;
    	Integer j = 0;
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
    	for(Integer k = 0; k < 6; k++) {
    		Integer parsedInt = Integer.parseInt(finishedAbilityScores.get(k));
    		parsedAbilityScores.add(parsedInt);
    	}
    	return parsedAbilityScores;
    	} else {
    		
    		Collections.shuffle(rawAbilityScores);
             System.out.println("parsed ability Scores: " + parsedAbilityScores);

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
         System.out.println(abilityModifier);
        return abilityModifier;
    }




}
