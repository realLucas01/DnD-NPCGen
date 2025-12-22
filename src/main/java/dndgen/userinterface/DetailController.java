package dndgen.userinterface;

import dndgen.database.DBMS;
import dndgen.tswp1.NpcClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Controller for the detail page of an NPC.
 * Displays all information of the selected NPC.
 * @author Marie
 */
@Controller
public class DetailController {
    private final DBMS dbms;
    private final HelpFunctions helpFunctions;

    @Autowired
    public DetailController(DBMS dbms, HelpFunctions helpFunctions) {
        this.dbms = dbms;
        this.helpFunctions = helpFunctions;
    }

    /**
     * Loads an NPC by its ID and displays it on the detail page.
     * @param id ID of the NPC from the database
     * @param model Model that provides the NPC data for Thymeleaf
     * @return The Thymeleaf template page "detail"
     */
    @GetMapping("/detail")
    public String getNpcDetail(@RequestParam(required = false) Integer id, Model model, RedirectAttributes redirectAttributes) {
        if (id == null) {
            redirectAttributes.addFlashAttribute("error", "Invalid NPC ID.");
            return "redirect:/history";
        }

        NpcClass npc = dbms.convertDatabaseEntryToNpcClass(dbms.getHistoryEntry(id));

        formatNpcDataForModel(npc, model);
        return "detail";
    }

    /**
     * Prepares the NPC data for the model.
     * @param npc The NPC to be displayed
     * @param model The model into which the data is loaded
     */
    private void formatNpcDataForModel(NpcClass npc, Model model) {
        model.addAttribute("npc", npc);
        model.addAttribute("abilityScores", helpFunctions.getAbilityScoresWithModifiers(npc));
        model.addAttribute("savingThrows", helpFunctions.formatSavingThrows(npc));
        model.addAttribute("allAbilities", List.of("Str", "Dex", "Con", "Int", "Wis", "Cha"));

        addMovementAndSensesToModel(npc, model);
        addSpellsToModel(npc, model);
        addDescriptionToModel(npc, model);
    }


    /**
     * Adds movement speed and senses to the provided model based on the NPC's attributes.
     *
     * @param npc The NPC object containing speed and senses.
     * @param model The model to which speed and senses will be added.
     */
    private void addMovementAndSensesToModel(NpcClass npc, Model model) {
        List<String> speeds = npc.getMovementSpeedList();
        model.addAttribute("walkingSpeed", speeds.stream().filter(s -> s.contains("walking")).findFirst().orElse(""));
        model.addAttribute("flyingSpeed", speeds.stream().filter(s -> s.contains("flying")).findFirst().orElse(""));

        // Null check and alternative handling
        String sensesValue = npc.getSenses();  // Get value from the method
        List<String> senses = (sensesValue != null && !sensesValue.isEmpty())
                ? new ArrayList<>(List.of(sensesValue.split(",\\s*")))
                : new ArrayList<>();  // Use an empty list if null or empty
        

        model.addAttribute("senses", senses);  // Add senses to the model
    }


    /**
     * Adds spell information from the provided NPC object to the model by splitting the spell strings
     * and categorizing them by spell level.
     *
     * @param npc The NPC object containing spell information for different levels.
     * @param model The model to which the processed spell data will be added.
     */
    private void addSpellsToModel(NpcClass npc, Model model) {
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
    }


    /**
     * Adds a formatted description of an NPC to the provided model. The descriptions are paired with predefined titles
     * and formatted for display. Additionally, a summarized description text is generated and added to the model.
     *
     * @param npc The NPC object containing description details to be formatted.
     * @param model The model to which the formatted description and the generated description text will be added.
     */
    private void addDescriptionToModel(NpcClass npc, Model model) {
        List<String> descriptions = npc.getDescription();
        List<String> titles = List.of("Eyes", "Hair Color", "Hairstyle", "Skin", "Trait", "Mannerism",
                "Bond", "Flaw", "Ideal", "Voice", "Clothing", "Attitude", "Size", "Height");

        List<String> formattedDesc = new ArrayList<>();
        for (int i = 0; i < descriptions.size(); i++) {
            formattedDesc.add(titles.get(i) + ": " + descriptions.get(i));
        }

        model.addAttribute("descriptionList", formattedDesc);
        model.addAttribute("descriptionText", helpFunctions.generateDescriptionText(npc));
    }
}
