package dndgen.tswp1;

import dndgen.database.DBMS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * The type Edit npc.
 */
public class EditNpc {
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
    Random rndNumb = new SecureRandom();
    /**
     * The Rand gen.
     */
    RandomGenerator randGen = new RandomGenerator();
    /**
     * The Rand npc.
     */
    @Autowired
    private RandomNpc randNpc;
    //RandomNpc randNpc = new RandomNpc();


    /**
     * The Weapon list.
     */
    ArrayList<String> weaponList;
    /**
     * The Languages.
     */
    ArrayList<String> languages;
    /**
     * The Saving throws.
     */
    ArrayList<String> savingThrows;
    /**
     * The Description.
     */
    ArrayList<String> description;
    /**
     * The Skill list.
     */
    ArrayList<String> skillList = new ArrayList<>();
    /**
     * The Ability scores.
     */
    List<Integer> abilityScores;
    /**
     * The Ability modifier.
     */
    ArrayList<Integer> abilityModifier;
    /**
     * The Armor.
     */
    String armor;
    /**
     * The Armor class.
     */
    int armorClass;
    /**
     * The Generated npc finished.
     */
    List<String> generatedNpcFinished = new ArrayList<>();
    /**
     * The Race.
     */
    String race;
    /**
     * The Job.
     */
    String job;
    /**
     * The Gen class.
     */
    String genClass;
    /**
     * The Gender.
     */
    String gender;
    /**
     * The Name.
     */
    String name;
    /**
     * The Alignment.
     */
    String alignment;
    /**
     * The Deity.
     */
    String deity;
    /**
     * The Senses.
     */
    String senses;
    /**
     * The Level.
     */
    int level;
    /**
     * The Age.
     */
    int age;
    /**
     * The Prof bonus.
     */
    int profBonus;
    /**
     * The Health points.
     */
    int healthPoints;
    /**
     * The Action list.
     */
    ArrayList<String> actionList;
    /**
     * The Bonus action list.
     */
    ArrayList<String> bonusActionList;
    /**
     * The Bonus text list.
     */
    ArrayList<String> bonusTextList;
    /**
     * The Movement speed list.
     */
    ArrayList<String> movementSpeedList;
    /**
     * The Spell list.
     */
    ArrayList<String> spellList;
    /**
     * The Spell ability.
     */
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


    /**
     * Apply edit list.
     *
     * @param newNpc the new npc
     * @param oldNpc the old npc
     * @return the list
     */
    public List<Object> applyEdit(List<Object> newNpc, List<Object> oldNpc) {
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

        /*
            was nach dem neuen Stand berechnet werden möchte, wenn sich dies ändert:
              Class:
                - profArmor :
                       - AC
                - saving throws
                - Skills (zahlen)
                - AbilityScoreDistripution
                - Spells (Anzahl)

              Race:
                -



         */
        ArrayList<Object> changedNpc = new ArrayList<>(newNpc);
        List<String> changeList = findChanges(newNpc, oldNpc);
        if (changeList == null) {
            return new ArrayList<>();
        }
        defineVariables(oldNpc);

        List<String> manuelleChangedSkills = manuellChanges(newNpc, oldNpc, 12);
        List<String> manuelleChangedLanguage = manuellChanges(newNpc, oldNpc, 19);

        List<String> editedSkills = new ArrayList<>();
        List<String> editedLanguages = new ArrayList<>();
        List<String> editedSkillList = new ArrayList<>();

        languages = (ArrayList<String>) manuelleChangedLanguage;
        for (String changes : changeList) {

            switch (changes) {
                case "class":
                    //wird in UIHistoryEditUpdate übernommen
                     genClass = (String) newNpc.get(4);


                    break;
                case "race":
                    //wird in UIHIstoryEditUpdate übernommen
                    race = (String) newNpc.get(5);
                    break;
                case "level":
                    // HP, Action, AS(HP,ST,Skills, Spells) , Spells, PB(-> Skills, SavingThrows)

                    lookAtAbilityScore(newNpc, oldNpc);
                    abilityModifier = (ArrayList<Integer>) randNpc.determineAbilityModifier( abilityScores);

                    healthPoints = randNpc.calculateHP(genClass, level, abilityModifier);
                    actionList = (ArrayList<String>) randNpc.determineFeatures(race,"action");
                    bonusActionList = (ArrayList<String>) randNpc.determineFeatures(race,"bonus action");
                    profBonus = randNpc.determineProfBonus(level);
                    skillList = (ArrayList<String>) randNpc.addAbilityScoreToSkillList(abilityModifier,removePlusNumberInAStringList(skillList),profBonus);
                    savingThrows = (ArrayList<String>) randNpc.determineSavingThrows(genClass,abilityModifier,profBonus);
                    spellAbility.set(0,String.valueOf(randGen.spellAttackMod(abilityModifier, profBonus, genClass)));
                    spellAbility.set(1,String.valueOf(randGen.spellSaveDC(randGen.spellAttackMod(abilityModifier, profBonus, genClass))));


                    break;
                case "job":
                    //Skills, Language
                    String newJob = (String) newNpc.get(4);
                    ArrayList<String> jobSkills = (ArrayList<String>) randGen.bigStringToDividedString(dbms.getRow("job", job).get(2));
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
                    skillList = (ArrayList<String>) randNpc.addAbilityScoreToSkillList(abilityModifier, skillList, profBonus);
                    break;
                case "deity":
                    deity = (String) newNpc.get(18);
                    break;

                case "name":
                    name = (String) newNpc.get(2);
                    break;

                case "armorClass":
                    armorClass = (int) newNpc.get(7);
                    break;

                case "healthPoints":
                    healthPoints = (int) newNpc.get(8);
                    break;

                case "movementSpeedList":
                    movementSpeedList = (ArrayList<String>) newNpc.get(9);
                    break;

                case "languages":
                    languages = (ArrayList<String>) newNpc.get(10);
                    break;

                case "savingThrows":
                    savingThrows = (ArrayList<String>) newNpc.get(12);
                    break;

                case "weaponList":
                    weaponList = (ArrayList<String>) newNpc.get(14);
                    break;

                case "senses":
                    senses = (String) newNpc.get(16);
                    break;

                case "spellList":
                    spellList = (ArrayList<String>) newNpc.get(17);
                    break;

                case "spellAbility":
                    spellAbility = (ArrayList<String>) newNpc.get(18);
                    break;

                case "description":
                    description = (ArrayList<String>) newNpc.get(19);
                    break;

                case "actionList":
                    actionList = (ArrayList<String>) newNpc.get(20);
                    break;

                case "bonusActionList":
                    bonusActionList = (ArrayList<String>) newNpc.get(21);
                    break;

                case "profBonus":
                    profBonus = (int) newNpc.get(22);
                    break;

                case "abilityModifier":
                    abilityModifier = (ArrayList<Integer>) newNpc.get(23);
                    break;

                case "gender":
                    gender = (String) newNpc.get(24);
                    break;

                case "age":
                    age = (int) newNpc.get(25);
                    break;

                case "alignment":
                    alignment = (String) newNpc.get(26);
                    break;

                case "bonusTextList":
                    bonusTextList = (ArrayList<String>) newNpc.get(28);
                    break;

                default:
                    break;
            }
        }


        return changedNpc;
    }

