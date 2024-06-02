package DnDGen.TSWP1;

import DnDGen.DB.DBMS;
import org.springframework.context.ApplicationContext;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class EditNPC {
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


    ArrayList<String> weaponList;
    ArrayList<String> languages;
    ArrayList<String> savingThrows;
    ArrayList<String> description;
    ArrayList<String> skillList = new ArrayList<>();
    List<Integer> abilityScores;
    ArrayList<Integer> abilityModifier;
    String armor;
    int armorClass;
    ArrayList<String> generatedNPCFinished = new ArrayList<>();
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
    ArrayList<String> actionList;
    ArrayList<String> bonusActionList;
    ArrayList<String> bonusTextList;
    ArrayList<String> movementSpeedList;
    ArrayList<String> spellList;
    ArrayList<String> spellAbility;
/*
    eine ArrayList, diese ist wie folgt aufgebaut:
            * (race, level, job, class, AbilityScore, AbilityModifier, HP, Armor, AC, Weapon(), walkingSpeed, SavingThrows, Skills(), Gender, Age, Name, Description, Deity, Languages, Senses, spellList)
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


    public ArrayList<Object> applyEdit(ArrayList<Object> newNPC, ArrayList<Object> oldNPC) {
        /*
        Legende:
            ST: Saving Throws,
            PB: ProfBonus,
            AS: Ability Score,
            AC: Armor Class

           was passiert, wenn man was ändert:

           Level:
            HP, Action, AS(HP,ST,Skills, Spells) , Spells, PB(-> Skills, SavingThrows)

           Class:
            HP, AS Verteilung, ProfWeapon, ProfArmor, ST, Skills, Spells, features, (BonusAction, Action)

           Race:
            AS, features, Age, Skills, description, Language,(BonusAction, Action)

           Job:
            Skills, Language

           AS:
            HP, AC, Skills(Berechnung), Spells(Anzahl), ST(Berechnung)

           Armor:
            AC

           Skills:
            neue Skills berechnen


           Pseudocode:
            Erst checken was sich alles geändert hat, Namen der Änderungen in Liste speichern, in Variablen neue und alte werte speichern

            for(String changes : changeList){

            switch(changes):

                case "class":
                    do stuff;
                    break;

                case "race"

            }

         */
        ArrayList<Object> changedNPC = new ArrayList<>(newNPC);
        ArrayList<String> changeList = findChanges(newNPC, oldNPC);
        if (changeList == null) {
            return null;
        }
        defineVariables(oldNPC);

        ArrayList<String> manuelleChangedSkills = manuellChanges(newNPC, oldNPC, 12);
        ArrayList<String> manuelleChangedLanguage = manuellChanges(newNPC, oldNPC, 19);

        ArrayList<String> editedSkills = new ArrayList<>();
        ArrayList<String> editedLanguages = new ArrayList<>();
        ArrayList<String> editedSkillList = new ArrayList<>();

        languages = manuelleChangedLanguage;
        for (String changes : changeList) {

            switch (changes) {
                case "class":
                    //HP, AS Verteilung, ProfWeapon, ProfArmor, ST, Skills, Spells, features, (BonusAction, Action)
                    genClass = (String) newNPC.get(4);
                    removeRaceAbilityAddition();
                    abilityScores = randNPC.addRaceModifierToAbilityScore(race,randNPC.distributeAbilityScore((ArrayList<Integer>) abilityScores,genClass));
                    if (isThisManuelleChanged(changeList,"healthPoints")){
                        healthPoints = randNPC.calculateHP(genClass, level, abilityModifier);
                    }
                    weaponList = randNPC.determineWeapons(genClass,race);
                    armor = randNPC.determineArmorAndAC(genClass,race,abilityModifier).getFirst();
                    savingThrows = randNPC.determineSavingThrows(genClass,abilityModifier,profBonus);
                    skillList = randNPC.addAbilityScoreToSkillList(abilityModifier,randNPC.determineSkillList(race,job,genClass), profBonus);


                    break;
                case "race":
                    //AS, features, Age, Skills, description, Language,(BonusAction, Action)
                    race = (String) newNPC.get(3);
                    newRaceAbilityScore(newNPC,oldNPC);
                    actionList = randNPC.determineFeatures(race,"action");
                    bonusActionList = randNPC.determineFeatures(race, "bonus action");
                    bonusTextList = randNPC.determineFeatures(race, "bonus text");
                    age = randGen.randAge(race);
                    if(!genClass.equals("Commoner")){
                        skillList = randNPC.determineSkillList(race, job, genClass);
                    } else {
                        skillList.add("none");
                    }
                    String size = randGen.randSize(race);
                    double height = randGen.randHeight(race);
                    description = (ArrayList<String>) oldNPC.get(17);
                    description.set(13, size);
                    description.set(14, Double.toString(height));

                    String languagesRace = dbms.getRow("race",race).get(16);
                    languages.remove(languagesRace);
                    languages.addAll(randNPC.determineLanguages(race, job));
                    break;
                case "level":
                    // HP, Action, AS(HP,ST,Skills, Spells) , Spells, PB(-> Skills, SavingThrows)

                    lookAtAbilityScore(newNPC, oldNPC);
                    abilityModifier = randNPC.determineAbilityModifier((ArrayList<Integer>) abilityScores);

                    healthPoints = randNPC.calculateHP(genClass, level, abilityModifier);
                    break;
                case "job":
                    //Skills, Language
                    String newJob = (String) newNPC.get(4);
                    ArrayList<String> jobSkills = randGen.bigStringToDividedString(dbms.getRow("job", job).get(2));
                    skillList.remove(jobSkills);
                    skillList.addAll(randGen.bigStringToDividedString(dbms.getRow("job",newJob).get(2)));
                    break;
                case "abilityScore":
                    //HP, AC, Skills(Berechnung), Spells(Anzahl), ST(Berechnung)
                    break;
                case "armor":
                    //AC
                    newArmorClass();
                    break;
                case "skills":
                    //neue Skills berechnen
                    skillList = randNPC.addAbilityScoreToSkillList(abilityModifier, skillList, profBonus);
                    break;
                case "deity":
                    deity = (String) newNPC.get(18);
                    break;

                case "name":
                    name = (String) newNPC.get(2);
                    break;

                case "armorClass":
                    armorClass = (int) newNPC.get(7);
                    break;

                case "healthPoints":
                    healthPoints = (int) newNPC.get(8);
                    break;

                case "movementSpeedList":
                    movementSpeedList = (ArrayList<String>) newNPC.get(9);
                    break;

                case "languages":
                    languages = (ArrayList<String>) newNPC.get(10);
                    break;

                case "savingThrows":
                    savingThrows = (ArrayList<String>) newNPC.get(12);
                    break;

                case "weaponList":
                    weaponList = (ArrayList<String>) newNPC.get(14);
                    break;

                case "senses":
                    senses = (String) newNPC.get(16);
                    break;

                case "spellList":
                    spellList = (ArrayList<String>) newNPC.get(17);
                    break;

                case "spellAbility":
                    spellAbility = (ArrayList<String>) newNPC.get(18);
                    break;

                case "description":
                    description = (ArrayList<String>) newNPC.get(19);
                    break;

                case "actionList":
                    actionList = (ArrayList<String>) newNPC.get(20);
                    break;

                case "bonusActionList":
                    bonusActionList = (ArrayList<String>) newNPC.get(21);
                    break;

                case "profBonus":
                    profBonus = (int) newNPC.get(22);
                    break;

                case "abilityModifier":
                    abilityModifier = (ArrayList<Integer>) newNPC.get(23);
                    break;

                case "gender":
                    gender = (String) newNPC.get(24);
                    break;

                case "age":
                    age = (int) newNPC.get(25);
                    break;

                case "alignment":
                    alignment = (String) newNPC.get(26);
                    break;

                case "bonusTextList":
                    bonusTextList = (ArrayList<String>) newNPC.get(28);
                    break;

                default:
                    break;
            }
        }


        return changedNPC;
    }

    /*
        Diese Funktion vergleicht den alten und neuen NPC und gibt eine Liste mit den changes zurück
    */
    public ArrayList<String> findChanges(ArrayList<Object> newNPC, ArrayList<Object> oldNPC) {
        ArrayList<String> formatNPCWithNamesForIndex = new ArrayList<>();
        ArrayList<String> changeList = new ArrayList<>();
        formatNPCWithNamesForIndex.add("name");
        formatNPCWithNamesForIndex.add("level");
        formatNPCWithNamesForIndex.add("class");
        formatNPCWithNamesForIndex.add("race");
        formatNPCWithNamesForIndex.add("job");
        formatNPCWithNamesForIndex.add("armorClass");
        formatNPCWithNamesForIndex.add("healthPoints");
        formatNPCWithNamesForIndex.add("movementSpeedList");
        formatNPCWithNamesForIndex.add("languages");
        formatNPCWithNamesForIndex.add("abilityScores");
        formatNPCWithNamesForIndex.add("savingThrows");
        formatNPCWithNamesForIndex.add("armor");
        formatNPCWithNamesForIndex.add("weaponList");
        formatNPCWithNamesForIndex.add("skillList");
        formatNPCWithNamesForIndex.add("senses");
        formatNPCWithNamesForIndex.add("spellList");
        formatNPCWithNamesForIndex.add("spellAbility");
        formatNPCWithNamesForIndex.add("description");
        formatNPCWithNamesForIndex.add("actionList");
        formatNPCWithNamesForIndex.add("bonusActionList");
        formatNPCWithNamesForIndex.add("profBonus");
        formatNPCWithNamesForIndex.add("abilityModifier");
        formatNPCWithNamesForIndex.add("gender");
        formatNPCWithNamesForIndex.add("age");
        formatNPCWithNamesForIndex.add("alignment");
        formatNPCWithNamesForIndex.add("deity");
        formatNPCWithNamesForIndex.add("bonusTextList");

        //finde die Changes
        for (Object s : newNPC) {
            int indexOfS = newNPC.indexOf(s);
            if (!newNPC.get(indexOfS).equals(oldNPC.get(indexOfS))) {
                changeList.add(formatNPCWithNamesForIndex.get(indexOfS));
            }
        }
        return changeList;
    }

    private ArrayList<Object> editJob(ArrayList<Object> oldNPc, ArrayList<String> manuelleChangedSkills, ArrayList<String> manuelleChangedLanguage) {
        ArrayList<Object> editedJobList = new ArrayList<>();

        languages.addAll(randNPC.determineLanguages(race, job));


        return null;
    }

    private ArrayList<String> manuellChanges(ArrayList<Object> newNPC, ArrayList<Object> oldNPC, int index) {
        ArrayList<String> changedSkills = new ArrayList<>();
        ArrayList<String> oldStats = (ArrayList<String>) oldNPC.get(index);
        ArrayList<String> newStats = (ArrayList<String>) newNPC.get(index);

        for (String skill : newStats) {
            if (!oldStats.contains(skill)) {
                changedSkills.add(skill);
            }
        }
        return changedSkills;
    }

    private void newArmorClass() {
        String justArmor;
        boolean isThereAShield = false;

        if (armor.contains("Shield")) {
            isThereAShield = true;
            justArmor = armor.substring(0, armor.indexOf("+") - 1);
        } else {
            justArmor = armor;
        }

        ArrayList<String> armorDBEntry = dbms.getRow("armor", justArmor);
        int newAC = Integer.parseInt(armorDBEntry.get(3));
        ArrayList<String> armorBoni = randGen.bigStringToDividedString(justArmor);
        String kindOfArmor = armorDBEntry.get(2);
        if ((kindOfArmor.equals("Natural Armor")) || (kindOfArmor.equals("Class Armor"))) {
            for (String s : armorBoni) {
                //für jeden AbilityScore Boni, diesen hinzufügen
                newAC = switch (s) {
                    case "Str" -> newAC + abilityModifier.get(0);
                    case "Dex" -> newAC + abilityModifier.get(1);
                    case "Con" -> newAC + abilityModifier.get(2);
                    case "Int" -> newAC + abilityModifier.get(3);
                    case "Wis" -> newAC + abilityModifier.get(4);
                    case "Cha" -> newAC + abilityModifier.get(5);
                    default -> newAC;
                };
            }
        } else if ((armorBoni.size() == 1) && (armorBoni.contains("Dex"))) {
            newAC = Integer.parseInt(armorDBEntry.get(3)) + abilityModifier.get(1);
        }
        if ((newAC > Integer.parseInt(armorDBEntry.get(4)))) {//überprüfen ob AC im toleranten Bereich liegt
            newAC = Integer.parseInt(armorDBEntry.get(4)); // wenn nein, dann diesen festlegen
        }
        if (isThereAShield) {
            int shieldPercent = rndNumb.nextInt(2);
            if (shieldPercent == 1) {
                armor = armor + " + " + "Shield";
                newAC = newAC + 2;
            }
        }
        armorClass = newAC;

    }


    private void lookAtAbilityScore(ArrayList<Object> newNpc, ArrayList<Object> oldNPC) {
        int newLevel = (int) newNpc.get(1);
        int oldLevel = (int) oldNPC.get(1);
        int levelDiff = 0;
        int deproveCount = 0;
        List<Integer> sortedAS = abilityScores;

        if (oldLevel < newLevel) {
            levelDiff = newLevel - oldLevel;
            abilityScores = randNPC.addAbilityScoreImprovement(levelDiff - 4, abilityScores);
            return;
        } else if (oldLevel > newLevel) {
            levelDiff = oldLevel - newLevel;
        }
        switch (levelDiff) {
            case 19, 20:
                deproveCount = deproveCount + 5;
                break;
            case 16, 17, 18:
                deproveCount = deproveCount + 4;
                break;
            case 12, 13, 14, 15:
                deproveCount = deproveCount + 3;
                break;
            case 8, 9, 10, 11:
                deproveCount = deproveCount + 2;
                break;
            case 4, 5, 6, 7:
                deproveCount = deproveCount + 1;
                break;
            default:
                break;
        }

        for (int rounds = 0; deproveCount > rounds; rounds++) {
            sortedAS.sort(Collections.reverseOrder());
            int randNumb = rndNumb.nextInt(2) + 1;

            switch (randNumb) {
                case 1:
                    sortedAS.set(0, sortedAS.getFirst() - 1);
                    sortedAS.set(0, sortedAS.get(1) - 1);
                    break;
                case 2:
                    sortedAS.set(0, sortedAS.getFirst() - 2);
            }
        }

        abilityScores = sortedAS;
    }


    private void newRaceAbilityScore(ArrayList<Object> newNPC, ArrayList<Object> oldNPC ) {
        String oldRace = (String) oldNPC.get(3);
        ArrayList<String> oldRaceAbilityModifier = randGen.bigStringToDividedString(dbms.getRow("race", oldRace ).get(2));
        int raceModNumber;
        for (String s: oldRaceAbilityModifier) {
            raceModNumber = Integer.parseInt(s.substring(s.length() - 1));
            s = s.substring(0,3);
            switch (s){
                case "Str":
                    abilityScores.set(0, abilityScores.get(0) - raceModNumber);
                    break;
                case "Dex":
                    abilityScores.set(1,abilityScores.get(1) - raceModNumber);
                    break;
                case "Con":
                    abilityScores.set(2,abilityScores.get(2) - raceModNumber);
                    break;
                case "Int":
                    abilityScores.set(3,abilityScores.get(3) - raceModNumber);
                    break;
                case "Wis":
                    abilityScores.set(4,abilityScores.get(4) - raceModNumber);
                    break;
                case "Cha":
                    abilityScores.set(5,abilityScores.get(5) - raceModNumber);
                    break;
                default:
                    break;
            }
        }
        abilityScores = randNPC.addRaceModifierToAbilityScore(race, (ArrayList<Integer>) abilityScores);
        }

    private void defineVariables(ArrayList<Object> oldNPC){
        race = (String) oldNPC.get(0);
        level = (int) oldNPC.get(1);
        job = (String) oldNPC.get(2);
        genClass = (String) oldNPC.get(3);
        abilityScores = (List<Integer>) oldNPC.get(4);
        abilityModifier = (ArrayList<Integer>) oldNPC.get(5);
        healthPoints = (int) oldNPC.get(6);
        armor = (String) oldNPC.get(7);
        armorClass = (int) oldNPC.get(8);
        weaponList = (ArrayList<String>) oldNPC.get(9);
        movementSpeedList = (ArrayList<String>) oldNPC.get(10);
        savingThrows = (ArrayList<String>) oldNPC.get(11);
        skillList = (ArrayList<String>) oldNPC.get(12);
        gender = (String) oldNPC.get(13);
        age = (int) oldNPC.get(14);
        name = (String) oldNPC.get(15);
        alignment = (String) oldNPC.get(16);
        description = (ArrayList<String>) oldNPC.get(17);
        deity = (String) oldNPC.get(18);
        languages = (ArrayList<String>) oldNPC.get(19);
        senses = (String) oldNPC.get(20);
        spellList = (ArrayList<String>) oldNPC.get(21);
        actionList = (ArrayList<String>) oldNPC.get(22);
        bonusActionList = (ArrayList<String>) oldNPC.get(23);
        bonusTextList = (ArrayList<String>) oldNPC.get(24);
        spellAbility = (ArrayList<String>) oldNPC.get(25);
    }

    private boolean isThisManuelleChanged(ArrayList<String> changedList, String changedParameter ){

        for(String s: changedList ) {
            if (s.equals(changedParameter)) {
                return true;
            }
        }
        return false;
    }

    private void removeRaceAbilityAddition() {
        ArrayList<String> raceAbilityModifier = randGen.bigStringToDividedString(dbms.getRow("race", race).get(2));
        int raceModNumber;
        for (String s : raceAbilityModifier) {
            raceModNumber = Integer.parseInt(s.substring(s.length() - 1));
            s = s.substring(0, 3);
            switch (s) {
                case "Str":
                    abilityScores.set(0, abilityScores.get(0) - raceModNumber);
                    break;
                case "Dex":
                    abilityScores.set(1, abilityScores.get(1) - raceModNumber);
                    break;
                case "Con":
                    abilityScores.set(2, abilityScores.get(2) - raceModNumber);
                    break;
                case "Int":
                    abilityScores.set(3, abilityScores.get(3) - raceModNumber);
                    break;
                case "Wis":
                    abilityScores.set(4, abilityScores.get(4) - raceModNumber);
                    break;
                case "Cha":
                    abilityScores.set(5, abilityScores.get(5) - raceModNumber);
                    break;
                default:
                    break;

            }
        }
    }



    }






















