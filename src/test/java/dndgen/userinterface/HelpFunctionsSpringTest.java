package dndgen.userinterface;

import dndgen.tswp1.NpcClass;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class HelpFunctionsSpringTest {

    private final HelpFunctions helpFunctions = new HelpFunctions(null);


    /**
     * Tests for the `getAbilityScoresWithModifiers` method in the `HelpFunctions` class.
     * <p>
     * The `getAbilityScoresWithModifiers` method takes an `NpcClass` object as input
     * and returns a list of formatted strings.
     * Each entry in the resulting list combines an ability score with its corresponding modifier:
     * - The ability score is taken from the `abilityScores` list.
     * - The modifier is taken from the `abilityModifier` list.
     * - Positive modifiers are prefixed with a "+" sign, while negative modifiers remain unchanged.
     * - Each result entry follows the format: "<score> <modifier>" (e.g., "14 +2", "8 -1").
     * <p>
     * The method assumes that both lists are of equal length and aligned by index.
     * If the lists differ in size or are null, runtime exceptions may occur.
     */
    @Test
    void testGetAbilityScoresWithModifiers_withValidInput() {
        NpcClass npc = new NpcClass();
        npc.setAbilityScores(List.of(14, 12, 10));
        npc.setAbilityModifier(List.of(2, 1, 0));

        List<String> result = helpFunctions.getAbilityScoresWithModifiers(npc);

        assertEquals(List.of("14 +2", "12 +1", "10 +0"), result);
    }

    @Test
    void testGetAbilityScoresWithModifiers_withNegativeModifiers() {
        NpcClass npc = new NpcClass();
        npc.setAbilityScores(List.of(8, 10));
        npc.setAbilityModifier(List.of(-1, 0));

        List<String> result = helpFunctions.getAbilityScoresWithModifiers(npc);

        assertEquals(List.of("8 -1", "10 +0"), result);
    }

    @Test
    void testGetAbilityScoresWithModifiers_withEmptyLists() {
        NpcClass npc = new NpcClass();
        npc.setAbilityScores(List.of());
        npc.setAbilityModifier(List.of());

        List<String> result = helpFunctions.getAbilityScoresWithModifiers(npc);

        assertTrue(result.isEmpty());
    }

    @Test
    void testGetAbilityScoresWithModifiers_withMismatchedSizes() {
        NpcClass npc = new NpcClass();
        npc.setAbilityScores(List.of(10, 12));
        npc.setAbilityModifier(List.of(1));

        assertThrows(IndexOutOfBoundsException.class, () -> helpFunctions.getAbilityScoresWithModifiers(npc));
    }

    @Test
    void testGetAbilityScoresWithModifiers_withNullLists() {
        NpcClass npc = new NpcClass();
        npc.setAbilityScores(null);
        npc.setAbilityModifier(null);

        assertThrows(NullPointerException.class, () -> helpFunctions.getAbilityScoresWithModifiers(npc));
    }


    /**
     * Tests for the `formatSavingThrows` method in the `HelpFunctions` class.
     * <p>
     * The `formatSavingThrows` method takes an `NpcClass` object as input
     * and returns a list of saving throw modifiers aligned to specific attributes.
     * The resulting list always has a fixed size of 6 elements corresponding to:
     * "Str", "Dex", "Con", "Int", "Wis", "Cha".
     * <p>
     * Each saving throw string is expected in the format "<Ability> <Modifier>"
     * (e.g., "Str +2", "Dex -1"). The method:
     * - Extracts the ability and its modifier from each string.
     * - Places the modifier at the correct index based on the predefined ability order.
     * - Leaves unspecified abilities as empty strings.
     * <p>
     * If the input list is null, empty, or contains "none",
     * a default list filled with empty strings is returned.
     * Invalid or malformed entries are ignored.
     */
    @Test
    void testFormatSavingThrows_withValidInput() {
        NpcClass npc = new NpcClass();
        npc.setSavingThrows(List.of("Str +2", "Dex +1", "Wis -1"));

        List<String> result = helpFunctions.formatSavingThrows(npc);

        assertEquals(List.of("+2", "+1", "", "", "-1", ""), result);
    }

    @Test
    void testFormatSavingThrows_withEmptyList() {
        NpcClass npc = new NpcClass();
        npc.setSavingThrows(List.of());

        List<String> result = helpFunctions.formatSavingThrows(npc);

        assertEquals(List.of("", "", "", "", "", ""), result);
    }

    @Test
    void testFormatSavingThrows_withNullInput() {
        NpcClass npc = new NpcClass();
        npc.setSavingThrows(null);

        List<String> result = helpFunctions.formatSavingThrows(npc);

        assertEquals(List.of("", "", "", "", "", ""), result);
    }

    @Test
    void testFormatSavingThrows_withNoneValue() {
        NpcClass npc = new NpcClass();
        npc.setSavingThrows(List.of("none"));

        List<String> result = helpFunctions.formatSavingThrows(npc);

        assertEquals(List.of("", "", "", "", "", ""), result);
    }

    @Test
    void testFormatSavingThrows_withUnorderedInput() {
        NpcClass npc = new NpcClass();
        npc.setSavingThrows(List.of("Wis +3", "Str +1"));

        List<String> result = helpFunctions.formatSavingThrows(npc);

        assertEquals(List.of("+1", "", "", "", "+3", ""), result);
    }

    @Test
    void testFormatSavingThrows_withInvalidEntries() {
        NpcClass npc = new NpcClass();
        npc.setSavingThrows(List.of("InvalidEntry", "Dex +2", "Str"));

        List<String> result = helpFunctions.formatSavingThrows(npc);

        assertEquals(List.of("", "+2", "", "", "", ""), result);
    }

    @Test
    void testFormatSavingThrows_withUnknownAbility() {
        NpcClass npc = new NpcClass();
        npc.setSavingThrows(List.of("Luck +5"));

        List<String> result = helpFunctions.formatSavingThrows(npc);

        assertEquals(List.of("", "", "", "", "", ""), result);
    }


    /**
     * Tests for the `generateDescriptionText` method in the `HelpFunctions` class.
     * <p>
     * The `generateDescriptionText` method takes an `NpcClass` object as input
     * and returns a formatted description string based on the NPC's attributes.
     * <p>
     * The method:
     * - Retrieves a list of description attributes from the NPC.
     * - Uses fixed index positions within the list to construct a full descriptive sentence.
     * - Applies gender-specific pronouns ("he"/"she", "his"/"her") based on the NPC's gender.
     * - Replaces null values (e.g., hairstyle) with default placeholders.
     * <p>
     * If the description list is null or empty, a default message is returned.
     * The method assumes that the description list contains all required elements
     * in the correct order. Missing elements may result in runtime exceptions.
     */
    @Test
    void testGenerateDescriptionText_withValidMaleInput() {
        NpcClass npc = new NpcClass();
        npc.setName("Aragorn");
        npc.setGender("male");

        npc.setDescription(List.of(
                "blue",        // eyes
                "black",       // hair color
                "long",        // hairstyle
                "tan",         // skin
                "calm",        // behavior
                "whistles",    // habit
                "honor",       // bond
                "fear",        // flaw
                "justice",     // ideal
                "deep",        // voice
                "armor",       // clothing
                "serious",     // attitude
                "tall",        // size
                "6"            // height
        ));

        String result = helpFunctions.generateDescriptionText(npc);

        assertTrue(result.contains("Aragorn"));
        assertTrue(result.contains("he"));
        assertTrue(result.contains("his"));
    }

    @Test
    void testGenerateDescriptionText_withValidFemaleInput() {
        NpcClass npc = new NpcClass();
        npc.setName("Eowyn");
        npc.setGender("female");

        npc.setDescription(List.of(
                "green","blonde","braided","fair","brave","smiles",
                "family","doubt","freedom","soft","dress","kind","medium","5"
        ));

        String result = helpFunctions.generateDescriptionText(npc);

        assertTrue(result.contains("she"));
        assertTrue(result.contains("her"));
    }

    @Test
    void testGenerateDescriptionText_withNullDescription() {
        NpcClass npc = new NpcClass();
        npc.setDescription(null);

        String result = helpFunctions.generateDescriptionText(npc);

        assertEquals("No Description available.", result);
    }

    @Test
    void testGenerateDescriptionText_withEmptyDescription() {
        NpcClass npc = new NpcClass();
        npc.setDescription(List.of());

        String result = helpFunctions.generateDescriptionText(npc);

        assertEquals("No Description available.", result);
    }

    @Test
    void testGenerateDescriptionText_withNullHairStyle() {
        NpcClass npc = new NpcClass();
        npc.setName("Test");
        npc.setGender("male");

        List<String> desc = new ArrayList<>(Arrays.asList(
                "blue", "black", null, "tan", "calm", "habit",
                "bond", "flaw", "ideal", "voice", "clothes", "attitude", "size", "6"
        ));
        npc.setDescription(desc);

        String result = helpFunctions.generateDescriptionText(npc);

        assertTrue(result.contains("a simple style"));
    }

    @Test
    void testGenerateDescriptionText_withTooShortDescription() {
        NpcClass npc = new NpcClass();
        npc.setGender("male");
        npc.setDescription(List.of("blue", "black"));

        assertThrows(IndexOutOfBoundsException.class, () -> helpFunctions.generateDescriptionText(npc));
    }

    @Test
    void testGenerateDescriptionText_withNullGender() {
        NpcClass npc = new NpcClass();
        npc.setDescription(List.of("a","b","c","d","e","f","g","h","i","j","k","l","m","n"));
        npc.setGender(null);

        assertThrows(NullPointerException.class, () -> helpFunctions.generateDescriptionText(npc));
    }

}
