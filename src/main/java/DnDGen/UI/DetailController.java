package DnDGen.UI;

import DnDGen.DB.DBMS;
import DnDGen.TSWP1.NpcClass;
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
                ? new ArrayList<>(List.of(sensesValue.split(",\\s*")))
                : new ArrayList<>();  // Leere Liste verwenden, falls null oder leer


        //System.out.println( "Senses: " + sensesValue + " -> " + senses);

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

        // Print-Zeilen
        /*
        System.out.println("Cantrips: " + npc.getSpellCantrips() + " -> " + splitString(npc.getSpellCantrips()));
        System.out.println("First Level Spells: " + npc.getFirstLevelSpell() + " -> " + splitString(npc.getFirstLevelSpell()));
        System.out.println("Second Level Spells: " + npc.getSecondLevelSpell() + " -> " + splitString(npc.getSecondLevelSpell()));
        System.out.println("Third Level Spells: " + npc.getThirdLevelSpell() + " -> " + splitString(npc.getThirdLevelSpell()));
        System.out.println("Fourth Level Spells: " + npc.getFourthLevelSpell() + " -> " + splitString(npc.getFourthLevelSpell()));
        System.out.println("Fifth Level Spells: " + npc.getFifthLevelSpell() + " -> " + splitString(npc.getFifthLevelSpell()));
        System.out.println("Sixth Level Spells: " + npc.getSixthLevelSpell() + " -> " + splitString(npc.getSixthLevelSpell()));
        System.out.println("Seventh Level Spells: " + npc.getSeventhLevelSpell() + " -> " + splitString(npc.getSeventhLevelSpell()));
        System.out.println("Eighth Level Spells: " + npc.getEightLevelSpell() + " -> " + splitString(npc.getEightLevelSpell()));
        System.out.println("Ninth Level Spells: " + npc.getNinthLevelSpell() + " -> " + splitString(npc.getNinthLevelSpell()));
        */
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
        model.addAttribute("descriptionText", generateDescriptionText(npc));
    }

    /**
     * Generiert einen formatierten Beschreibungstext für den angegebenen NPC basierend auf dessen Attributen und Beschreibungen.
     *
     * @param npc Das NPC-Objekt mit Attributen und Beschreibungsdetails, die für die Konstruktion des Beschreibungstextes verwendet werden.
     * @return Ein formatierter String, der den NPC beschreibt. Wenn keine Beschreibung verfügbar ist, wird eine Standardnachricht zurückgegeben.
     */
    public String generateDescriptionText(NpcClass npc) {
        List<String> desc = npc.getDescription();

        if (desc == null || desc.isEmpty()) return "Keine Beschreibung verfügbar.";

        // Geschlechtsspezifische Pronomen setzen
        String pronoun = npc.getGender().equalsIgnoreCase("male") ? "he" : "she";
        String possessivePronoun = npc.getGender().equalsIgnoreCase("male") ? "his" : "her";

        // Null-Werte durch sinnvolle Platzhalter ersetzen
        String hairStyle = desc.get(2) != null ? desc.get(2) : "a simple style";

        // Beschreibung formatieren
        return String.format("%s has striking %s eyes. %s hair is %s, worn in %s. %s skin has a %s tone. %s is generally %s in behavior and has a distinctive habit: %s. %s feels strongly about \"%s\" and struggles with \"%s\". %s values \"%s\" above all. %s speaks with a %s voice. They usually wear %s and tend to come across as %s. %s is %s in size and stands at %s feet tall.",
                npc.getName(), desc.get(0), possessivePronoun, desc.get(1), hairStyle,
                npc.getName(), desc.get(3), pronoun, desc.get(4), desc.get(5),
                pronoun, desc.get(6), desc.get(7),
                pronoun, desc.get(8), pronoun, desc.get(9),
                desc.get(10), pronoun, desc.get(11), desc.get(12), desc.get(13));
    }


    private List<String> splitString(String input) {
        return (input == null || input.isEmpty()) ? Collections.emptyList() : List.of(input.split("\\s*,\\s*"));
    }
}
