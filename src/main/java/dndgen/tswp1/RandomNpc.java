package dndgen.tswp1;

import java.security.SecureRandom;
import java.util.*;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import dndgen.database.DBMS;
import org.springframework.stereotype.Service;

/**
 * The type Random npc.
 */
@Service
public class RandomNpc {

    @Autowired
    public RandomNpc(DBMS dbms) {
        this.dbms = dbms;
    }


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
    Random rndNumb =  new SecureRandom();
    /**
     * The Rand gen.
     */
    RandomGenerator randGen = new RandomGenerator();
    /**
     * The Name db features.
     */
/*
    nicht wundern, diese variablen die hier folgen dienen dazu sich wiederholende Strings bei database aufrufen zu vermeiden
    und um SonarCube zufriedenzustellen
     */
    String nameDBFeatures = "features";
    /**
     * The Name db commoner.
     */
    String nameDBCommoner = "Commoner";
    /**
     * The Name db class.
     */
    String nameDBClass = "class";
    /**
     * The Search term bonus action.
     */
    String searchTermBonusAction = "bonus action";

    Logger logger = Logger.getLogger(getClass().getName());

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

        HISTORY: +2
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

    /**
     * Create random npc npc class.
     *
     * @param inputName         the input name
     * @param inputRace         the input race
     * @param inputClass        the input class
     * @param inputJob          the input job
     * @param inputLevel        the input level
     * @param inputAbilityScore the input ability score
     * @param inputGender       the input gender
     * @return the npc class
     */
    public NpcClass createRandomNPC(String inputName, String inputRace, String inputClass, String inputJob, Integer inputLevel, String inputAbilityScore, String inputGender) {
        NpcClass randNpc = new NpcClass();
        List<String> weaponList;
        List<String> languages;
        List<String> savingThrows;
        List<String> description;
        List<String> skillList = new ArrayList<>();
        List<Integer> abilityScores = new ArrayList<>();
        List<Integer> abilityModifier;
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
        randNpc.setRace(race);
        //random Level
        level = Objects.requireNonNullElseGet(inputLevel, () -> randGen.randLevel());
        randNpc.setLevel(level);
        //random Job
        if (inputJob == null) {
            job = randGen.randJob();
        } else {
            job = inputJob;
        }
        randNpc.setJob(job);
        //random Class
        if ((inputClass == null) && (level != 0)) {
            genClass = randGen.randCharClass();
        } else if (level == 0) {
            genClass = nameDBCommoner;
        } else {
            genClass = inputClass;
            if(inputClass.equals(nameDBCommoner)){level=0;randNpc.setLevel(level);}
        }
        randNpc.setGenClass((genClass));
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
                List<String> abilityScoreNew = randGen.bigStringToDividedString(inputAbilityScore);
                List<Integer> abilityScoreNewInteger = new ArrayList<>();
                for (String abilityStr : abilityScoreNew) {
                    abilityScoreNewInteger.add(Integer.parseInt(abilityStr));
                }
                abilityScores.addAll(abilityScoreNewInteger);
                break;
        }

        //distribute AbilityScore
        abilityScores = distributeAbilityScore(abilityScores, genClass);
        abilityScores =  addRaceModifierToAbilityScore(race,abilityScores);
        abilityScores =  abilityScoreImprovement(level, abilityScores, genClass);
        randNpc.setAbilityScores(abilityScores);
        //calc AbilityModifier
        abilityModifier =  determineAbilityModifier(abilityScores);
        randNpc.setAbilityModifier(abilityModifier);

        //calc HP
        healthPoints = calculateHP(genClass, level, abilityModifier);
        randNpc.setHealthPoints(healthPoints);

        determineArmorAndAC(genClass, race, randNpc);



        weaponList = determineWeapons(genClass, race);
        randNpc.setWeaponList(weaponList);//waffenliste

        List<String> movementSpeedList = determineMovementSpeed(race);
        randNpc.setMovementSpeedList(movementSpeedList);//movement speed

        //Prof Bonus
        profBonus = determineProfBonus(level);
        randNpc.setProfBonus(profBonus);

        //Saving Throws
        savingThrows = determineSavingThrows(genClass, abilityModifier, profBonus);// saving throws
        randNpc.setSavingThrows(savingThrows);

