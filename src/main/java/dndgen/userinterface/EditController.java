package dndgen.userinterface;

import dndgen.database.DBMS;
import dndgen.tswp1.NpcClass;
import dndgen.tswp1.RandomGenerator;
import dndgen.tswp1.UiHistoryEditUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * This class is responsible for managing NPCs.
 * It handles operations such as preparing data for the edit view,
 * updating NPC data, and managing specific attributes like
 * abilities, modifiers, weapons, armors, and languages.
 * Uses services and helper functions for database access and logical processing.
 */
@Controller
@SessionAttributes("updateNPC")
public class EditController {
    private static final Logger log = LoggerFactory.getLogger(EditController.class);
    private final DBMS dbms;
    private NpcClass npc;
    private final HelpFunctions helpFunctions;
    private NpcClass updateNPC;
    private final String switchValue = "switch";
    private final RandomGenerator randomGenerator = new RandomGenerator();
    private final List<String> availableSenses = Arrays.asList(
            "Darkvision",
            "Blindsight",
            "Tremorsense",
            "Truesight",
            "Passive Perception"
    );
    private Map<String, String> descriptionMap;

    @Autowired
    public EditController(DBMS dbms, HelpFunctions helpFunctions) {
        this.dbms = dbms;
        this.helpFunctions = helpFunctions;
        descriptionMap = new LinkedHashMap<>();
        descriptionMap.put("Eyes", "");
        descriptionMap.put("Hair Color", "");
        descriptionMap.put("Hairstyle", "");
        descriptionMap.put("Skin", "");
        descriptionMap.put("Trait", "");
        descriptionMap.put("Mannerism", "");
        descriptionMap.put("Bond", "");
        descriptionMap.put("Flaw", "");
        descriptionMap.put("Ideal", "");
        descriptionMap.put("Voice", "");
        descriptionMap.put("Clothing", "");
        descriptionMap.put("Attitude", "");
        descriptionMap.put("Size", "");
        descriptionMap.put("Height", "");
    }

    /**
     * Handles the editing of an NPC by preparing the required data, attributes, and UI elements
     * for the edit view. Loads the NPC details based on a provided ID,
     * sets the necessary model attributes, and returns the name of the view for rendering.
     *
     * @param id    the unique identifier of the NPC to be edited
     * @param model the model to which NPC attributes and other data are added for view rendering
     * @return the name of the view returned for NPC editing
     * @author Marie
     */
    @GetMapping("/edit")
    public String editNpc(@RequestParam Integer id, Model model) {
        // create NPC-Object
        npc = new NpcClass();
        npc = dbms.convertDatabaseEntryToNpcClass(dbms.getHistoryEntry(id));

        // No changes yet → core elements can still be modified
        model.addAttribute(switchValue, false);

        // create dropdown
        model.addAttribute("races", helpFunctions.getSortedColumn("race"));
        model.addAttribute("classes", helpFunctions.getSortedColumn("class"));
        model.addAttribute("alignments", helpFunctions.getSortedColumn("alignment"));

        // Pass previously selected data
        model.addAttribute("name", npc.getName());
        model.addAttribute("race", npc.getRace());
        model.addAttribute("class", npc.getGenClass());
        model.addAttribute("alignment", npc.getAlignment());

        updateNPC = new NpcClass();

        model.addAttribute("updateNPC", updateNPC); // Session value is set here

        return "edit";
    }


