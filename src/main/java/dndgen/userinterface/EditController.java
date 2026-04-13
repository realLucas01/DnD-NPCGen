package dndgen.userinterface;

import dndgen.database.DBMS;
import dndgen.tswp1.NpcClass;
import dndgen.tswp1.RandomGenerator;
import dndgen.tswp1.UiHistoryEditUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.support.SessionStatus;


import java.util.*;
import java.util.function.Supplier;
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
    private static final String SWITCH = "switch";
    private final RandomGenerator randomGenerator = new RandomGenerator();
    private final List<String> availableSensesFeet = Arrays.asList(
            "Darkvision",
            "Blindsight",
            "Tremorsense",
            "Truesight"
    );
    private final Map<String, String> descriptionMap;
    private static final String WALKING_SPEED = "walkingSpeed";
    private static final String ARMOR_TEXT = "armor";
    private static final String DEITY_TEXT = "deity";
    private static final String WEAPON_TEXT = "weapon";
    private static final String SKILL_TEXT = "skill";
    private static final String CLASS_TEXT = "class";

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
        model.addAttribute(SWITCH, false);

        // create dropdown
        model.addAttribute("races", helpFunctions.getSortedColumn("race"));
        model.addAttribute("classes", helpFunctions.getSortedColumn(CLASS_TEXT));
        model.addAttribute("alignments", helpFunctions.getSortedColumn("alignment"));

        // Pass previously selected data
        model.addAttribute("name", npc.getName());
        model.addAttribute("race", npc.getRace());
        model.addAttribute(CLASS_TEXT, npc.getGenClass());
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
        model.addAttribute(SWITCH, true);


        // Make changes
        List<String> changes = new ArrayList<>(4);
        changes.add(race);
        changes.add(genClass);
        changes.add(alignment);
        changes.add(name);

        // update NPC
        UiHistoryEditUpdate uiHistoryEditUpdate = new UiHistoryEditUpdate();

        updateNPC = uiHistoryEditUpdate.updateHistoryEdit(changes, npc);

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
        // Prepare shared parameters
        model.addAttribute("name", npc.getName());
        model.addAttribute("race", npc.getRace());
        model.addAttribute(CLASS_TEXT, npc.getGenClass());
        model.addAttribute("selectedAlignment", npc.getAlignment());
        model.addAttribute("selectedSkills", npc.getSkillList());
        model.addAttribute("selectedSenses", npc.getSenses());
        model.addAttribute("npc", npc);

        // extra data (Level, Jobs, etc.)
        model.addAttribute("level", npc.getLevel());
        model.addAttribute("jobs", helpFunctions.getSortedColumn("job"));
        model.addAttribute("job", npc.getJob());
        model.addAttribute("deities", dbms.getColWithPref(DEITY_TEXT, npc.getAlignment()));
        model.addAttribute(DEITY_TEXT, npc.getAlignment());

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
        model.addAttribute(WALKING_SPEED, walkingSpeed.replaceAll("\\D+", ""));
        model.addAttribute("flyingSpeed", flyingSpeed.replaceAll("\\D+", ""));

        // Languages
        model.addAttribute("languages", dbms.onlyColumn("language"));
        model.addAttribute("language", String.join(", ", npc.getLanguages()));

        // Ability Scores
        formatNpcDataForStats(npc, model);

        // Inventory
        model.addAttribute("armors", dbms.onlyColumn(ARMOR_TEXT));
        model.addAttribute(ARMOR_TEXT, String.join(", ", npc.getArmor()));

        model.addAttribute("weapons", dbms.onlyColumn(WEAPON_TEXT));
        model.addAttribute(WEAPON_TEXT, String.join(", ", npc.getWeaponList()));

        // Skills
        model.addAttribute("skills", helpFunctions.listToMap(npc.getSkillList()));
        model.addAttribute("availableSkills", dbms.onlyColumn(SKILL_TEXT));

        // Senses
        model.addAttribute("senses", getSelectedSenses());
        model.addAttribute("availableSensesFeet", availableSensesFeet);


        // Spells
        String spells = "spells";
        // all spells from the DB
        model.addAttribute("cantrips", dbms.getMultRowWithPref(spells,"cantrip",null));
        model.addAttribute("firstSpell",dbms.getMultRowWithPref(spells,"1",null));
        model.addAttribute("secondSpell",dbms.getMultRowWithPref(spells,"2",null));
        model.addAttribute("thirdSpell",dbms.getMultRowWithPref(spells,"3",null));
        model.addAttribute("fourthSpell",dbms.getMultRowWithPref(spells,"4",null));
        model.addAttribute("fifthSpell",dbms.getMultRowWithPref(spells,"5",null));
        model.addAttribute("sixthSpell",dbms.getMultRowWithPref(spells,"6",null));
        model.addAttribute("seventhSpell",dbms.getMultRowWithPref(spells,"7",null));
        model.addAttribute("eightSpell",dbms.getMultRowWithPref(spells,"8",null));
        model.addAttribute("ninthSpell",dbms.getMultRowWithPref(spells,"9",null));

        // all spells from the npc
        helpFunctions.addSpellsToModel(npc, model);

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


    /**
     * Handles the saving of edits made to an NPC entity and updates its history entry.
     * After processing, the session status is marked as complete and redirects to the history page.
     *
     * @param sessionStatus the status of the current web session, used to indicate that the session is complete
     * @return a redirection path to the history page
     */
    @PostMapping("saveNpc")
    public String saveEdit(SessionStatus sessionStatus) {
        dbms.updateHistoryEntry(updateNPC, updateNPC.getId(), dbms.getHistoryEntry(updateNPC.getId()).getEntryAge());

        sessionStatus.setComplete();

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
        editListAttributes(selectedArmors, ARMOR_TEXT, model);
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
        editListAttributes(selectedWeapons, WEAPON_TEXT, model);
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
        model.addAttribute(SWITCH, true);

        // update list
        switch (type) {
            case "lang":
                updateNPC.setLanguages(selectedList);
                break;
            case ARMOR_TEXT:
                updateNPC.setArmor(helpFunctions.listToString(selectedList));
                break;
            case SKILL_TEXT:
                updateNPC.setSkillList(selectedList);
                break;
            case WEAPON_TEXT:
                updateNPC.setWeaponList(selectedList);
                break;
            case "sense":
                updateNPC.setSenses(helpFunctions.listToString(selectedList));
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
     * Formats the ability and its corresponding value into a single string representation.
     *
     * @param ability the name or identifier of the ability
     * @param val the integer value associated with the ability; positive values are prefixed with a plus sign
     * @return a formatted string combining the ability and its value, with appropriate sign formatting for the value
     */
    private String formatThrow(String ability, int val) {
        return ability + " " + (val >= 0 ? "+" : "") + val;
    }



    /**
     * Updates the saving throws for a given NPC by adjusting the specified ability key with a new value.
     * The method updates the NPC's saving throws list and rebuilds it to include the modified or newly added value.
     *
     * @param key the ability key to update (e.g., "Str", "Dex", "Con").
     * @param value the value to assign to the specified ability key.
     * @param updateNPC the NPC object whose saving throws are being updated.
     * @return a ResponseEntity with an HTTP status indicating the result of the operation.
     */
    @PostMapping("/editSavingThrows")
    @ResponseBody
    public ResponseEntity<Void> editSavingThrows(@RequestParam String key, @RequestParam int value,
                                                 @ModelAttribute("updateNPC") NpcClass updateNPC) {
        List<String> allAbilities = Arrays.asList("Str", "Dex", "Con", "Int", "Wis", "Cha");

        // Existing throws → Map<"Str", 1> etc.
        Map<String, Integer> throwsMap = new HashMap<>();
        for (String saving : updateNPC.getSavingThrows()) {
            try {
                throwsMap.put(saving.substring(0, 3), Integer.parseInt(saving.substring(4)));
            } catch (NumberFormatException e) {
                log.error("Invalid number format: {}", saving);
            }
        }

        // Update/add the target key
        throwsMap.put(key, value);

        // Rebuild list
        List<String> updatedThrowsList = allAbilities.stream()
                .map(ability -> formatThrow(ability, throwsMap.getOrDefault(ability, 0)))
                .collect(Collectors.toList());

        // Non-standard key (z.B. custom ability)
        if (!allAbilities.contains(key)) {
            updatedThrowsList.add(formatThrow(key, value));
        }

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
    public String editSkills(@RequestParam(value = SKILL_TEXT, required = false) List<String> selectedSkills,
                             @RequestParam Map<String, String> allParams,
                             Model model) {
        List<String> skills = new ArrayList<>();

        if (selectedSkills == null) {
            skills.add("none");
        }

        for (String skill : selectedSkills) {
            for (Map.Entry<String,String> param : allParams.entrySet()) {
                if (param.getKey().contains(skill)) {
                    if(Integer.parseInt(param.getValue()) > 0) {
                        skills.add(skill + " +" + param.getValue());
                    } else {
                        skills.add(skill + " " + param.getValue());
                    }
                }
            }
        }

        editListAttributes(skills, SKILL_TEXT, model);
        return "edit";
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
                            Matcher matcher = Pattern.compile("\\b(\\d+)").matcher(sense);
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
    public String editSenses(@RequestParam("sense") List<String> selectedSenses, @RequestParam Map<String, String> allParams, Model model) {
        List<String> senses = new ArrayList<>();

        for (String sense : selectedSenses) {
            for (Map.Entry<String,String> param : allParams.entrySet()) {
                if (param.getKey().contains(sense)) {
                    if(sense.equals("Passive Perception")) {
                        senses.add(sense + " " + param.getValue());
                    }else{
                     senses.add(sense + " " + param.getValue() + "feet");
                    }
                    break;
                }
            }
        }

        if(selectedSenses.contains("your blind in the dark... live with it")) {
            senses.add("your blind in the dark... live with it");
        }

        editListAttributes(senses, "sense", model);
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
        String level;
        String name;
        List<String> spellList = getAllSpells();

        // remove old spells
        removeSpell(spellList, selectedSpells);

        // add new spells
        for (String spell : selectedSpells) {
            level = (spell.substring(0,spell.indexOf(";"))).replaceAll("[\n\r]", "_");
            name = (spell.substring(spell.indexOf(";")+1)).replaceAll("[\n\r]", "_");

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
                    log.warn("Unknown level: {} for spell: {}.", level, name);
                    break;
            }
        }

        model.addAttribute(SWITCH, true);

        prepareEditAttributes(model, updateNPC);
        return "edit";
    }


    /**
     * Removes spells from the NPC's spell list if they are not present in the list of selected spells.
     *
     * @param spellList The list of spells to be checked and potentially removed.
     * @param selectedSpells The list of selected spells to determine which spells should remain.
     * @author Marie
     */
    private void removeSpell(List<String> spellList, List<String> selectedSpells) {
        for (String spells : spellList) {
            boolean contains = selectedSpells.stream()
                    .map(s -> s.substring(s.indexOf(";") + 1))
                    .anyMatch(name -> name.contains(spells));

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
    }


    /**
     * Retrieves a list of all spells, including cantrips and spells from first to ninth level.
     * Combines spells from various levels into a single list.
     *
     * @return a list of all spells as strings
     * @author Marie
     */
    private List<String> getAllSpells() {
        List<String> spellList = new ArrayList<>();

        addSpells(spellList, updateNPC::getSpellCantrips);
        addSpells(spellList, updateNPC::getFirstLevelSpell);
        addSpells(spellList, updateNPC::getSecondLevelSpell);
        addSpells(spellList, updateNPC::getThirdLevelSpell);
        addSpells(spellList, updateNPC::getFourthLevelSpell);
        addSpells(spellList, updateNPC::getFifthLevelSpell);
        addSpells(spellList, updateNPC::getSixthLevelSpell);
        addSpells(spellList, updateNPC::getSeventhLevelSpell);
        addSpells(spellList, updateNPC::getEightLevelSpell);
        addSpells(spellList, updateNPC::getNinthLevelSpell);

        return spellList;
    }


    /**
     * Adds spells to the provided spell list using the spell data supplied by the given supplier.
     *
     * @param spellList the list to which the spells should be added
     * @param spellSupplier a supplier that provides a string of spell names, separated by commas
     * @author Marie
     */
    private void addSpells(List<String> spellList, Supplier<String> spellSupplier) {
        String spells = spellSupplier.get();
        if (spells != null && !spells.isEmpty()) {
            spellList.addAll(Arrays.asList(spells.split(", ")));
        }
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

        // Update logic
        switch (fieldName) {
            case "job":
                updateNPC.setJob(fieldValue);
                break;
            case "level":
                updateNPC.setLevel(Integer.parseInt(fieldValue));
                break;
            case DEITY_TEXT:
                updateNPC.setDeity(fieldValue);
                break;
            case "age":
                updateNPC.setAge(Integer.parseInt(fieldValue));
                break;
            case "gender":
                setGenderFromInput(updateNPC, fieldValue);
                break;
            case "AC":
                updateNPC.setArmorClass(Integer.parseInt(fieldValue));
                break;
            case "HP":
                updateNPC.setHealthPoints(Integer.parseInt(fieldValue));
                break;
            case WALKING_SPEED, "flyingSpeed":
                List<String> speedList = new ArrayList<>();

                // changes at walking
                if (fieldName.equals(WALKING_SPEED)) {
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


    /**
     * Sets the gender of the specified NPC based on the input value.
     * If the input value is "Random" (case-insensitive), a random gender
     * is assigned using the random generator. Otherwise, the input
     * value is directly assigned as the gender.
     *
     * @param updateNPC the NPC whose gender is being updated
     * @param fieldValue the input value specifying the gender or the keyword "Random"
     */
    private void setGenderFromInput(NpcClass updateNPC, String fieldValue) {
        if(fieldValue.equalsIgnoreCase("Random")){
            updateNPC.setGender(randomGenerator.randGender());
        } else {
            updateNPC.setGender(fieldValue);
        }
    }


    /**
     * Updates the description of the given NPC object by setting its description
     * field with values collected from a map.
     *
     * @param value the value to be associated with the specified field in the description map
     * @param descKey the key to be used for storing the value in the description map
     * @param updateNPC the NPC object to be updated with the new description
     * @return a ResponseEntity with HTTP status 200 (OK) indicating that the operation was successful
     * @author Marie
     */
    @PostMapping("/updateDescription")
    @ResponseBody
    public ResponseEntity<Void> updateDescription(@RequestParam String value, @RequestParam String descKey, @ModelAttribute("updateNPC") NpcClass updateNPC) {
        descriptionMap.put(descKey, value);

        List<String> desriptionList = new ArrayList<>();

        for(Map.Entry<String, String> entry : descriptionMap.entrySet()) {
            desriptionList.add(entry.getValue());
        }

        updateNPC.setDescription(desriptionList);

        return ResponseEntity.ok().build();
    }
}
