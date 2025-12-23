package dndgen.userinterface;

import dndgen.tswp1.NpcClass;
import dndgen.tswp1.RandomNpc;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.function.Function;

/**
 * @author Marie
 */
@Controller
public class IndexController {
    private final RandomNpc randomNpc;
    private final HelpFunctions helpFunctions;
    private static final String RANDOM = "Random";

    
    @Autowired
    public IndexController(RandomNpc randomNpc, HelpFunctions helpFunctions) {
        this.randomNpc = randomNpc;
        this.helpFunctions = helpFunctions;
    }

    /**
     * Handles GET requests to the "/index" endpoint and prepares the model
     * with the necessary attributes for the view.
     *
     * @param model The model object used to pass attributes to the view
     * @return the name of the Thymeleaf template to display the index (generator) page
     */
    @GetMapping(value = {"/", "/index"})
    String getGeneratorPage(Model model, HttpSession session) {
        // Check if an NPC exists in the session
        NpcClass existingNpc = (NpcClass) session.getAttribute("currentNpc");
        DropdownSelection existingSelection = (DropdownSelection) session.getAttribute("currentSelection");

        if (existingNpc != null) {
            // If an NPC exists, use the stored data
            formatNpcDataForModel(existingNpc, model);
            populateDropdowns(model, existingSelection);
            model.addAttribute("npc", existingNpc);
        } else {
            // If no NPC exists, only initialize the dropdown menus
            populateDropdowns(model, new DropdownSelection("", RANDOM, RANDOM, RANDOM, RANDOM, RANDOM, RANDOM));
        }
        return "index";

    }

    
    /**
     * Generates an NPC (Non-Player Character) based on the specified parameters and adds it to the model.
     * Prepares dropdown menu data, creates the NPC, formats the NPC data, and returns the corresponding
     * Thymeleaf template.
     *
     * @param name     the name of the NPC; can be null or empty
     * @param race     the race of the NPC; can be null or empty
     * @param genClass the general class or archetype of the NPC; can be null or empty
     * @param job      the specific job or role of the NPC; can be null or empty
     * @param level    the level or power level of the NPC; can be null or empty
     * @param abilisc  the ability points or description of the NPC; can be null or empty
     * @param gender   the gender of the NPC; can be null or empty
     * @param model    the model to which dropdown menu data and NPC data are added
     * @return the name of the Thymeleaf template that renders the NPC index (generator) page
     */
    @PostMapping("/index")
    public String generateNPC(@RequestParam(required = false) String name,
                              @RequestParam(required = false) String race,
                              @RequestParam(required = false) String genClass,
                              @RequestParam(required = false) String job,
                              @RequestParam(required = false) String level,
                              @RequestParam(required = false) String abilisc,
                              @RequestParam(required = false) String gender,
                              Model model,
                              HttpSession session) {

        DropdownSelection selection = new DropdownSelection(name, race, genClass, job, level, abilisc, gender);
        populateDropdowns(model, selection);

        NpcClass npc = createNPC(name, race, genClass, job, level, abilisc, gender);
        formatNpcDataForModel(npc, model);

        // Save NPC and Selection in the session
        session.setAttribute("currentNpc", npc);
        session.setAttribute("currentSelection", selection);

        return "redirect:/index";
    }

    
    /**
     * Populates dropdown options in the specified model with sorted data for various categories.
     *
     * @param model The model object to which dropdown attributes are added
     */
    private void populateDropdowns(Model model, DropdownSelection selection) {
        // Add dropdown data
        model.addAttribute("races", helpFunctions.getSortedColumn("race"));
        model.addAttribute("classes", helpFunctions.getSortedColumn("class"));
        model.addAttribute("jobs", helpFunctions.getSortedColumn("job"));
        model.addAttribute("abilisc", helpFunctions.getSortedColumn("abilisc"));

        // Pass previously selected data
        model.addAttribute("name", selection.getName());
        model.addAttribute("race", selection.getRace());
        model.addAttribute("selectedClass", selection.getGenClass());
        model.addAttribute("selectedJob", selection.getJob());
        model.addAttribute("selectedLevel", selection.getLevel());
        model.addAttribute("selectedAbilisc", selection.getAbilisc());
        model.addAttribute("selectedGender", selection.getGender());
    }


