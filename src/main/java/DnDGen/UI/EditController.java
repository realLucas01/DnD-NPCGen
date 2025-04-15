package DnDGen.UI;

import DnDGen.DB.DBMS;
import DnDGen.TSWP1.NpcClass;
import DnDGen.TSWP1.UiHistoryEditUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.ArrayList;
import java.util.List;

import static DnDGen.UI.HelpFunctions.*;

/**
 * @author Marie
 */
@Controller
public class EditController {
    private final DBMS dbms;
    private NpcClass npc;
    private NpcClass updateNPC;
    private final HelpFunctions helpFunctions;
    
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
        model.addAttribute("switch", false);

        // Dropdown erstellen
        model.addAttribute("races", helpFunctions.getSortedColumn("race"));
        model.addAttribute("classes", helpFunctions.getSortedColumn("class"));
        model.addAttribute("alignments", helpFunctions.getSortedColumn("alignment"));

        // vorher gewählte Daten übergeben
        model.addAttribute("name", npc.getName());
        model.addAttribute("race", npc.getRace());
        model.addAttribute("class", npc.getGenClass());
        model.addAttribute("alignment", npc.getAlignment());

        return "edit";
    }

    @PostMapping("/editFirst")
    public String editNPC(@RequestParam String name, @RequestParam String race, @RequestParam String genClass, @RequestParam String alignment, Model model) {
        // Änderungen → Kernelemente können NICHT geändert werden
        model.addAttribute("switch", true);

        // Kernänderungen vornehmen
        List<String> changes = new ArrayList<>(3);
        
        changes.add(race);
        changes.add(genClass);
        changes.add(alignment);
        changes.add(name);
        
        // NPC bearbeiten
        UiHistoryEditUpdate uiHistoryEditUpdate = new UiHistoryEditUpdate();
        updateNPC = uiHistoryEditUpdate.updateHistoryEdit(changes, npc);

        // vorher gewählte Daten übergeben
        model.addAttribute("name", updateNPC.getName());
        model.addAttribute("selectedRace", updateNPC.getRace());
        model.addAttribute("selectedClass", updateNPC.getGenClass());
        model.addAttribute("selectedAlignment", updateNPC.getAlignment());

        // Level
        model.addAttribute("level", updateNPC.getLevel());

        // Job
        model.addAttribute("jobs", helpFunctions.getSortedColumn("job"));
        model.addAttribute("job", updateNPC.getJob());

        // Deity
        model.addAttribute("deities", dbms.getColWithPref("deity", updateNPC.getAlignment()));
        model.addAttribute("deity", updateNPC.getAlignment());

        // Sex
        model.addAttribute("sex", updateNPC.getGender());

        // Age
        model.addAttribute("age", updateNPC.getAge());

        // AC
        model.addAttribute("AC", updateNPC.getArmorClass());

        // HP
        model.addAttribute("HP", updateNPC.getHealthPoints());

        // Speed
        List<String> speeds = updateNPC.getMovementSpeedList();
        model.addAttribute("walkingSpeed", speeds.stream().filter(s -> s.contains("walking")).findFirst().orElse(""));
        model.addAttribute("flyingSpeed", speeds.stream().filter(s -> s.contains("flying")).findFirst().orElse(""));

        // Languages
        model.addAttribute("languages", dbms.onlyColumn("language"));
        model.addAttribute("language", updateNPC.getLanguages());

        // AbilityScores
        model.addAttribute("abilityScores", helpFunctions.getAbilityScoresWithModifiers(npc));
        model.addAttribute("savingThrows", helpFunctions.formatSavingThrows(npc));
        model.addAttribute("allAbilities", List.of("Str", "Dex", "Con", "Int", "Wis", "Cha"));

        // Inventar
            // Armor
            model.addAttribute("armors", dbms.onlyColumn("armor"));
            model.addAttribute("armor", updateNPC.getArmor());

            // Weapon
            model.addAttribute("weapons", dbms.onlyColumn("weapon"));
            model.addAttribute("weapon", updateNPC.getWeaponList());

        // Skills

        // Senses

        // Spells

        // Description
        List<String> descriptions = npc.getDescription();
        List<String> titles = List.of("Eyes", "Hair Color", "Hairstyle", "Skin", "Trait", "Mannerism",
                "Bond", "Flaw", "Ideal", "Voice", "Clothing", "Attitude", "Size", "Height");

        List<String> formattedDesc = new ArrayList<>();
        for (int i = 0; i < descriptions.size(); i++) {
            formattedDesc.add(titles.get(i) + ": " + descriptions.get(i));
        }

        model.addAttribute("descriptionList", formattedDesc);
        model.addAttribute("descriptionText", helpFunctions.generateDescriptionText(updateNPC));

        
        return "edit";
    }

    
    @PostMapping("editSave")
    public String saveEdit() {

        return "redirect:/detail?id=" + npc.getId();
    }
    
}