    /**
     * Updates the attributes of an NPC using the provided data, sets the
     * necessary model attributes, and prepares the view for rendering after the changes.
     *
     * @param name      the name of the NPC to be edited
     * @param race      the race of the NPC to be updated
     * @param genClass  the class of the NPC to be updated
     * @param alignment the alignment of the NPC to be updated
     * @param model     the model used to store the updated attributes for view rendering
     * @return the name of the view to be returned for the NPC edit screen
     */
    @PostMapping("/editFirst")
    public String editedNPC(@RequestParam String name, @RequestParam String race, @RequestParam String genClass, @RequestParam String alignment, Model model) {
        // Changes made → core elements can no longer be modified
        model.addAttribute(switchValue, true);

        // Make changes
        List<String> changes = new ArrayList<>(4);
        changes.add(race);
        changes.add(genClass);
        changes.add(alignment);
        changes.add(name);

        // update NPC
        UiHistoryEditUpdate uiHistoryEditUpdate = new UiHistoryEditUpdate();

        updateNPC = uiHistoryEditUpdate.updateHistoryEdit(changes, npc);

       // updateNPC.setName(name);
       // updateNPC.setRace(race);
       // updateNPC.setGenClass(genClass);
       // updateNPC.setAlignment(alignment);

        // Prepare display logic
        prepareEditAttributes(model, updateNPC);
        model.addAttribute("updateNPC", updateNPC);
        return "edit";
    }


    /**
     * Prepares the attributes for editing an NPC by setting various properties—
     * such as general attributes, additional data, movement values, languages,
     * ability scores, inventory, and descriptions—into the provided model.
     *
     * @param model the model used to prepare attributes for displaying the edit view
     * @param npc   the NPC object whose attributes are being prepared for editing
     * @author Marie
     */
    private void prepareEditAttributes(Model model, NpcClass npc) {
        // TODO: Reduce function size → extract logic / refactor

        // Prepare shared parameters
        model.addAttribute("name", npc.getName());
        model.addAttribute("selectedRace", npc.getRace());
        model.addAttribute("selectedClass", npc.getGenClass());
        model.addAttribute("selectedAlignment", npc.getAlignment());
        model.addAttribute("selectedSkills", npc.getSkillList());
        model.addAttribute("selectedSenses", npc.getSenses());
        model.addAttribute("npc", npc);

        // extra data (Level, Jobs, etc.)
        model.addAttribute("level", npc.getLevel());
        model.addAttribute("jobs", helpFunctions.getSortedColumn("job"));
        model.addAttribute("job", npc.getJob());
        model.addAttribute("deities", dbms.getColWithPref("deity", npc.getAlignment()));
        model.addAttribute("deity", npc.getAlignment());

        // further data (Sex, Age, HP, Speed)
        model.addAttribute("sex", npc.getGender());
        model.addAttribute("age", npc.getAge());
        model.addAttribute("AC", npc.getArmorClass());
        model.addAttribute("HP", npc.getHealthPoints());

        // Movement values
        List<String> speeds = npc.getMovementSpeedList();
        String walkingSpeed = speeds.stream()
                .filter(s -> s.contains("walking"))
                .findFirst()
                .orElse("");
        String flyingSpeed = speeds.stream()
                .filter(s -> s.contains("flying"))
                .findFirst()
                .orElse("");
        model.addAttribute("walkingSpeed", walkingSpeed.replaceAll("\\D+", ""));
        model.addAttribute("flyingSpeed", flyingSpeed.replaceAll("\\D+", ""));

        // Languages
        model.addAttribute("languages", dbms.onlyColumn("language"));
        model.addAttribute("language", String.join(", ", npc.getLanguages()));

        // Ability Scores
        formatNpcDataForStats(npc, model);

        // Inventory
        model.addAttribute("armors", dbms.onlyColumn("armor"));
        model.addAttribute("armor", String.join(", ", npc.getArmor())); // TODO: Currently a String – should probably be a List due to multiple possible objects
        model.addAttribute("weapons", dbms.onlyColumn("weapon"));
        model.addAttribute("weapon", String.join(", ", npc.getWeaponList()));

        // Skills
        model.addAttribute("skills", filterSkills(npc.getSkillList()));
        model.addAttribute("availableSkills", dbms.onlyColumn("skill"));

        // Senses
        model.addAttribute("senses", getSelectedSenses());
        model.addAttribute("availableSenses", availableSenses);


        // Spells
        // all spells from the DB
        model.addAttribute("cantrips", dbms.getMultRowWithPref("spells","cantrip",null));
        model.addAttribute("firstSpell",dbms.getMultRowWithPref("spells","1",null));
        model.addAttribute("secondSpell",dbms.getMultRowWithPref("spells","2",null));
        model.addAttribute("thirdSpell",dbms.getMultRowWithPref("spells","3",null));
        model.addAttribute("fourthSpell",dbms.getMultRowWithPref("spells","4",null));
        model.addAttribute("fifthSpell",dbms.getMultRowWithPref("spells","5",null));
        model.addAttribute("sixthSpell",dbms.getMultRowWithPref("spells","6",null));
        model.addAttribute("seventhSpell",dbms.getMultRowWithPref("spells","7",null));
        model.addAttribute("eightSpell",dbms.getMultRowWithPref("spells","8",null));
        model.addAttribute("ninthSpell",dbms.getMultRowWithPref("spells","9",null));

        // all spells from the npc
        model.addAttribute("spellCantrips", helpFunctions.splitString(npc.getSpellCantrips()));
        model.addAttribute("spellFirstLevel", helpFunctions.splitString(npc.getFirstLevelSpell()));
        model.addAttribute("spellSecondLevel", helpFunctions.splitString(npc.getSecondLevelSpell()));
        model.addAttribute("spellThirdLevel", helpFunctions.splitString(npc.getThirdLevelSpell()));
        model.addAttribute("spellFourthLevel", helpFunctions.splitString(npc.getFourthLevelSpell()));
        model.addAttribute("spellFifthLevel", helpFunctions.splitString(npc.getFifthLevelSpell()));
        model.addAttribute("spellSixthLevel", helpFunctions.splitString(npc.getSixthLevelSpell()));
        model.addAttribute("spellSeventhLevel", helpFunctions.splitString(npc.getSeventhLevelSpell()));
        model.addAttribute("spellEighthLevel", helpFunctions.splitString(npc.getEightLevelSpell()));
        model.addAttribute("spellNinthLevel", helpFunctions.splitString(npc.getNinthLevelSpell()));

        // Description
        List<String> descriptions = npc.getDescription();
        List<String> titles = List.of("Eyes", "Hair Color", "Hairstyle", "Skin", "Trait", "Mannerism",
                "Bond", "Flaw", "Ideal", "Voice", "Clothing", "Attitude", "Size", "Height");

        for (int i = 0; i < descriptions.size(); i++) {
            descriptionMap.put(titles.get(i), descriptions.get(i));
        }

        model.addAttribute("descriptionList",descriptionMap);
        model.addAttribute("descriptionListTitles", titles);
        model.addAttribute("descriptionListAttribute", descriptions);
        model.addAttribute("descriptionText", helpFunctions.generateDescriptionText(npc));
    }


