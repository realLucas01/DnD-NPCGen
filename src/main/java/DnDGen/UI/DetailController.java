package DnDGen.UI;

import DnDGen.DB.DBMS;
import DnDGen.TSWP1.NpcClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @Autowired
    public DetailController(DBMS dbms) {
        this.dbms = dbms;
    }

    /**
     * Lädt einen NPC anhand seiner ID und zeigt ihn auf der Detailseite an.
     * @param id ID des NPCs aus der Datenbank
     * @param model Model, das die NPC-Daten für Thymeleaf bereitstellt
     * @return Die Thymeleaf-Template-Seite "detail"
     */
    @GetMapping("/detail")
    public String getNpcDetail(@RequestParam("id") int id, Model model) {
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
        model.addAttribute("abilityScores", getAbilityScoresWithModifiers(npc));
        model.addAttribute("savingThrows", formatSavingThrows(npc));
        model.addAttribute("allAbilities", List.of("Str", "Dex", "Con", "Int", "Wis", "Cha"));

        addMovementAndSensesToModel(npc, model);
        addSpellsToModel(npc, model);
        addDescriptionToModel(npc, model);
    }

    private List<String> getAbilityScoresWithModifiers(NpcClass npc) {
        List<String> result = new ArrayList<>();
        List<Integer> scores = npc.getAbilityScores();
        List<Integer> mods = npc.getAbilityModifier();

        for (int i = 0; i < scores.size(); i++) {
            String mod = (mods.get(i) >= 0) ? "+" + mods.get(i) : String.valueOf(mods.get(i));
            result.add(scores.get(i) + " " + mod);
        }
        return result;
    }

    private List<String> formatSavingThrows(NpcClass npc) {
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

    private void addMovementAndSensesToModel(NpcClass npc, Model model) {
        List<String> speeds = npc.getMovementSpeedList();
        model.addAttribute("walkingSpeed", speeds.stream().filter(s -> s.contains("walking")).findFirst().orElse(""));
        model.addAttribute("flyingSpeed", speeds.stream().filter(s -> s.contains("flying")).findFirst().orElse(""));

        // Nullprüfung und alternative Handhabung
        String sensesValue = npc.getSenses();  // Wert aus der Methode holen
        List<String> senses = (sensesValue != null && !sensesValue.isEmpty())
                ? List.of(sensesValue.split(",\\s*"))
                : Collections.emptyList();  // Leere Liste verwenden, falls null oder leer

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

    private void addDescriptionToModel(NpcClass npc, Model model) {
        List<String> descriptions = npc.getDescription();
        List<String> titles = List.of("Eyes", "Hair Color", "Hairstyle", "Skin", "Trait", "Mannerism",
                "Bond", "Flaw", "Ideal", "Voice", "Clothing", "Attitude", "Size", "Height");

        List<String> formattedDesc = new ArrayList<>();
        for (int i = 0; i < descriptions.size(); i++) {
            formattedDesc.add(titles.get(i) + ": " + descriptions.get(i));
        }

        model.addAttribute("descriptionList", formattedDesc);
    }

    private List<String> splitString(String input) {
        return (input == null || input.isEmpty()) ? Collections.emptyList() : List.of(input.split("\\s*,\\s*"));
    }
}
