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

    /**
     * Handles the editing of an NPC by preparing the required data, attributes, and UI elements
     * for the editing view. It loads the NPC's details based on a provided ID, sets necessary
     * model attributes, and returns the name of the view for rendering.
     *
     * @param id the unique identifier of the NPC to be edited
     * @param model the model to which NPC attributes and other data are added for rendering the view
     * @return the name of the view to be returned for NPC editing
     *
     * @author Marie
     */
    @GetMapping("/edit")
    public String editNpc(@RequestParam Integer id, Model model) {
        // NPC-Objekt erstellen
        npc = new NpcClass();
        npc.historyEntryToNpc(dbms.getHistoryEntry(id));

        // noch keine Änderungen → Kernelemente können geändert werden
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

        // Update NPC funktioniert nicht → wirft Fehler → NPC Daten, die angezeigt werden, werden statisch immer aus NPC ide 2 genommen
        updateNPC.historyEntryToNpc(dbms.getHistoryEntry(2));

        return "edit";
    }

    /**
     * Handles the editing of an NPC's attributes by updating the provided data, setting the necessary
     * model attributes, and preparing the view for rendering after the changes are made.
     *
     * @param name the name of the NPC being edited
     * @param race the race of the NPC to be updated
     * @param genClass the class of the NPC to be updated
     * @param alignment the alignment of the NPC to be updated
     * @param model the model used to store updated attributes for rendering the view
     * @return the name of the view to be returned for the editing screen
     */
    @PostMapping("/editFirst")
    public String editedNPC(@RequestParam String name, @RequestParam String race, @RequestParam String genClass, @RequestParam String alignment, Model model) {
        // Änderungen → Kernelemente können NICHT geändert werden
        model.addAttribute(switchValue, true);

        // Änderungen vornehmen
        List<String> changes = new ArrayList<>(4);
        changes.add(race);
        changes.add(genClass);
        changes.add(alignment);
        changes.add(name);

        // NPC aktualisieren
        UiHistoryEditUpdate uiHistoryEditUpdate = new UiHistoryEditUpdate();
        // TODO updateNPC fixen
        // updateNPC = uiHistoryEditUpdate.updateHistoryEdit(changes, npc);

        // Anzeigen-Logik vorbereiten
        prepareEditAttributes(model, updateNPC);

        return "edit";
    }

    /**
     * Prepares the attributes for editing an NPC by setting various properties,
     * such as general attributes, additional data, movement values, languages,
     * ability scores, inventory, and descriptions, into the provided model.
     *
     * @param model the model used to prepare attributes for rendering the editing view
     * @param npc the NPC object whose attributes are being prepared for editing
     *
     * @author Marie
     */
    private void prepareEditAttributes(Model model, NpcClass npc) {
        // TODO Funktion verkleinern -> Auslagern/Refactoring

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
        model.addAttribute("armor", String.join(", ", npc.getArmor())); // TODO ist String, sollte wahrscheinlich List sein, wegen mehrerer möglichen Objekten
        model.addAttribute("weapons", dbms.onlyColumn("weapon"));
        model.addAttribute("weapon", String.join(", ", npc.getWeaponList()));

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
        // TODO Das ganze Speichern des bearbeiteten NPC fehlt
        return "redirect:/detail?id=" + npc.getId();
    }

    /**
     * Handles the editing of selected languages for an NPC by updating the language list
     * and preparing the model with the updated information.
     *
     * @param selectedLanguages the list of languages selected by the user
     * @param model the model used to prepare attributes for view rendering
     * @return a string indicating the name of the view to be rendered
     *
     * @author Marie
     */
    @PostMapping("editLanguages")
    public String editLanguages(@RequestParam("lang") List<String> selectedLanguages, Model model) {
        editListLangWeaponArmors(selectedLanguages, "lang", model);
        return "edit";

    }

    /**
     * Handles the editing of selected armors for an NPC by updating the armor list
     * and preparing the model with the updated information.
     *
     * @param selectedArmors the list of armors selected by the user
     * @param model the model used to prepare attributes for view rendering
     * @return a string indicating the name of the view to be rendered
     *
     * @author Marie
     */
    @PostMapping("editArmors")
    public String editArmors(@RequestParam("arm") List<String> selectedArmors, Model model) {
        editListLangWeaponArmors(selectedArmors, "armor", model);
        return "edit";
    }

    /**
     * Handles the editing of selected weapons for an NPC by updating the weapon list
     * and preparing the model with the updated information.
     *
     * @param selectedWeapons the list of weapons selected by the user
     * @param model the model used to prepare attributes for view rendering
     * @return a string indicating the name of the view to be rendered
     *
     * @author Marie
     */
    @PostMapping("editWeapons")
    public String editWeapons(@RequestParam("wea") List<String> selectedWeapons, Model model) {
        editListLangWeaponArmors(selectedWeapons, "weapon", model);
        return "edit";
    }

    /**
     * Updates the specified NPC's attributes (languages, armor, or weapons) based on the given type
     * and prepares the model for displaying updated information.
     *
     * @param selectedList the list of selected values to be assigned to the NPC's attribute
     * @param type the type of attribute to update ("lang" for languages, armor, weapon)
     * @param model the model used to prepare attributes for view rendering
     *
     * @author Marie
     */
    private void editListLangWeaponArmors(List<String> selectedList, String type, Model model) {
        model.addAttribute(switchValue, true);

        // Liste aktualisieren
        switch (type) {
            case "lang":
                updateNPC.setLanguages(selectedList);
                break;
            case "armor":
                updateNPC.setArmor(String.valueOf(selectedList));
                break;
            case "weapon":
                updateNPC.setWeaponList(selectedList);
        }

        // Update der Anzeige vorbereiten
        prepareEditAttributes(model, updateNPC);

    }

    
}