    @PostMapping("saveNpc")
    public String saveEdit() {
        // TODO: Entire saving logic for the edited NPC is missing
        // TODO: Iterate over skills again – "+..." values are missing

        dbms.updateHistoryEntry(updateNPC, updateNPC.getId(), dbms.getHistoryEntry(updateNPC.getId()).getEntryAge());

        //return "redirect:/detail?id=" + npc.getId();
        return "redirect:/history";
    }


    /**
     * Processes the selected languages for an NPC by updating the language list
     * and preparing the model with the updated information.
     *
     * @param selectedLanguages the list of languages selected by the user
     * @param model             the model used to prepare attributes for view rendering
     * @return a string indicating the name of the view to be rendered
     * @author Marie
     */
    @PostMapping("editLanguages")
    public String editLanguages(@RequestParam("lang") List<String> selectedLanguages, Model model) {
        editListAttributes(selectedLanguages, "lang", model);
        return "edit";

    }


    /**
     * Processes the selected armors for an NPC by updating the armor list
     * and preparing the model with the updated information.
     *
     * @param selectedArmors the list of armors selected by the user
     * @param model          the model used to prepare attributes for view rendering
     * @return a string indicating the name of the view to be rendered
     * @author Marie
     */
    @PostMapping("editArmors")
    public String editArmors(@RequestParam("arm") List<String> selectedArmors, Model model) {
        editListAttributes(selectedArmors, "armor", model);
        return "edit";
    }