    /**
     * Find changes list.
     *
     * @param newNpc the new npc
     * @param oldNpc the old npc
     * @return the list
     */
/*
        Diese Funktion vergleicht den alten und neuen Npc und gibt eine Liste mit den changes zurück
    */
    public List<String> findChanges(List<Object> newNpc, List<Object> oldNpc) {
        ArrayList<String> formatNpcWithNamesForIndex = new ArrayList<>();
        ArrayList<String> changeList = new ArrayList<>();
        formatNpcWithNamesForIndex.add("name");
        formatNpcWithNamesForIndex.add("level");
        formatNpcWithNamesForIndex.add("class");
        formatNpcWithNamesForIndex.add("race");
        formatNpcWithNamesForIndex.add("job");
        formatNpcWithNamesForIndex.add("armorClass");
        formatNpcWithNamesForIndex.add("healthPoints");
        formatNpcWithNamesForIndex.add("movementSpeedList");
        formatNpcWithNamesForIndex.add("languages");
        formatNpcWithNamesForIndex.add("abilityScores");
        formatNpcWithNamesForIndex.add("savingThrows");
        formatNpcWithNamesForIndex.add("armor");
        formatNpcWithNamesForIndex.add("weaponList");
        formatNpcWithNamesForIndex.add("skillList");
        formatNpcWithNamesForIndex.add("senses");
        formatNpcWithNamesForIndex.add("spellList");
        formatNpcWithNamesForIndex.add("spellAbility");
        formatNpcWithNamesForIndex.add("description");
        formatNpcWithNamesForIndex.add("actionList");
        formatNpcWithNamesForIndex.add("bonusActionList");
        formatNpcWithNamesForIndex.add("profBonus");
        formatNpcWithNamesForIndex.add("abilityModifier");
        formatNpcWithNamesForIndex.add("gender");
        formatNpcWithNamesForIndex.add("age");
        formatNpcWithNamesForIndex.add("alignment");
        formatNpcWithNamesForIndex.add("deity");
        formatNpcWithNamesForIndex.add("bonusTextList");

        //finde die Changes
        for (Object s : newNpc) {
            int indexOfS = newNpc.indexOf(s);
            if (!newNpc.get(indexOfS).equals(oldNpc.get(indexOfS))) {
                changeList.add(formatNpcWithNamesForIndex.get(indexOfS));
            }
        }
        return changeList;
    }

    private List<Object> editJob(List<Object> oldNpc, List<String> manuelleChangedSkills, List<String> manuelleChangedLanguage) {
        ArrayList<Object> editedJobList = new ArrayList<>();

        languages.addAll(randNpc.determineLanguages(race, job));


        return null;
    }