        //SkillList
        if (!genClass.equals(nameDBCommoner)) {     // Fall Commoner?
            skillList = determineSkillList(race, job, genClass);
            randNpc.setSkillList(addAbilityScoreToSkillList(abilityModifier,skillList, profBonus));
        } else {
            skillList.add("none");
            randNpc.setSkillList(skillList);
        }

        //random Gender
        if (inputGender == null) {
            gender = randGen.randGender();
        } else {
            gender = inputGender;
        }
        randNpc.setGender(gender);

        //random Age
        age = randGen.randAge(race);
        randNpc.setAge(age);

        //RandomName
        if (inputName ==null) {
            name = randGen.randName(gender, race);
        }else {
            name = inputName;
        }
        randNpc.setName(name);

        //random Alignment
        alignment = randGen.randAlignment();
        randNpc.setAlignment(alignment);
        
        //random Description
        description = randGen.randDescription(race);
        randNpc.setDescription(description);

        //random Deity
        deity = randGen.randDeity(alignment);
        randNpc.setDeity(deity);

        //random Languages
        languages = determineLanguages(race,job);
        randNpc.setLanguages(languages);

        senses = randGen.getVision(race) + ", Passive Perception " + randGen.passivePerception(abilityModifier, skillList, profBonus);
        randNpc.setSenses(senses);

        randNpc = randGen.randomSpellsForClass(randNpc);
        //spells
        List<Object> spellList =  randNpc.getSpellList();
        randGen.addRaceCantripsAndSpells(randNpc);
        randNpc.setSpellList(spellList);
        //features
        List<String> actionList = determineFeatures(race,"action");
        List<String> bonusActionList = determineFeatures(race, searchTermBonusAction);
        List<String> bonusTextList = determineFeatures(race, "bonus text");

        randNpc.setActionList(actionList);
        randNpc.setBonusActionList(bonusActionList);
        randNpc.setBonusTextList(bonusTextList);

       //Spell Ability = Spell Attack Mod und Spell Save DC
        int spellAttack = randGen.spellAttackMod(abilityModifier, profBonus, genClass);
        int spellSave = randGen.spellSaveDC(randGen.spellAttackMod(abilityModifier, profBonus, genClass));
        List<String> spellAbility =new ArrayList<>();

        spellAbility.add(String.valueOf(spellAttack));
        spellAbility.add(String.valueOf(spellSave)) ;
        randNpc.setSpellAbility(spellAbility);
        /*vorerst ausser betrieb genommen, noch fraglich ob sie wieder in betrieb genommen wird
        if(race.equals("Dragonborn")){
            race= dbms.getRandomRow("dracances").get(1) + " - " + race;
        randNpc.setRace(race);
        }

         */
        randNpc.saveNpcInHistory(); // speichern in der History