    /**
     * Processes the selected weapons for an NPC by updating the weapon list
     * and preparing the model with the updated information.
     *
     * @param selectedWeapons the list of weapons selected by the user
     * @param model           the model used to prepare attributes for view rendering
     * @return a string indicating the name of the view to be rendered
     * @author Marie
     */
    @PostMapping("editWeapons")
    public String editWeapons(@RequestParam("wea") List<String> selectedWeapons, Model model) {
        editListAttributes(selectedWeapons, "weapon", model);
        return "edit";
    }


    /**
     * Processes and updates specific attributes of a list based on the given type,
     * and updates the model accordingly.
     *
     * @param selectedList the list of elements to be updated
     * @param type the type of attribute to be modified (e.g., "lang", "armor", "skill", "weapon")
     * @param model the model object used to update attributes for display
     * @throws IllegalArgumentException if an unknown attribute type is provided
     */
    private void editListAttributes(List<String> selectedList, String type, Model model) {
        model.addAttribute(switchValue, true);

        // update list
        switch (type) {
            case "lang":
                updateNPC.setLanguages(selectedList);
                break;
            case "armor":
                updateNPC.setArmor(String.valueOf(selectedList));
                break;
            case "skill":
                updateNPC.setSkillList(selectedList);
                break;
            case "weapon":
                updateNPC.setWeaponList(selectedList);
                break;
            case "sense":
                updateNPC.setSenses(String.valueOf(selectedList));
                break;
            default:
                throw new IllegalArgumentException("Unknown attribute type: " + type);
        }

        // Prepare display update
        prepareEditAttributes(model, updateNPC);

    }


    /**
     * Formats the NPC data for statistical display and adds attributes such as
     * ability scores, modifiers, and saving throws to the model for use in a view.
     *
     * @param npc   the NPC object containing raw data for ability scores, modifiers,
     *              and saving throws
     * @param model the model object used to store the formatted data as attributes
     */
    private void formatNpcDataForStats(NpcClass npc, Model model) {
        List<String> allAbilities = Arrays.asList("Str", "Dex", "Con", "Int", "Wis", "Cha");

        // Retrieve separated values and modifiers directly via existing functions
        List<Integer> abilityScores = npc.getAbilityScores();
        List<Integer> abilityModifiers = npc.getAbilityModifier();
        List<String> savingNpc = npc.getSavingThrows();


        // Format list of saving throws
        Map<String, Integer> savingThrows = new LinkedHashMap<>(); // LinkedHashMap: preserves insertion order

        // Add the key names with null as placeholder values
        for (String ability : allAbilities) {
            // Check abilities from the NPC list
            for (String saving : savingNpc) {
                if (saving.substring(0, 3).contains(ability)) {
                    try {
                        // Parse substring starting at index 4 (after the ":")
                        int parsedValue = Integer.parseInt(saving.substring(4));
                        savingThrows.put(ability, parsedValue); // Add the value
                        break;
                    } catch (NumberFormatException e) {
                        // In case of error, no new value is added
                    }
                } else {
                    savingThrows.put(ability, 0); // set default value
                }
            }
        }

        model.addAttribute("allAbilities", allAbilities);
        model.addAttribute("abilityScores", abilityScores);
        model.addAttribute("abilityModifiers", abilityModifiers);
        model.addAttribute("savingThrows",savingThrows);
    }


