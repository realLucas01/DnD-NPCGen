package dndgen.userinterface;

import dndgen.database.DBMS;
import dndgen.tswp1.NpcClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Marie
 */
@Component
public class HelpFunctions {
    private final DBMS dbms;

    @Autowired
    public HelpFunctions(DBMS dbms) {
        this.dbms = dbms;
    }

    /**
     * Retrieves a column of strings from the database, sorts it in ascending order, and returns the sorted list.
     *
     * @param columnName the name of the column to retrieve and sort
     * @return a list of strings containing the sorted values from the specified column
     */
    public List<String> getSortedColumn(String columnName) {
        ArrayList<String> list = dbms.onlyColumn(columnName);
        Collections.sort(list);
        return list;
    }


    /**
     * Generates a formatted description text for the specified NPC based on its attributes and descriptions.
     *
     * @param npc The NPC object containing attributes and description details used to construct the description text.
     * @return A formatted string describing the NPC. If no description is available, a default message is returned.
     */
    public String generateDescriptionText(NpcClass npc) {
        List<String> desc = npc.getDescription();

        if (desc == null || desc.isEmpty()) return "Keine Beschreibung verfügbar.";

        // Set gender-specific pronouns
        String pronoun = npc.getGender().equalsIgnoreCase("male") ? "he" : "she";
        String possessivePronoun = npc.getGender().equalsIgnoreCase("male") ? "his" : "her";

        // Replace null values with meaningful placeholders
        String hairStyle = desc.get(2) != null ? desc.get(2) : "a simple style";

        // format description
        return String.format("%s has striking %s eyes. %s hair is %s, worn in %s. %s skin has a %s tone. %s is generally %s in behavior and has a distinctive habit: %s. %s feels strongly about \"%s\" and struggles with \"%s\". %s values \"%s\" above all. %s speaks with a %s voice. They usually wear %s and tend to come across as %s. %s is %s in size and stands at %s feet tall.",
                npc.getName(), desc.get(0), possessivePronoun, desc.get(1), hairStyle,
                npc.getName(), desc.get(3), pronoun, desc.get(4), desc.get(5),
                pronoun, desc.get(6), desc.get(7),
                pronoun, desc.get(8), pronoun, desc.get(9),
                desc.get(10), pronoun, desc.get(11), desc.get(12), desc.get(13));
    }


    /**
     * Formats the saving throes of a given NPC class into a list, aligning the values
     * with specific attributes (Strength, Dexterity, Constitution, Intelligence, Wisdom, Charisma).
     * If the NPC has no saving throws or invalid inputs, a default list with empty strings is returned.
     *
     * @param npc The NPC object containing saving throw information.
     * @return A list of saving throws, formatted by attributes, where each value corresponds to the attributes
     * "Str", "Dex", "Con", "Int", "Wis", "Cha". If no valid saving throws
     * are found, the list contains empty strings.
     */
    public List<String> formatSavingThrows(NpcClass npc) {
        List<String> formattedThrows = new ArrayList<>(List.of("", "", "", "", "", "")); // Mutable List
        List<String> abilities = List.of("Str", "Dex", "Con", "Int", "Wis", "Cha");
        List<String> savingThrows = npc.getSavingThrows();

        if (savingThrows == null || savingThrows.isEmpty() || savingThrows.contains("none")) {
            return formattedThrows;
        }

        for (String throwValue : savingThrows) {
            String[] parts = throwValue.split(" ");
            if (parts.length == 2) {
                int index = abilities.indexOf(parts[0]);
                if (index != -1) {
                    formattedThrows.set(index, parts[1]);
                }
            }
        }
        return formattedThrows;
    }


    /**
     * Combines the attribute values and their corresponding modifiers of a given NPC into a formatted string list.
     * Each entry in the list contains an attribute value followed by its modifier (e.g., "14 +2").
     *
     * @param npc The NPC object containing the attribute values and modifiers to format.
     * @return A list of strings, where each string combines an attribute value and its modifier.
     */
    public List<String> getAbilityScoresWithModifiers(NpcClass npc) {
        List<String> result = new ArrayList<>();
        List<Integer> scores = npc.getAbilityScores();
        List<Integer> mods = npc.getAbilityModifier();

        for (int i = 0; i < scores.size(); i++) {
            String mod = (mods.get(i) >= 0) ? "+" + mods.get(i) : String.valueOf(mods.get(i));
            result.add(scores.get(i) + " " + mod);
        }
        return result;
    }


    /**
     * Splits a given string into a list of substrings based on commas,
     * trimming any surrounding whitespace from each substring.
     * If the input string is null or empty, an empty list is returned.
     *
     * @param input the string to be split into substrings
     * @return a list of substrings obtained by splitting the input string on commas,
     *         or an empty list if the input string is null or empty
     * @author Marie
     */
    public List<String> splitString(String input) {
        return (input == null || input.isEmpty()) ? Collections.emptyList() : List.of(input.split("\\s*,\\s*"));
    }


    /**
     * Adds spell information from the provided NPC object to the model by splitting the spell strings
     * and categorizing them by spell level.
     *
     * @param npc The NPC object containing spell information for different levels.
     * @param model The model to which the processed spell data will be added.
     */
    public void addSpellsToModel(NpcClass npc, Model model) {
        model.addAttribute("spellCantrips", splitString(npc.getSpellCantrips()));
        model.addAttribute("spellFirstLevel", splitString(npc.getFirstLevelSpell()));
        model.addAttribute("spellSecondLevel", splitString(npc.getSecondLevelSpell()));
        model.addAttribute("spellThirdLevel", splitString(npc.getThirdLevelSpell()));
        model.addAttribute("spellFourthLevel", splitString(npc.getFourthLevelSpell()));
        model.addAttribute("spellFifthLevel", splitString(npc.getFifthLevelSpell()));
        model.addAttribute("spellSixthLevel", splitString(npc.getSixthLevelSpell()));
        model.addAttribute("spellSeventhLevel", splitString(npc.getSeventhLevelSpell()));
        model.addAttribute("spellEighthLevel", splitString(npc.getEightLevelSpell()));
        model.addAttribute("spellNinthLevel", splitString(npc.getNinthLevelSpell()));
    }


    /**
     * Converts a list of strings into a single comma-separated string without square brackets.
     *
     * @param list the list of strings to be converted
     * @return a string representation of the list with elements separated by commas and no square brackets
     */
    public String ListToString(List<String> list){
        String string = list.toString();
        String result = string.substring(1, string.length()-1);
        return result;
    }

}
