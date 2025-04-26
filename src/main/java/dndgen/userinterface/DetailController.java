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
 * Controller für die Detailseite eines NPCs
 * zeigt alle Informationen des gewählten NPCs an.
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
     * Lädt einen NPC anhand seiner ID und zeigt ihn auf der Detailseite an.
     * @param id ID des NPCs aus der Datenbank
     * @param model Model, das die NPC-Daten für Thymeleaf bereitstellt
     * @return Die Thymeleaf-Template-Seite "detail"
     */
    @GetMapping("/detail")
    public String getNpcDetail(@RequestParam(required = false) Integer id, Model model, RedirectAttributes redirectAttributes) {
        if (id == null) {
            redirectAttributes.addFlashAttribute("error", "Ungültige NPC-ID.");
            return "redirect:/history";
        }

        NpcClass npc = new NpcClass();
        npc.historyEntryToNpc(dbms.getHistoryEntry(id));

        formatNpcDataForModel(npc, model);
        return "detail";
    }

    /**
     * Bereitet die NPC-Daten für das Model vor.
     * @param npc Der NPC, der angezeigt wird
     * @param model Model, in das die Daten geladen werden
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





    private void addMovementAndSensesToModel(NpcClass npc, Model model) {
        List<String> speeds = npc.getMovementSpeedList();
        model.addAttribute("walkingSpeed", speeds.stream().filter(s -> s.contains("walking")).findFirst().orElse(""));
        model.addAttribute("flyingSpeed", speeds.stream().filter(s -> s.contains("flying")).findFirst().orElse(""));

        // Nullprüfung und alternative Handhabung
        String sensesValue = npc.getSenses();  // Wert aus der Methode holen
        List<String> senses = (sensesValue != null && !sensesValue.isEmpty())
                ? new ArrayList<>(List.of(sensesValue.split(",\\s*")))
                : new ArrayList<>();  // Leere Liste verwenden, falls null oder leer
        

        model.addAttribute("senses", senses);  // Senses dem Model hinzufügen

    }

    private void addSpellsToModel(NpcClass npc, Model model) {
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

    // Inventory



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


    private List<String> splitString(String input) {
        return (input == null || input.isEmpty()) ? Collections.emptyList() : List.of(input.split("\\s*,\\s*"));
    }
}