    /**
     * Updates the attribute values of a given NPC. This method processes the existing
     * attribute values and applies the provided updates by modifying the corresponding
     * value for the specified key. The updated list of attribute values is then stored
     * in the NPC object.
     *
     * @param key       the attribute key (e.g., "0", "1", etc.) representing the
     *                  index in the attribute value list
     * @param value     the new value to be set for the specified key in the
     *                  attribute value list
     * @param updateNPC the NPC object whose attribute values are to be updated
     * @return a ResponseEntity indicating the result of the operation
     */
    @PostMapping("/editAbility")
    @ResponseBody
    public ResponseEntity<Void> editAbility(@RequestParam String key, @RequestParam int value,
                                            @ModelAttribute("updateNPC") NpcClass updateNPC) {

        List<Integer> abilityScores = updateNPC.getAbilityScores();

        abilityScores.set(Integer.parseInt(key), value);

        updateNPC.setAbilityScores(abilityScores);

        return ResponseEntity.ok().build();
    }


    /**
     * Updates the ability modifiers of a given NPC. This method processes the existing
     * ability modifiers and applies the provided updates by modifying the corresponding
     * value for the specified key. The updated list of ability modifiers is then stored
     * in the NPC object.
     *
     * @param key       the attribute key (e.g., "0", "1", etc.) representing the index
     *                  to be updated in the ability modifier list
     * @param value     the new value to be set for the specified key in the ability modifier list
     * @param updateNPC the NPC object whose ability modifiers are to be updated
     * @return a ResponseEntity indicating the result of the operation
     */
    @PostMapping("/editModifier")
    @ResponseBody
    public ResponseEntity<Void> editModifier(@RequestParam String key, @RequestParam int value,
                                             @ModelAttribute("updateNPC") NpcClass updateNPC) {
        List<Integer> abilityModifiers = updateNPC.getAbilityModifier();

        abilityModifiers.set(Integer.parseInt(key), value);

        updateNPC.setAbilityModifier(abilityModifiers);

        return ResponseEntity.ok().build();
    }


    /**
     * Updates the saving throes of a given NPC. This method processes the existing
     * saving throws and applies the provided updates or adds a new entry if the specified
     * key does not exist. After processing, the updated list of saving throws is stored
     * in the NPC object.
     *
     * @param key       the attribute key (e.g., "Str", "Dex", etc.) to update or add
     * @param value     the new value for the specified attribute key
     * @param updateNPC the NPC object whose saving throws are to be updated
     * @return a ResponseEntity indicating the result of the operation
     */
    @PostMapping("/editSavingThrows")
    @ResponseBody
    public ResponseEntity<Void> editSavingThrows(@RequestParam String key, @RequestParam int value,
                                                 @ModelAttribute("updateNPC") NpcClass updateNPC) {
        List<String> throwsList = updateNPC.getSavingThrows();

        List<String> allAbilities = Arrays.asList("Str", "Dex", "Con", "Int", "Wis", "Cha");

        // Initialize a list for updated saving throws
        List<String> updatedThrowsList = new ArrayList<>();

        // Update list based on existing data
        for (String ability : allAbilities) {
            boolean isUpdated = false;

            for (String saving : throwsList) {
                // Check if ability is contained in the list
                if (saving.substring(0, 3).equalsIgnoreCase(ability)) {
                    try {
                        // Parse substring: extract "+1" or numeric value starting at index 4
                        int parsedValue = Integer.parseInt(saving.substring(4));

                        // If ability matches `key`, update the value
                        if (ability.equals(key)) {
                            updatedThrowsList.add(ability + " " + (value >= 0 ? "+" : "") + value); // Format: Con +1
                        } else {
                            updatedThrowsList.add(ability + " " + (parsedValue >= 0 ? "+" : "") + parsedValue); // Keep current value
                        }
                        isUpdated = true;
                        break;
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number format: " + saving);
                    }
                }
            }
            // If ability is not in the throwsList, add it with 0 (default) or update it
            if (!isUpdated) {
                if (ability.equals(key)) {
                    updatedThrowsList.add(ability + " " + (value >= 0 ? "+" : "") + value);
                } else {
                    updatedThrowsList.add(ability + " +0");
                }
            }
        }

        // Verify that `key` is correctly included in the updated list
        if (!allAbilities.contains(key)) {
            updatedThrowsList.add(key + " " + (value >= 0 ? "+" : "") + value);
        }

        // Set the updated list back into the NPC
        updateNPC.setSavingThrows(updatedThrowsList);

        return ResponseEntity.ok().build();

    }