    /**
     * Creates a new NPC (Non-Player Character) with random or specific attributes
     * based on the provided input parameters.
     *
     * @param name         The name of the NPC; if "random" is provided, a random name will be generated.
     * @param race         The race of the NPC; if "random" is provided, a random race will be selected.
     * @param genClass     The general class (e.g., warrior, mage) of the NPC; if "random" is provided, a random class will be selected.
     * @param job          The specific job or task of the NPC; if "random" is provided, a random job will be assigned.
     * @param level        The level of the NPC; if "random" is provided, a random integer level will be generated.
     * @param abilityScore The ability scores of the NPC (e.g., strength, intelligence); if "random" is provided, a random value will be generated.
     * @param gender       The gender of the NPC; if "random" is provided, a random gender will be selected.
     * @return An instance of the NpcClass class representing the generated NPC with specific or random attributes.
     */
    private NpcClass createNPC(String name, String race, String genClass, String job, String level, String abilityScore, String gender) {
        String finalName = randomString(name);
        String finalRace = random(race);
        String finalClass = random(genClass);
        String finalJob = random(job);
        Integer finalLevel = randomLevel(level);
        String finalAbilityScore = random(abilityScore);
        String finalGender = randomString(gender);

        return randomNpc.createRandomNPC(finalName, finalRace, finalClass, finalJob, finalLevel, finalAbilityScore, finalGender);
    }


    /**
     * Formats NPC data and adds various attributes to the provided model for display in the user interface.
     *
     * @param npc   The NPC object containing data to be formatted and added to the model.
     * @param model The model object to which the NPC data and attributes are added as key-value pairs.
     */
    private void formatNpcDataForModel(NpcClass npc, Model model) {
        Map<String, Object> formattedNpcData = formatNpcData(npc);
        model.addAttribute("npc", formattedNpcData);

        model.addAttribute("abilityScores", helpFunctions.getAbilityScoresWithModifiers(npc));
        model.addAttribute("savingThrows", helpFunctions.formatSavingThrows(npc));
        model.addAttribute("allAbilities", Arrays.asList("Str", "Dex", "Con", "Int", "Wis", "Cha"));

        addMovementAndSensesToModel(npc, model);
        addSpellsToModel(npc, model);
        addDescriptionToModel(npc, model);

        model.addAttribute("hitPoints", npc.getHealthPoints());
        model.addAttribute("armorClass", npc.getArmorClass());
        model.addAttribute("armor", npc.getArmor());
        model.addAttribute("weapons", npc.getWeaponList());
        model.addAttribute("skills", npc.getSkillList());
    }


    /**
     * Inserts the movement rates and senses of an NPC into the provided model.
     * The method extracts walking and flying speeds from the NPC and adds these along
     * with a list of senses to the model.
     *
     * @param npc   The NPC object from which movement rates and senses are taken.
     * @param model The model object to which movement rates and senses are added as attributes.
     */
    private void addMovementAndSensesToModel(NpcClass npc, Model model) {
        String walkingSpeed = "";
        String flyingSpeed = "";
        for (String speed : npc.getMovementSpeedList()) {
            if (speed.contains("walking")) {
                walkingSpeed = speed;
            } else if (speed.contains("flying")) {
                flyingSpeed = speed;
            }
        }

        model.addAttribute("walkingSpeed", walkingSpeed);
        model.addAttribute("flyingSpeed", flyingSpeed);

        model.addAttribute("languages", npc.getLanguages());

        List<String> sensesList = Arrays.asList(npc.getSenses().split(",\\s*"));
        model.addAttribute("senses", sensesList);
    }


