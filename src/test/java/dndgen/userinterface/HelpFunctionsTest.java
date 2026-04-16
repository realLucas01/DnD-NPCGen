package dndgen.userinterface;

import dndgen.database.DBMS;
import dndgen.tswp1.NpcClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HelpFunctionsTest {
    @Mock
    private DBMS dbms; // Creates a Mock-Instance of DBMS

    @InjectMocks
    private HelpFunctions helpFunctions;


    /**
     * Tests for the `listToMap` method in the `HelpFunctions` class.
     * <p>
     * The `listToMap` method takes a list of strings as input and returns a map.
     * Each string in the list is split into a key-value pair where:
     * - The part before the last space is the key.
     * - The numeric value after the last space is the value.
     * If a string does not contain any spaces, the key is the string, and the value is 0.
     */
    @Test
    void testListToMap_withValidInput() {
        // Given
        List<String> input = List.of("Strength +3", "Dexterity +2", "Intelligence -1");

        // When
        Map<String, Integer> result = helpFunctions.listToMap(input);

        // Then
        assertEquals(3, result.size());
        assertEquals(3, result.get("Strength"));
        assertEquals(2, result.get("Dexterity"));
        assertEquals(-1, result.get("Intelligence"));
    }

    @Test
    void testListToMap_withEmptyList() {
        // Given
        List<String> input = List.of();

        // When
        Map<String, Integer> result = helpFunctions.listToMap(input);

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    void testListToMap_withNoSpaces() {
        // Given
        List<String> input = List.of("Strength", "Dexterity", "Wisdom");

        // When
        Map<String, Integer> result = helpFunctions.listToMap(input);

        // Then
        assertEquals(3, result.size());
        assertEquals(0, result.get("Strength"));
        assertEquals(0, result.get("Dexterity"));
        assertEquals(0, result.get("Wisdom"));
    }

    @Test
    void testListToMap_withMixedInputs() {
        // Given
        List<String> input = List.of("Strength +3", "Dexterity", "Intelligence -1");

        // When
        Map<String, Integer> result = helpFunctions.listToMap(input);

        // Then
        assertEquals(3, result.size());
        assertEquals(3, result.get("Strength"));
        assertEquals(0, result.get("Dexterity"));
        assertEquals(-1, result.get("Intelligence"));
    }

    @Test
    void testListToMap_withInvalidNumberFormat() {
        // Given
        List<String> input = List.of("Strength abc", "Dexterity +2");

        // When / Then
        assertThrows(NumberFormatException.class, () -> helpFunctions.listToMap(input));
    }


    /**
     * Tests for the `listToString` method in the `HelpFunctions` class.
     * <p>
     * The `listToString` method takes a list of strings as input and returns a String.
     * Each string in the list is separated by ',' in the result.
     * There are no square brackets around the result.
     */
    @Test
    void testListToString_withValidInput() {
        // Given
        List<String> input = Arrays.asList("Strength +3", "Dexterity +2", "Intelligence -1");

        // When
        String result = helpFunctions.listToString(input);

        // Then
        assertEquals("Strength +3, Dexterity +2, Intelligence -1", result);
    }

    @Test
    void testListToString_withEmptyList() {
        // Given
        List<String> input = List.of();

        // When
        String result = helpFunctions.listToString(input);

        // Then
        assertEquals("", result);
    }

    @Test
    void testListToString_withSingleElement() {
        // Given
        List<String> input = List.of("Strength +3");

        // When
        String result = helpFunctions.listToString(input);

        // Then
        assertEquals("Strength +3", result);
    }


    /**
     * Tests for the `splitString` method in the `HelpFunctions` class.
     * <p>
     * The `splitString` method makes a List out of a String.
     * Each Item in the List is separated by ',' in the input String.
     */
    @Test
    void testSplitString_withValidInput() {
        // Given
        String input = "Strength +3, Dexterity +2, Intelligence -1";

        // When
        List<String> result = helpFunctions.splitString(input);

        // Then
        assertEquals(3, result.size());
        assertEquals("Strength +3", result.get(0));
        assertEquals("Dexterity +2", result.get(1));
        assertEquals("Intelligence -1", result.get(2));
    }

    @Test
    void testSplitString_withNullInput() {
        List<String> result = helpFunctions.splitString(null);

        assertTrue(result.isEmpty());
    }

    @Test
    void testSplitString_withEmptyInput() {
        List<String> result = helpFunctions.splitString("");

        assertTrue(result.isEmpty());
    }

    @Test
    void testSplitString_withTooLongInput() {
        String input = "a".repeat(1001);

        assertThrows(IllegalArgumentException.class, () -> helpFunctions.splitString(input));
    }

    @Test
    void testSplitString_withTooManyElements() {
        String input = String.join(",", Collections.nCopies(101, "A"));

        assertThrows(IllegalArgumentException.class, () -> helpFunctions.splitString(input));
    }

    @Test
    void testSplitString_withOnlyCommas() {
        String input = ", , ,";

        List<String> result = helpFunctions.splitString(input);

        assertEquals(List.of(), result);
    }

    @Test
    void testSplitString_withMaxElementsBoundary() {
        String input = String.join(",", Collections.nCopies(100, "A"));

        List<String> result = helpFunctions.splitString(input);

        assertEquals(100, result.size());
    }


    /**
     * Tests for the `getSortedColumn` method in the `HelpFunctions` class.
     * <p>
     * This test class uses Mockito to mock the database access (DBMS),
     * ensuring that the sorting logic works correctly regardless of the actual DB state.
     */
    @Test
    void testGetSortedColumn_shouldReturnSortedList() {
        // Given
        String columnName = "hair_color";
        ArrayList<String> mockData = new ArrayList<>(Arrays.asList("brown", "blonde", "black", "red"));

        // We define the behavior of the mock:
        when(dbms.onlyColumn(columnName)).thenReturn(mockData);

        // When
        List<String> result = helpFunctions.getSortedColumn(columnName);

        // Then
        List<String> expected = Arrays.asList("black", "blonde", "brown", "red");
        assertEquals(expected, result, "The list should be sorted in ascending order.");

        // Optional: Verify that the mock was actually called
        verify(dbms).onlyColumn(columnName);
    }

    @Test
    void testGetSortedColumn_withEmptyDatabase() {
        // Given
        String columnName = "empty_column";
        when(dbms.onlyColumn(columnName)).thenReturn(new ArrayList<>());

        // When
        List<String> result = helpFunctions.getSortedColumn(columnName);

        // Then
        assertTrue(result.isEmpty(), "Result should be an empty list if DB returns no data.");
    }


    /**
     * Tests for the `addSpellsToModel` method in the `HelpFunctions` class.
     * <p>
     * This test verifies that spell information is correctly retrieved from the NPC object,
     * split into individual spells, and then categorized by their respective spell levels
     * before being added to the Spring Model.
     * <p>
     * Mocks are used for both NpcClass and Model to isolate the mapping logic and
     * avoid side effects from the NPC's constructor.
     */
    @Test
    void testAddSpellsToModel_withMocks() {
        // Mock
        NpcClass npcMock = mock(NpcClass.class);
        Model modelMock = mock(Model.class);

        // Given
        when(npcMock.getSpellCantrips()).thenReturn("Firebolt, Light");
        when(npcMock.getFirstLevelSpell()).thenReturn("Shield");

        // When
        helpFunctions.addSpellsToModel(npcMock, modelMock);

        // Then
        verify(modelMock).addAttribute(eq("spellCantrips"), anyList());
        verify(modelMock).addAttribute(eq("spellFirstLevel"), anyList());
    }

}