    /**
     * Edits the selected abilities of an NPC by updating the skill list
     * and preparing the model with the updated information.
     *
     * @param selectedSkills the list of abilities selected by the user
     * @param model          the model used to prepare attributes for the view rendering
     * @return a string specifying the name of the view to render
     * @author Marie
     */
    @PostMapping("editSkills")
    public String editSkills(@RequestParam("skill") List<String> selectedSkills, Model model) {
        editListAttributes(selectedSkills, "skill", model);
        return "edit";
    }


    /**
     * Cleans up a list of ability strings by removing numeric suffixes (e.g. "+1")
     * and trimming surrounding whitespace.
     *
     * @param skills list of ability strings to process
     * @return a new list of ability strings after filtering
     */
    public static List<String> filterSkills(List<String> skills) {
        return skills.stream()
                .map(skill -> skill.replaceAll("\\s*\\+\\d+", ""))
                .collect(Collectors.toList());
    }


    /**
     * Retrieves the selected senses from the NPC object and maps them to their corresponding numeric values.
     * If the NPC has no senses or the sense field is empty, an empty map is returned.
     * Otherwise, splits the sense string by commas, extracts and trims each sense,
     * and maps them based on their names (removing numeric values and units) and associated numeric values.
     *
     * @return a map where keys are the sense names (without numeric values or units)
     *         and values are the corresponding numeric values extracted from the sense string.
     */
    public Map<String, Integer> getSelectedSenses() {
        if (npc.getSenses() == null || npc.getSenses().isEmpty()) {
            return Collections.emptyMap();
        }

        return Arrays.stream(npc.getSenses().split(","))
                .map(String::trim)
                .collect(Collectors.toMap(
                        // Remove the number and everything after it, keep the full name
                        sense -> sense.replaceAll("\\s+\\d+.*$", ""),
                        // Extract only the number, ignore the unit
                        sense -> {
                            Matcher matcher = Pattern.compile("\\b(\\d+)\\b").matcher(sense);
                            return matcher.find() ? Integer.parseInt(matcher.group(1)) : 0;
                        }
                ));
    }


    /**
     * Handles the editing of senses by updating model attributes with the selected senses.
     *
     * @param selectedSenses the list of senses selected by the user to be updated
     * @param model the model to update with the selected senses attributes
     * @return a string representing the name of the view to render
     */
    @PostMapping("editSenses")
    public String editSenses(@RequestParam("sense") List<String> selectedSenses, Model model) {
        editListAttributes(selectedSenses, "sense", model);
        return "edit";
    }