    private List<String> manuellChanges(List<Object> newNpc, List<Object> oldNpc, int index) {
        ArrayList<String> changedSkills = new ArrayList<>();
        ArrayList<String> oldStats = (ArrayList<String>) oldNpc.get(index);
        ArrayList<String> newStats = (ArrayList<String>) newNpc.get(index);

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
        ArrayList<String> armorBoni = (ArrayList<String>) randGen.bigStringToDividedString(justArmor);
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


    private void lookAtAbilityScore(List<Object> newNpc, List<Object> oldNpc) {
        int newLevel = (int) newNpc.get(1);
        int oldLevel = (int) oldNpc.get(1);
        int levelDiff = 0;
        int deproveCount = 0;
        List<Integer> sortedAS = abilityScores;

        if (oldLevel < newLevel) {
            levelDiff = newLevel - oldLevel;
           // abilityScores = randNpc.addAbilityScoreImprovement(levelDiff - 4, abilityScores);
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


    private void newRaceAbilityScore(ArrayList<Object> newNpc, ArrayList<Object> oldNpc ) {
        String oldRace = (String) oldNpc.get(3);
        ArrayList<String> oldRaceAbilityModifier = (ArrayList<String>) randGen.bigStringToDividedString(dbms.getRow("race", oldRace ).get(2));
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
        abilityScores = randNpc.addRaceModifierToAbilityScore(race, (ArrayList<Integer>) abilityScores);
        }

    private void defineVariables(List<Object> oldNpc){
        race = (String) oldNpc.get(0);
        level = (int) oldNpc.get(1);
        job = (String) oldNpc.get(2);
        genClass = (String) oldNpc.get(3);
        abilityScores = (List<Integer>) oldNpc.get(4);
        abilityModifier = (ArrayList<Integer>) oldNpc.get(5);
        healthPoints = (int) oldNpc.get(6);
        armor = (String) oldNpc.get(7);
        armorClass = (int) oldNpc.get(8);
        weaponList = (ArrayList<String>) oldNpc.get(9);
        movementSpeedList = (ArrayList<String>) oldNpc.get(10);
        savingThrows = (ArrayList<String>) oldNpc.get(11);
        skillList = (ArrayList<String>) oldNpc.get(12);
        gender = (String) oldNpc.get(13);
        age = (int) oldNpc.get(14);
        name = (String) oldNpc.get(15);
        alignment = (String) oldNpc.get(16);
        description = (ArrayList<String>) oldNpc.get(17);
        deity = (String) oldNpc.get(18);
        languages = (ArrayList<String>) oldNpc.get(19);
        senses = (String) oldNpc.get(20);
        spellList = (ArrayList<String>) oldNpc.get(21);
        actionList = (ArrayList<String>) oldNpc.get(22);
        bonusActionList = (ArrayList<String>) oldNpc.get(23);
        bonusTextList = (ArrayList<String>) oldNpc.get(24);
        spellAbility = (ArrayList<String>) oldNpc.get(25);
    }

    private boolean isThisManuelleChanged(ArrayList<String> changedList, String changedParameter ){

        for(String s: changedList ) {
            if (s.equals(changedParameter)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Remove race ability addition list.
     *
     * @param race         the race
     * @param abilityScore the ability score
     * @return the list
     */
    public List<Integer> removeRaceAbilityAddition(String race, ArrayList<Integer> abilityScore) {
        List<String> raceAbilityModifier = randGen.bigStringToDividedString(dbms.getRow("race", race).get(2));
        int raceModNumber;
        for (String s : raceAbilityModifier) {
            raceModNumber = Integer.parseInt(s.substring(s.length() - 1));
            s = s.substring(0, 3);
            switch (s) {
                case "Str":
                    abilityScore.set(0, abilityScore.getFirst() - raceModNumber);
                    break;
                case "Dex":
                    abilityScore.set(1, abilityScore.get(1) - raceModNumber);
                    break;
                case "Con":
                    abilityScore.set(2, abilityScore.get(2) - raceModNumber);
                    break;
                case "Int":
                    abilityScore.set(3, abilityScore.get(3) - raceModNumber);
                    break;
                case "Wis":
                    abilityScore.set(4, abilityScore.get(4) - raceModNumber);
                    break;
                case "Cha":
                    abilityScore.set(5, abilityScore.get(5) - raceModNumber);
                    break;
                default:
                    break;

            }
        }
        return  abilityScore;
    }

    /**
     * Remove plus number in a string list list.
     *
     * @param stringList the string list
     * @return the list
     */
    public List<String> removePlusNumberInAStringList (List<String> stringList ){
        List<String> stringListWithoutNumbers = new ArrayList<>();
        for(String skills:stringList){
            stringListWithoutNumbers.add(skills.substring(0,skills.indexOf("+")-1));
        }
        return stringListWithoutNumbers;
    }

    }






















