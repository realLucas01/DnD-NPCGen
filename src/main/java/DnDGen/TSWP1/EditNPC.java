package DnDGen.TSWP1;

import java.util.ArrayList;

public class EditNPC {

    public ArrayList<Object> applyEdit(ArrayList<String> newNPC, ArrayList<Object> oldNPC) {
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
                    break;
                case "job":
                    //Skills, Language
                    break;
                case "abilityScore":
                    //HP, AC, Skills(Berechnung), Spells(Anzahl), ST(Berechnung)
                    break;
                case "Armor":
                    //AC
                    break;
                case "Skills":
                    //neue Skills berechnen
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
    public ArrayList<String> findChanges(ArrayList<String> newNPC, ArrayList<Object> oldNPC) {
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
        for (String s : newNPC) {
            if(!newNPC.equals(oldNPC)){
                changeList.add(formatNPCWithNamesForIndex.get(oldNPC.indexOf(s)));
            }
        }
        return changeList;
    }



















}