    /**
     * Updates the spells associated with an NPC by removing obsolete spells and adding selected spells
     * based on their levels and names. Also prepares the model attributes for rendering the edit view.
     *
     * @param selectedSpells the list of selected spells to be processed. Each spell entry should be a
     *                       string formatted as "level;name", where "level" represents the spell level
     *                       (e.g., "Cantrip", "1", "2", etc.) and "name" represents the spell's name.
     * @param model          the Model object used to populate attributes required for the edit view.
     * @return a string representing the name of the view to be rendered, specifically the "edit" view.
     * @author Marie
     */
    @PostMapping("editSpells")
    public String editSpells(@RequestParam("spells") List<String> selectedSpells, Model model) {
        String level, name;
        List<String> spellList = getAllSpells();

        // remove old spells
        for (String spells : spellList) {
            boolean contains = false;
            for (String selectedSpell : selectedSpells) {
                name = selectedSpell.substring(selectedSpell.indexOf(";")+1);

                if (name.contains(spells)) {
                    contains = true;
                }
            }

            if (!contains) {
                updateNPC.removeSpellCantrip(spells);

                updateNPC.removeFirstLevelSpell(spells);

                updateNPC.removeSecondLevelSpell(spells);

                updateNPC.removeThirdLevelSpell(spells);

                updateNPC.removeFourthLevelSpell(spells);

                updateNPC.removeFifthLevelSpell(spells);

                updateNPC.removeSixthLevelSpell(spells);

                updateNPC.removeSeventhLevelSpell(spells);

                updateNPC.removeEightLevelSpells(spells);

                updateNPC.removeNinthLevelSpell(spells);
            }
        }

        // add new spells
        for (String spell : selectedSpells) {
            level = spell.substring(0,spell.indexOf(";"));
            name = spell.substring(spell.indexOf(";")+1);

            switch (level) {
                case "Cantrip":
                    updateNPC.addSpellCantrip(name);
                    break;
                case "1":
                    updateNPC.addFirstLevelSpell(name);
                    break;
                case "2":
                    updateNPC.addSecondLevelSpell(name);
                    break;
                case "3":
                    updateNPC.addThirdLevelSpell(name);
                    break;
                case "4":
                    updateNPC.addFourthLevelSpell(name);
                    break;
                case "5":
                    updateNPC.addFifthLevelSpell(name);
                    break;
                case "6":
                    updateNPC.addSixthLevelSpell(name);
                    break;
                case "7":
                    updateNPC.addSeventhLevelSpell(name);
                    break;
                case "8":
                    updateNPC.addEightLevelSpell(name);
                    break;
                case "9":
                    updateNPC.addNinthLevelSpell(name);
                    break;
                default:
                    System.out.println("Unknown level: " + level + " for spell: " + name + ".");
                    log.warn("Unknown level: {} for spell: {}.", level, name);
                    break;
            }
        }

        model.addAttribute(switchValue, true);

        prepareEditAttributes(model, updateNPC);
        return "edit";
    }


    @NonNull
    private List<String> getAllSpells() {
        // Creation of a List with all npc spells
        List<String> spellList = new ArrayList<>();
        if (updateNPC.getSpellCantrips() != null && !updateNPC.getSpellCantrips().isEmpty()) {
            spellList.addAll(Arrays.asList(updateNPC.getSpellCantrips().split(", ")));
        }
        if (updateNPC.getFirstLevelSpell() != null && !updateNPC.getFirstLevelSpell().isEmpty()) {
            spellList.addAll(Arrays.asList(updateNPC.getFirstLevelSpell().split(", ")));
        }
        if (updateNPC.getSecondLevelSpell() != null && !updateNPC.getSecondLevelSpell().isEmpty()) {
            spellList.addAll(Arrays.asList(updateNPC.getSecondLevelSpell().split(", ")));
        }
        if (updateNPC.getThirdLevelSpell() != null && !updateNPC.getThirdLevelSpell().isEmpty()) {
            spellList.addAll(Arrays.asList(updateNPC.getThirdLevelSpell().split(", ")));
        }
        if (updateNPC.getFourthLevelSpell() != null && !updateNPC.getFourthLevelSpell().isEmpty()) {
            spellList.addAll(Arrays.asList(updateNPC.getFourthLevelSpell().split(", ")));
        }
        if (updateNPC.getFifthLevelSpell() != null && !updateNPC.getFifthLevelSpell().isEmpty()) {
            spellList.addAll(Arrays.asList(updateNPC.getFifthLevelSpell().split(", ")));
        }
        if (updateNPC.getSixthLevelSpell() != null && !updateNPC.getSixthLevelSpell().isEmpty()) {
            spellList.addAll(Arrays.asList(updateNPC.getSixthLevelSpell().split(", ")));
        }
        if (updateNPC.getSeventhLevelSpell() != null && !updateNPC.getSeventhLevelSpell().isEmpty()) {
            spellList.addAll(Arrays.asList(updateNPC.getSeventhLevelSpell().split(", ")));
        }
        if (updateNPC.getEightLevelSpell() != null && !updateNPC.getEightLevelSpell().isEmpty()) {
            spellList.addAll(Arrays.asList(updateNPC.getEightLevelSpell().split(", ")));
        }
        if (updateNPC.getNinthLevelSpell() != null && !updateNPC.getNinthLevelSpell().isEmpty()) {
            spellList.addAll(Arrays.asList(updateNPC.getNinthLevelSpell().split(", ")));
        }
        return spellList;
    }