    /**
     * Adds the spells of an NPC at various levels as attributes to the provided model.
     * Converts comma-separated spell strings into lists and assigns them to the corresponding
     * spell level attributes in the model for display in the view.
     *
     * @param npc   The NPC object containing the spell details for various levels.
     * @param model The model object to which the spell attributes are added.
     */
    private void addSpellsToModel(NpcClass npc, Model model) {
        Function<String, List<String>> stringToList = str ->
                (str == null || str.isEmpty()) ? Collections.emptyList() : Arrays.asList(str.split("\\s*,\\s*"));

        model.addAttribute("spellCantrips", stringToList.apply(npc.getSpellCantrips()));
        model.addAttribute("spellFirstLevel", stringToList.apply(npc.getFirstLevelSpell()));
        model.addAttribute("spellSecondLevel", stringToList.apply(npc.getSecondLevelSpell()));
        model.addAttribute("spellThirdLevel", stringToList.apply(npc.getThirdLevelSpell()));
        model.addAttribute("spellFourthLevel", stringToList.apply(npc.getFourthLevelSpell()));
        model.addAttribute("spellFifthLevel", stringToList.apply(npc.getFifthLevelSpell()));
        model.addAttribute("spellSixthLevel", stringToList.apply(npc.getSixthLevelSpell()));
        model.addAttribute("spellSeventhLevel", stringToList.apply(npc.getSeventhLevelSpell()));
        model.addAttribute("spellEighthLevel", stringToList.apply(npc.getEightLevelSpell()));
        model.addAttribute("spellNinthLevel", stringToList.apply(npc.getNinthLevelSpell()));
    }


    /**
     * Adds a formatted description of an NPC to the provided model.
     * The method formats individual descriptive attributes of the NPC, such as eyes, hair, and traits,
     * into a structured list and also generates a cohesive description text for display.
     *
     * @param npc   The NPC object containing the description details to be added to the model.
     * @param model The model object to which the formatted description list and text are added.
     */
    private void addDescriptionToModel(NpcClass npc, Model model) {
        List<String> descriptionTitles = Arrays.asList("Eyes", "Hair Color", "Hairstyle", "Skin", "Trait", "Mannerism",
                "Bond", "Flaw", "Ideal", "Voice", "Clothing", "Attitude", "Size", "Height");

        List<String> formattedDescription = new ArrayList<>();
        List<String> npcDescription = npc.getDescription();

        for (int i = 0; i < npcDescription.size(); i++) {
            formattedDescription.add(String.format("%s: %s", descriptionTitles.get(i), npcDescription.get(i)));
        }

        model.addAttribute("descriptionList", formattedDescription);
        model.addAttribute("descriptionText", helpFunctions.generateDescriptionText(npc));
    }


    /**
     * Checks if a string is "random". If so, returns `null`.
     * @param input The input string
     * @return `null` if "random", otherwise the original string
     */
    public String random(String input) {
        return (RANDOM.equalsIgnoreCase(input) || input.isEmpty()) ? null : input;
    }


    /**
     * Validates a level input. If "random" was selected, returns `null`.
     * @param input The level input as a string
     * @return `null` if "random", otherwise the integer value
     */
    public Integer randomLevel(String input) {
        return (RANDOM.equalsIgnoreCase(input) || input.isEmpty()) ? null : Integer.parseInt(input);
    }


    /**
     * Checks if a string is empty. If yes, returns `null`.
     * @param string The input string
     * @return `null` if empty or "random", otherwise the string itself
     */
    public String randomString(String string) {
        return (string == null || string.equalsIgnoreCase(RANDOM) || string.isEmpty()) ? null : string;
    }


    /**
     * Formats the provided NPC data into a map structure for further processing or display.
     *
     * @param npc The NPC object containing data to be formatted.
     * @return A map where keys are property names (e.g., "name", "level") and values are the corresponding NPC data.
     */
    private Map<String, Object> formatNpcData(NpcClass npc) {
        Map<String, Object> npcData = new HashMap<>();

        npcData.put("name", npc.getName());
        npcData.put("level", npc.getLevel());
        npcData.put("genClass", npc.getGenClass());
        npcData.put("race", npc.getRace());
        npcData.put("job", npc.getJob());
        npcData.put("alignment", npc.getAlignment());
        npcData.put("age", npc.getAge());
        npcData.put("gender", npc.getGender());
        npcData.put("deity", npc.getDeity());

        return npcData;
    }
}