        //randNpc.exportNPC(); // NPC wird in eine .txt exportiert
        return randNpc;
    }

    /**
     * Distribute ability score list.
     *
     * @param rolledAbilityScore the rolled ability score
     * @param charClass          the char class
     * @return the list
     */
    public List<Integer> distributeAbilityScore (List<Integer> rolledAbilityScore, String charClass) {
    	List<Integer> rawAbilityScores ;
    	List<String> arrayForClass;
    	List<String> dividedAbilityScores ;
    	List<String> finishedAbilityScores = new ArrayList<>();
    	List<Integer> parsedAbilityScores = new ArrayList<>();
    	
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

    /**
     * Determine ability modifier list.
     *
     * @param abilityScore the ability score
     * @return the list
     */
    public List<Integer> determineAbilityModifier(List<Integer> abilityScore){
        List<Integer> abilityModifier = new ArrayList<>();
        float calcMod;
        for (Integer i: abilityScore){
            calcMod = (float) (i-10)/ 2 - 0.2f;
            abilityModifier.add(Math.round(calcMod));
        }
        return abilityModifier;
    }

    /**
     * Add ability score to skill list.
     *
     * @param abilityModifier the ability modifier
     * @param skillList       the skill list
     * @param profBonus       the prof bonus
     * @return the list
     */
    public List<String> addAbilityScoreToSkillList(List<Integer> abilityModifier, List<String> skillList, int profBonus ){
        List<String> skillListWithNumbers = new ArrayList<>();
        String dbAbilityScore ;
        for (String s : skillList){
            if((!Objects.equals(s, "null")) && (!s.equals("Any"))) {
                s= s.replaceAll("\\s+","");
                dbAbilityScore = dbms.getRow("skills", s).get(2);
                switch (s) {
                    case "SleightofHand" -> s = "Sleight of Hand";
                    case "AnimalHandling" -> s = "Animal Handling";
                    default -> {
                        //hi
                    }
                }
                switch (dbAbilityScore) {
                    case "Str" -> skillListWithNumbers.add(s + " +" + (abilityModifier.getFirst() + profBonus));
                    case "Dex" -> skillListWithNumbers.add(s + "+" + (abilityModifier.get(1) + profBonus));
                    case "Con" -> skillListWithNumbers.add(s + " +" + (abilityModifier.get(2) + profBonus));
                    case "Int" -> skillListWithNumbers.add(s + " +" + (abilityModifier.get(3) + profBonus));
                    case "Wis" -> skillListWithNumbers.add(s + " +" + (abilityModifier.get(4) + profBonus));
                    case "Cha" -> skillListWithNumbers.add(s + " +" + (abilityModifier.get(5) + profBonus));
                    default -> {
                            //hi
                        }
                }
            }
        }
        return skillListWithNumbers;
    }

    /**
     * Determine armor and ac list.
     *
     * @param genClass        the gen class
     * @param race            the race
     * @param crtNPC the ability modifier
     */
    /*
    Diese Funktion wurde ausgelagert. Sie möchte, um glücklich zu sein, die klasse, rasse und die ability Modifier.
    Am Ende wird zurückgegeben eine ArrayList<> mit (Die ausgewählte Rüstung, die Armor Class vollständig berechnet)
     */
    public void determineArmorAndAC(String genClass, String race, NpcClass crtNPC){
        List<String> armorProfList = randGen.figureOutProfList("armor", genClass, race);

        boolean isThereAShield = armorProfList.contains("Shield");
        String armor = randGen.randArmor(armorProfList);

        calculateArmorClass(armor,crtNPC,isThereAShield);
    }

    public void calculateArmorClass(String armor, NpcClass crtNPC, boolean shield) {
        //Berechnung AC
        List<String> armorBoni;
        List<String> currentArmor;
        String kindOfArmor;
        List<Integer> abilityModifier = crtNPC.getAbilityModifier();
        int armorClass;
        if (armor == null) {
            armor = "None";
        }
        currentArmor = dbms.getRow("armor", armor);
        armorClass = Integer.parseInt(currentArmor.get(3));
        armorBoni = randGen.bigStringToDividedString(currentArmor.get(5));

        kindOfArmor = currentArmor.get(2);
        if ((kindOfArmor.equals("Natural Armor")) || (kindOfArmor.equals("Class Armor"))) {
            for (String s : armorBoni) {
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
        } else if ((armorBoni.size() == 1) && (armorBoni.contains("Dex"))) {
            armorClass = Integer.parseInt(currentArmor.get(3)) + abilityModifier.get(1);
        }
        if ((armorClass > Integer.parseInt(currentArmor.get(4)))) {//überprüfen ob AC im toleranten Bereich liegt
            armorClass = Integer.parseInt(currentArmor.get(4)); // wenn nein, dann diesen festlegen
        }
        if (shield) {
            int shieldPercent = rndNumb.nextInt(2);
            if (shieldPercent == 1) {
                armor += " + Shield";
                armorClass = armorClass + 2;
            }

        }
        crtNPC.setArmor(armor);
        crtNPC.setArmorClass(armorClass);
    }

    /**
     * Determine weapons list.
     *
     * @param genClass the gen class
     * @param race     the race
     * @return the list
     */
    public List<String> determineWeapons(String genClass, String race){
        List<String> weaponProfList = randGen.figureOutProfList("weapon", genClass, race);
        List<String> weaponList = new ArrayList<>();
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

    /**
     * Determine movement speed list.
     *
     * @param race the race
     * @return the list
     */
    public List<String> determineMovementSpeed(String race) {
        //creature Speed
        List<String> movementSpeed = randGen.bigStringToDividedString(dbms.getRow("race", race).get(9));
        List<String> movementSpeedList = new ArrayList<>();
        for (String s : movementSpeed) {
            switch (s.substring(s.length() - 1)) {
                case "w" ->
                        movementSpeedList.add(movementSpeed.get(movementSpeed.indexOf(s)).substring(0, 2) + " ft" + " walking Speed");
                case "f" ->
                        movementSpeedList.add(movementSpeed.get(movementSpeed.indexOf(s)).substring(0, 2) + " ft" + " flying Speed");
                case "b" ->
                        movementSpeedList.add(movementSpeed.get(movementSpeed.indexOf(s)).substring(0, 2) + " ft" + " burrowing Speed");
                default -> { //hi
                }
            }
        }
        return movementSpeedList;
    }

    /**
     * Determine prof bonus int.
     *
     * @param level the level
     * @return the int
     */
    public int determineProfBonus(int level) {
       return Integer.parseInt(dbms.getRow("level", String.valueOf(level)).get(2));
    }

    /**
     * Determine saving throws list.
     *
     * @param genClass        the gen class
     * @param abilityModifier the ability modifier
     * @param profBonus       the prof bonus
     * @return the list
     */
    public List<String> determineSavingThrows(String genClass, List<Integer> abilityModifier, int profBonus){
        List<String> savingThrows = new ArrayList<>();
        if(!genClass.equals(nameDBCommoner)) {
            List<String> savingThrowList = randGen.bigStringToDividedString(dbms.getRow(nameDBClass, genClass).get(5));
            for (String s : savingThrowList) {
                //für jeden AbilityScore Boni, diesen hinzufügen
                switch (s) {
                    case "Str" -> savingThrows.add("Str " + "+" + (abilityModifier.getFirst() + profBonus));
                    case "Dex" -> savingThrows.add("Dex " + "+" + (abilityModifier.get(1) + profBonus));
                    case "Con" -> savingThrows.add("Con " + "+" + (abilityModifier.get(2) + profBonus));
                    case "Int" -> savingThrows.add("Int " + "+" + (abilityModifier.get(3) + profBonus));
                    case "Wis" -> savingThrows.add("Wis " + "+" + (abilityModifier.get(4) + profBonus));
                    case "Cha" -> savingThrows.add("Cha " + "+" + (abilityModifier.get(5) + profBonus));
                    default -> savingThrows.add("none");
                }
            }
            savingThrows.remove("none");
        }else{
            savingThrows.add("none");
        }
        return savingThrows;
    }

    /**
     * Calculate hp int.
     *
     * @param genClass        the gen class
     * @param level           the level
     * @param abilityModifier the ability modifier
     * @return the int
     */
    public int calculateHP(String genClass, int level, List<Integer> abilityModifier ){
        if (level == 0){
            level = 1;
        }
        int hp = randGen.diceThrower(level, Integer.parseInt(dbms.getRow(nameDBClass,genClass).get(2)))+ level*abilityModifier.get(2);
        if (hp == 0 ) hp =1;
        return hp;

    }

    /**
     * Determine skill list.
     *
     * @param race     the race
     * @param job      the job
     * @param genClass the gen class
     * @return the list
     */
    public List<String> determineSkillList (String race, String job, String genClass){
        //Race Skills
        int numberSkill;
        List<String> skillList =new ArrayList<>();
        String raceSkillList = dbms.getRow("race", race).get(6);
        if((raceSkillList != null)&&(!raceSkillList.contains("Any"))&&(raceSkillList.contains(":"))){ // nicht null, nicht Any, aber enthält einen Doppelpunkt
            numberSkill = Integer.parseInt(raceSkillList.substring(0,1));
            raceSkillList = raceSkillList.substring(raceSkillList.indexOf(":")+1, raceSkillList.length()-1);
            skillList = randGen.randSkillProf(randGen.bigStringToDividedString(raceSkillList),numberSkill, skillList);

        } else if ((raceSkillList !=null) && raceSkillList.contains("Any")) { //nicht null, enthält Any
            raceSkillList=raceSkillList.replace("Any","");
            numberSkill = Integer.parseInt(raceSkillList.substring(0,1));
            raceSkillList= "Acrobatics, Animal Handling, Arcana, Athletics, Deception, History, Insight, Intimidation, Investigation, Medicine, Nature, Perception, Performance, Persuasion, Religion, Sleight of Hand, Stealth, Survival";
            skillList = randGen.randSkillProf(randGen.bigStringToDividedString(raceSkillList),numberSkill, skillList);

        } else {
            skillList.add(raceSkillList);
        }
        //Job Skills
        skillList.addAll(randGen.bigStringToDividedString(dbms.getRow("job", job).get(2))); //Skills durch Job

        //ClassSkills
        List<String> possibleSkillList = randGen.bigStringToDividedString(dbms.getRow(nameDBClass, genClass).get(6)); // Skills durch class
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
            skillList = randGen.randSkillProf(possibleSkillList, numberSkill, skillList);
            Set<String> removeDuplicate = new HashSet<>(skillList); // die ArrayList wird in ein Set gestopft, um Duplicate innerhalb, zu entfernen
            skillList.clear();
            skillList.addAll(removeDuplicate);
        return skillList;
    }

    /**
     * Determine languages list.
     *
     * @param race the race
     * @param job  the job
     * @return the list
     */
    public List<String> determineLanguages(String race, String job){
        String languagesRace = dbms.getRow("race", race).get(10);   // Sprachen der Rasse
        String languagesJob = dbms.getRow("job", job).get(3); // Anzahl der möglichen Sprachen durch den Job
        List<String> languages =  randGen.bigStringToDividedString(languagesRace); //aufteilung des Strings

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

    /**
     * Determine features list.
     *
     * @param race            the race
     * @param searchedFeature the searched feature
     * @return the list
     */
    public List<String> determineFeatures(String race, String searchedFeature){
        List<String> features = new ArrayList<>();
        List<String> finalFeatures = new ArrayList<>();
        String searchTermDB = null;

        //überprüfung, ob null
        List<String> raceData = dbms.getRow("race", race);
        if (raceData != null && !Objects.equals(raceData.get(3), "null")) {
            features.addAll(randGen.bigStringToDividedString(raceData.get(3)));
        }
        switch (searchedFeature) {
            case "action" -> searchTermDB = "natural attack";
            case "bonus text" -> searchTermDB = searchedFeature;
            case "bonus action" -> searchTermDB = searchTermBonusAction;
            default ->{ //hi
                 }

        }
        for(String feature: features) {
            List<String> featureData = dbms.getRow(nameDBFeatures, feature);
            if (featureData.get(3).equals(searchTermDB)){
                finalFeatures.add(featureData.get(1) + ": " + featureData.get(2));            }
        }
        return finalFeatures;
    }

    /**
     * Add race modifier to ability score list.
     *
     * @param race         the race
     * @param abilityScore the ability score
     * @return the list
     */
    public List<Integer> addRaceModifierToAbilityScore(String race, List<Integer> abilityScore){
        List<String> raceAbilityModifier = randGen.bigStringToDividedString(dbms.getRow("race", race).get(2));
        int raceModNumber;
        for (String s: raceAbilityModifier) {
            raceModNumber = Integer.parseInt(s.substring(s.length() - 1));
            s = s.substring(0,3);
            switch (s) {
                case "Str" -> abilityScore.set(0, abilityScore.getFirst() + raceModNumber);
                case "Dex" -> abilityScore.set(1, abilityScore.get(1) + raceModNumber);
                case "Con" -> abilityScore.set(2, abilityScore.get(2) + raceModNumber);
                case "Int" -> abilityScore.set(3, abilityScore.get(3) + raceModNumber);
                case "Wis" -> abilityScore.set(4, abilityScore.get(4) + raceModNumber);
                case "Cha" -> abilityScore.set(5, abilityScore.get(5) + raceModNumber);
                default -> { //hi
                }
            }
        }
        return abilityScore;
    }
    public List<Integer> abilityScoreImprovement(Integer level, List<Integer> abilityScore, String classNameNPC){
        int improveCount = Math.max(0, level/4);
        if(level == 20) improveCount--;
        if(level == 19) improveCount++;

        for (int i=0; i < improveCount; i++){
            abilityScore = addImprovement(abilityScore,classNameNPC); // aufruf
        }
        return abilityScore;
    }
    public List<Integer> addImprovement (List<Integer> abilityScore, String classNameNPC) {
        /*
            check nach der Klasse, aus der Klasse werden die höchsten AS genommen und überprüft,
            wenn beide ungerade beide um eins erhöhen, wenn beide gerade und unter 20 wird eins erhöht,
            wenn beide unter 20 werden die nächsten ungeraden um eins erhöht
         */
        if (classNameNPC.equals("Commoner")) {
            //System.out.println("Commoner, abort");
            return abilityScore;
        }
        List<String> classASDistributionWeight = randGen.bigStringToDividedString(dbms.getRow(nameDBClass, classNameNPC).get(8));
        List<String> mostImportantAS = new ArrayList<>();
        mostImportantAS.add(classASDistributionWeight.get(0));
        mostImportantAS.add(classASDistributionWeight.get(1));
        List<Integer> aSIndex = new ArrayList<>();
        List<Integer> vIAS = new ArrayList<>();
        // for (String abSc : classASDistributionWeight) {
        for (String abSc : mostImportantAS) {
            switch (abSc) { // Werte über 20 werden nicht berücksichtigt
                case "Str":
                    if (abilityScore.getFirst() < 20) {
                        vIAS.add(abilityScore.getFirst());
                        aSIndex.add(0);
                    }
                    continue;
                case "Dex":
                    if (abilityScore.get(1) < 20) {
                        aSIndex.add(1);
                        vIAS.add(abilityScore.get(1));
                    }
                    break;
                case "Con":
                    if (abilityScore.get(2) < 20) {
                        aSIndex.add(2);
                        vIAS.add(abilityScore.get(2));
                    }
                    break;

                case "Int":
                    if (abilityScore.get(3) < 20) {
                        aSIndex.add(3);
                        vIAS.add(abilityScore.get(3));
                    }
                    break;

                case "Wis":
                    if (abilityScore.get(4) < 20) {
                        aSIndex.add(4);
                        vIAS.add(abilityScore.get(4));
                    }
                    break;

                case "Cha":
                    if (abilityScore.get(5) < 20) {
                        aSIndex.add(5);
                        vIAS.add(abilityScore.get(5));
                    }
                    break;
                default:
                    logger.info("Fehler:AbilityScore Improvement Nr. 1");
                    break;
            }
        }
        return aSImprovement(abilityScore,aSIndex,vIAS);
    }
    public List<Integer> aSImprovement(List<Integer> abilityScore, List<Integer> aSIndex, List<Integer> vIAS){
            int unevenCount = 0;
            for (Integer aS : vIAS) { // check wie viele ungerade sind und unter 20
                if (aS < 20) {
                    unevenCount = unevenCount + isItUneven(aS);
                }
            }
            //wie groß die Liste ist
            int size = vIAS.size();
            Random randOrNot = new SecureRandom();
            switch (size) {
                case 0:// beide werte sind über 20, andere Werte müssen überprüft werden
                    for(int i =0; i<2;i++){
                        abilityScore = increaseSecOrThirdAS(abilityScore);
                    }
                    break;
                case 1: // einer unter zwanzig, überprüfen ob dieser um 2 erhöht werden kann, ansonsten weiter der liste nach
                    if (vIAS.getFirst() <= 18) { // einen um 2 erhöht
                        abilityScore.set(aSIndex.getFirst(), vIAS.getFirst() + 2);
                        return abilityScore;
                    }
                    if (vIAS.getFirst() == 19) {// einer der wichtigen danach einer der übrigen überprüft, ob unter 20 und ungerade
                        abilityScore.set(aSIndex.getFirst(), vIAS.getFirst() + 1);
                        // ein weiterer der zweitrangigen als nächstes
                        abilityScore = increaseSecOrThirdAS(abilityScore);
                    }
                    break;
                case 2: //beide unter 20, überprüfen, ob ein oder beide ungerade sind, wenn ungerade wird der zweite punkt zufällig verteilt
                    switch (unevenCount) {
                        case 0:// beide sind ungerade, beide werden um 1 erhöht
                            abilityScore.set(aSIndex.getFirst(), vIAS.getFirst() + 1);
                            abilityScore.set(aSIndex.getLast(), vIAS.getLast() + 1);
                            break;
                        case 1: // einer ist ungerade, einer gerade
                            for (Integer aS : vIAS) { // der ungerade wird erhöht
                                if (aS % 2 == 1) {
                                    abilityScore.set(aSIndex.get(vIAS.indexOf(aS)), aS + 1);
                                    vIAS.set(vIAS.indexOf(aS),aS+1);
                                }
                            }
                            int randInd = randOrNot.nextInt(2);
                            //int randInd=1;
                            if (randInd == 0) { // einer der beiden wird zufällig erhöht
                                abilityScore.set(aSIndex.getFirst(), vIAS.getFirst() + 1);
                            } else {
                                abilityScore.set(aSIndex.getLast(), vIAS.getLast() + 1);
                            }
                            break;
                        case 2: // beide sind gerade, der wichtigste/höchste wird um 2 erhöht
                            abilityScore.set(aSIndex.getFirst(), vIAS.getFirst() + 2);
                            break;
                        default:
                            //Fehler
                            logger.info("Fehler ASI 1");
                            break;
                    }
                    break;
                default:
                    //Fehler
                    logger.info("Fehler ASI 2");
                    break;
            }
        return abilityScore;
    }
    public Integer isItUneven(Integer numb){
        if(numb%2==1) return 0; else return 1;
    }

    /**
     * Sollten die Priorisierten AS nicht weiter erhöht werden können, werden die restlichen AS erhöht
     * Priorität unter 20 und ungerade
     *
     * @param abilityScore wird übergeben, bearbeitet und zurückgegeben
     * @return abilityScore
     */
    public List<Integer> increaseSecOrThirdAS(List<Integer> abilityScore){
        List<Integer> secondaryAS = new ArrayList<>();
        List<Integer> secondaryIndexList = new ArrayList<>();
        List<Integer> thirdAS = new ArrayList<>();
        List<Integer> thirtIndexList = new ArrayList<>();
        Random randOrNot = new SecureRandom();
        for (int i = 0; i <= 5; i++) {
            if ((abilityScore.get(i) < 20) && (abilityScore.get(i) % 2 == 1)) {// wert ist unter 20 und ungerade, betrifft die AS ohne Priorität 1 und 2
                secondaryAS.add(abilityScore.get(i));
                secondaryIndexList.add(i);
            } else if (abilityScore.get(i) < 20) { // sollte es nicht ungerade sein aber unter 20
                thirdAS.add(abilityScore.get(i));
                thirtIndexList.add(i);
            }
        }
        if ((secondaryAS.isEmpty()) && (thirdAS.isEmpty())) {// wenn listen leer, müssten alle werte über 20 sein
            return abilityScore;
        }
        else if ((!secondaryAS.isEmpty())&&(thirdAS.isEmpty())) {
            int randDistribution = randOrNot.nextInt(secondaryAS.size());
            abilityScore.set(secondaryIndexList.get(randDistribution),secondaryAS.get(randDistribution)+1);
            return abilityScore;
        }
        else if ((secondaryAS.isEmpty())&&(!thirdAS.isEmpty())) {
            int randDistribution = randOrNot.nextInt(thirdAS.size()); // zufällig was ausgewählt werden soll
            abilityScore.set(thirtIndexList.get(randDistribution),thirdAS.get(randDistribution)+1);
            return abilityScore;
        }
        int randDistribution= randOrNot.nextInt(secondaryAS.size());
        abilityScore.set(secondaryIndexList.get(randDistribution),secondaryAS.get(randDistribution)+1);
        return abilityScore; // sicherheitshalber
    }
    /**
     * Zwei Abilitys werden zufällig um eins erhöht, diese Zahl kann derzeit 20 überschreiten
     * @param abilityScore von dem NPC, wird wieder zurückgegeben
     * @return abilityScore
     */
    public List<Integer> addRandomImprovement(List<Integer> abilityScore){
        Random rndNumber =  new SecureRandom();

        for(int i=0; i<2; i++ ){
            int randIncreaseIndex = rndNumber.nextInt(6)+1;
            abilityScore.set(randIncreaseIndex, abilityScore.get(randIncreaseIndex)+1);
        }
        return abilityScore;
    }
}

