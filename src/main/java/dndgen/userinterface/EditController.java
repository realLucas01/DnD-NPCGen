package dndgen.userinterface;

import dndgen.database.DBMS;
import dndgen.tswp1.NpcClass;
import dndgen.tswp1.UiHistoryEditUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.ArrayList;
import java.util.List;

/**
 * @author Marie
 */
@Controller
public class EditController {
    private final DBMS dbms;
    private NpcClass npc;
    private final HelpFunctions helpFunctions;
    private NpcClass updateNPC;
    private final String switchValue = "switch";

    @Autowired
    public EditController(DBMS dbms, HelpFunctions helpFunctions) {
        this.dbms = dbms;
        this.helpFunctions = helpFunctions;
    }

    @GetMapping("/edit")
    public String editNpc(@RequestParam Integer id, Model model) {
        // NPC-Objekt erstellen
        npc = new NpcClass();
        npc.historyEntryToNpc(dbms.getHistoryEntry(id));

        // noch keinen Änderungen → Kernelemente können geändert werden
        model.addAttribute(switchValue, false);

        // Dropdown erstellen
        model.addAttribute("races", helpFunctions.getSortedColumn("race"));
        model.addAttribute("classes", helpFunctions.getSortedColumn("class"));
        model.addAttribute("alignments", helpFunctions.getSortedColumn("alignment"));

        // vorher gewählte Daten übergeben
        model.addAttribute("name", npc.getName());
        model.addAttribute("race", npc.getRace());
        model.addAttribute("class", npc.getGenClass());
        model.addAttribute("alignment", npc.getAlignment());

        updateNPC = new NpcClass();
        updateNPC.historyEntryToNpc(dbms.getHistoryEntry(2));

        return "edit";
    }

    @PostMapping("/editFirst")
    public String editedNPC(@RequestParam String name, @RequestParam String race, @RequestParam String genClass, @RequestParam String alignment, Model model) {
        // Änderungen → Kernelemente können NICHT geändert werden
        model.addAttribute(switchValue, true);

        // Änderungen vornehmen
        List<String> changes = new ArrayList<>(3);
        changes.add(race);
        changes.add(genClass);
        changes.add(alignment);
        changes.add(name);

        // NPC aktualisieren
        UiHistoryEditUpdate uiHistoryEditUpdate = new UiHistoryEditUpdate();
       // updateNPC = uiHistoryEditUpdate.updateHistoryEdit(changes, npc);

        // Anzeigen-Logik vorbereiten
        prepareEditAttributes(model, updateNPC);

        return "edit";
    }

    private void prepareEditAttributes(Model model, NpcClass npc) {
        // Gemeinsame Parameter vorbereiten
        model.addAttribute("name", npc.getName());
        model.addAttribute("selectedRace", npc.getRace());
        model.addAttribute("selectedClass", npc.getGenClass());
        model.addAttribute("selectedAlignment", npc.getAlignment());
        model.addAttribute("npc", npc);

        // Zusatzdaten (Level, Jobs, etc.)
        model.addAttribute("level", npc.getLevel());
        model.addAttribute("jobs", helpFunctions.getSortedColumn("job"));
        model.addAttribute("job", npc.getJob());
        model.addAttribute("deities", dbms.getColWithPref("deity", npc.getAlignment()));
        model.addAttribute("deity", npc.getAlignment());

        // Weitere Werte (Geschlecht, Alter, HP, Geschwindigkeit)
        model.addAttribute("sex", npc.getGender());
        model.addAttribute("age", npc.getAge());
        model.addAttribute("AC", npc.getArmorClass());
        model.addAttribute("HP", npc.getHealthPoints());

        // Bewegungswerte
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

        // Sprachen
        model.addAttribute("languages", dbms.onlyColumn("language"));
        model.addAttribute("language", String.join(", ", npc.getLanguages()));

        // Ability Scores
        model.addAttribute("abilityScores", helpFunctions.getAbilityScoresWithModifiers(npc));
        model.addAttribute("savingThrows", helpFunctions.formatSavingThrows(npc));
        model.addAttribute("allAbilities", List.of("Str", "Dex", "Con", "Int", "Wis", "Cha"));

        // Inventar
        model.addAttribute("armors", dbms.onlyColumn("armor"));
        model.addAttribute("armor", npc.getArmor());
        model.addAttribute("weapons", dbms.onlyColumn("weapon"));
        model.addAttribute("weapon", npc.getWeaponList());

        // Beschreibung
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


    @PostMapping("editSave")
    public String saveEdit() {

        return "redirect:/detail?id=" + npc.getId();
    }

    @PostMapping("editLanguages")
    public String editLanguages(@RequestParam("lang") List<String> selectedLanguages, Model model) {

        model.addAttribute(switchValue, true);
        // Sprachen aktualisieren
        updateNPC.setLanguages(selectedLanguages);

        // Update der Anzeige vorbereiten
        prepareEditAttributes(model, updateNPC);

        return "edit";

    }
    
}
