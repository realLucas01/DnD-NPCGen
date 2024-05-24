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

    Random rndNumb =  new SecureRandom();
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
        if (changeList==null){
            return null;
        }

        ArrayList<String> manuelleChangedSkills = manuellChanges(newNPC, oldNPC, 12);
        ArrayList<String> manuelleChangedLanguage = manuellChanges(newNPC, oldNPC, 19);

        ArrayList<String> editedSkills = new ArrayList<>();
        ArrayList<String> editedLanguages = new ArrayList<>();
        ArrayList<String> editedSkillList = new ArrayList<>();

        languages = manuelleChangedLanguage;
        for(String changes: changeList){

            switch(changes){
                case "class":
                    //HP, AS Verteilung, ProfWeapon, ProfArmor, ST, Skills, Spells, features, (BonusAction, Action)
                    break;
                case "race":
                    //AS, features, Age, Skills, description, Language,(BonusAction, Action)
                    break;
                case "level":
                    // HP, Action, AS(HP,ST,Skills, Spells) , Spells, PB(-> Skills, SavingThrows)

                    lookAtAbilityScore(newNPC,oldNPC);
                    abilityModifier = randNPC.determineAbilityModifier((ArrayList<Integer>) abilityScores);

                    healthPoints = randNPC.calculateHP(genClass,level,abilityModifier);
                    

                    break;
                case "job":
                    //Skills, Language



                    break;
                case "abilityScore":
                    //HP, AC, Skills(Berechnung), Spells(Anzahl), ST(Berechnung)
                    break;
                case "Armor":
                    //AC
                    newArmorClass();
                    break;
                case "Skills":
                    //neue Skills berechnen
                    skillList = randNPC.addAbilityScoreToSkillList(abilityModifier,skillList,profBonus);
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
        formatNPCWithNamesForIndex.add("race");
        formatNPCWithNamesForIndex.add("level");
        formatNPCWithNamesForIndex.add("job");
        formatNPCWithNamesForIndex.add("class");
        formatNPCWithNamesForIndex.add("abilityScore");
        formatNPCWithNamesForIndex.add("abilityModifier");
        formatNPCWithNamesForIndex.add("HP");
        formatNPCWithNamesForIndex.add("armor");
        formatNPCWithNamesForIndex.add("AC");
        formatNPCWithNamesForIndex.add("weaponList");
        formatNPCWithNamesForIndex.add("walkingSpeedList");
        formatNPCWithNamesForIndex.add("savingThrows");
        formatNPCWithNamesForIndex.add("skillList");
        formatNPCWithNamesForIndex.add("gender");
        formatNPCWithNamesForIndex.add("name");
        formatNPCWithNamesForIndex.add("alignment");
        formatNPCWithNamesForIndex.add("description");
        formatNPCWithNamesForIndex.add("deity");
        formatNPCWithNamesForIndex.add("languages");
        formatNPCWithNamesForIndex.add("senses");
        formatNPCWithNamesForIndex.add("spellList");

        //finde die Changes
        for (Object s : newNPC) {
            if(!newNPC.equals(oldNPC)){
                changeList.add(formatNPCWithNamesForIndex.get(oldNPC.indexOf(s)));
            }
        }
        return changeList;
    }

    private ArrayList<Object> editJob(ArrayList<Object> oldNPc, ArrayList<String> manuelleChangedSkills,ArrayList<String> manuelleChangedLanguage ){
        ArrayList<Object> editedJobList = new ArrayList<>();

        languages.addAll(randNPC.determineLanguages(race,job));










        return null;
    }

    private ArrayList<String> manuellChanges(ArrayList<Object> newNPC, ArrayList<Object> oldNPC, int index){
        ArrayList<String> changedSkills = new ArrayList<>();
        ArrayList<String> oldStats = (ArrayList<String>) oldNPC.get(index);
        ArrayList<String> newStats = (ArrayList<String>) newNPC.get(index);

        for (String skill: newStats){
            if(!oldStats.contains(skill)){
                changedSkills.add(skill);
            }
        }
        return changedSkills;
    }

    private void newArmorClass(){
        String justArmor;
        boolean isThereAShield = false;

        if(armor.contains("Shield") ){
            isThereAShield = true;
            justArmor = armor.substring(0, armor.indexOf("+")-1);
        }else{
            justArmor = armor;
        }

        ArrayList<String> armorDBEntry =dbms.getRow("armor", justArmor);
        int newAC = Integer.parseInt(armorDBEntry.get(3));
        ArrayList<String> armorBoni = randGen.bigStringToDividedString(justArmor);
        String kindOfArmor = armorDBEntry.get(2);
        if((kindOfArmor.equals("Natural Armor"))||(kindOfArmor.equals("Class Armor"))){
            for(String s: armorBoni){
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
        }else if((armorBoni.size()==1)&&(armorBoni.contains("Dex"))) {
            newAC = Integer.parseInt(armorDBEntry.get(3)) + abilityModifier.get(1);
        }
        if((newAC> Integer.parseInt(armorDBEntry.get(4)))){//überprüfen ob AC im toleranten Bereich liegt
            newAC= Integer.parseInt(armorDBEntry.get(4)); // wenn nein, dann diesen festlegen
        }
        if(isThereAShield){
            int shieldPercent= rndNumb.nextInt(2);
            if(shieldPercent == 1){
                armor = armor + " + " + "Shield";
                newAC = newAC +2;
            }
        }
        armorClass = newAC;

    }


    private void lookAtAbilityScore(ArrayList<Object> newNpc, ArrayList<Object> oldNPC){
        int newLevel = (int) newNpc.get(1);
        int oldLevel = (int) oldNPC.get(1);
        int levelDiff = 0;
        int deproveCount = 0;
       List<Integer> sortedAS = abilityScores;

            if(oldLevel<newLevel) {
                levelDiff = newLevel - oldLevel;
                abilityScores = randNPC.addAbilityScoreImprovement(levelDiff-4,abilityScores);
                return;
            } else if (oldLevel>newLevel) {
                levelDiff = oldLevel - newLevel;
            }
            switch(levelDiff) {
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

            for (int rounds=0; deproveCount> rounds; rounds++){
                sortedAS.sort(Collections.reverseOrder());
                int randNumb = rndNumb.nextInt(2)+1;

                switch (randNumb) {
                    case 1:
                        sortedAS.set(0,sortedAS.getFirst() -1);
                        sortedAS.set(0,sortedAS.get(1) -1);
                        break;
                    case 2:
                        sortedAS.set(0,sortedAS.getFirst() -2);
                }
            }

            abilityScores = sortedAS;
            }


    }




