    /**
     * Updates a specific field of the provided NPC object based on the input parameters.
     * The `value` parameter determines the value to be set, and optionally, the field to update.
     * If no field is provided, the default field "age" will be updated.
     *
     * @param value a string containing the new value and optionally the field to update, separated by a semicolon (e.g., "newValue;fieldName")
     * @param updateNPC the NPC object to be updated
     * @return a ResponseEntity containing an HTTP status code of 200 (OK) if the update is successful
     * @throws IllegalArgumentException if the specified field is not recognized
     */
    @PostMapping("/updateField")
    @ResponseBody
    public ResponseEntity<Void> updateField(@RequestParam String value, @RequestParam(required = false) String field, @ModelAttribute("updateNPC") NpcClass updateNPC) {

        String fieldValue = value;
        String fieldName = field;

        // Handle older format where the field is part of the value
        if (fieldName == null) {
            String[] parts = fieldValue.split(";", 2);
            fieldValue = parts[0];
            fieldName = parts.length > 1 ? parts[1] : "age";
        }

        // Debug output
        System.out.println("Update request received for field:" + fieldValue + " " + fieldName);

        // Update logic
        switch (fieldName) {
            case "job":
                updateNPC.setJob(fieldValue);
                break;
            case "level":
                updateNPC.setLevel(Integer.parseInt(fieldValue));
                break;
            case "deity":
                updateNPC.setDeity(fieldValue);
                break;
            case "age":
                updateNPC.setAge(Integer.parseInt(fieldValue));
                break;
            case "gender":
                if(fieldValue.equalsIgnoreCase("Random")){
                    updateNPC.setGender(randomGenerator.randGender());
                } else {
                    updateNPC.setGender(fieldValue);
                }
                break;
            case "AC":
                updateNPC.setArmorClass(Integer.parseInt(fieldValue));
                break;
            case "HP":
                updateNPC.setHealthPoints(Integer.parseInt(fieldValue));
                break;
            case "walkingSpeed", "flyingSpeed":
                List<String> speedList = new ArrayList<>();

                // changes at walking
                if (fieldName.equals("walkingSpeed")) {
                    speedList.add(fieldValue + " ft walking Speed");
                    if(updateNPC.getMovementSpeedList().size()!=1){
                        speedList.add(updateNPC.getMovementSpeedList().get(1));
                    }
                } else { // changes at flying
                    if(fieldValue.isEmpty()) {
                        speedList.add(updateNPC.getMovementSpeedList().getFirst());
                    } else {
                        speedList.add(updateNPC.getMovementSpeedList().getFirst());
                        speedList.add(fieldValue + " ft flying Speed");
                    }
                }
                updateNPC.setMovementSpeedList(speedList);
                break;
            default:
                throw new IllegalArgumentException("Unknown field: " + field);
        }

        return ResponseEntity.ok().build();
    }


    @PostMapping("/updateDescription")
    @ResponseBody
    public ResponseEntity<Void> updateDescription(@RequestParam String value, @RequestParam String field, @ModelAttribute("updateNPC") NpcClass updateNPC) {
        descriptionMap.put(field, value);

        List<String> desriptionList = new ArrayList<>();

        for(Map.Entry<String, String> entry : descriptionMap.entrySet()) {
            desriptionList.add(entry.getValue());
        }

        updateNPC.setDescription(desriptionList);

        return ResponseEntity.ok().build();
    }
}
