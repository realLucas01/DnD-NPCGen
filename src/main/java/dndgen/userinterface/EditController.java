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


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Diese Klasse ist verantwortlich für die Bearbeitung von NPCs.
 * Sie ermöglicht Vorgänge wie die Vorbereitung von Daten für die Bearbeitungsansicht,
 * das Aktualisieren von NPC-Daten und die Verwaltung von spezifischen Attributen wie
 * Fähigkeiten, Modifikatoren, Waffen, Rüstungen und Sprachen.
 * Nutzt Services und Hilfsfunktionen für Datenbankzugriffe und logische Verarbeitung.
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
    private RandomGenerator randomGenerator = new RandomGenerator();
    private final List<String> availableSenses = Arrays.asList(
            "Darkvision",
            "Blindsight",
            "Tremorsense",
            "Truesight",
            "Passive Perception"
    );

    @Autowired
    public EditController(DBMS dbms, HelpFunctions helpFunctions) {
        this.dbms = dbms;
        this.helpFunctions = helpFunctions;
    }

    /**
     * Handhabt die Bearbeitung eines NPCs, indem die erforderlichen Daten, Attribute und UI-Elemente
     * für die Bearbeitungsansicht vorbereitet werden. Lädt die NPC-Details basierend auf einer übergebenen ID,
     * setzt die notwendigen Model-Attribute und gibt den Namen der View für das Rendering zurück.
     *
     * @param id    das eindeutige Kennzeichen des zu bearbeitenden NPCs
     * @param model das Model, dem NPC-Attribute und andere Daten für das Rendering der View hinzugefügt werden
     * @return der Name der View, die für die NPC-Bearbeitung zurückgegeben wird
     * @author Marie
     */
    @GetMapping("/edit")
    public String editNpc(@RequestParam Integer id, Model model) {
        // NPC-Objekt erstellen
        npc = new NpcClass();
        npc.historyEntryToNpc(dbms.getHistoryEntry(id));

        // Noch keine Änderungen → Kernelemente können geändert werden
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

        model.addAttribute("updateNPC", updateNPC); // hier wird Session-Wert gesetzt

        return "edit";
    }

    
    /**
     * Bearbeitet die Attribute eines NPCs durch Aktualisierung der bereitgestellten Daten, setzt die
     * notwendigen Model-Attribute und bereitet die View für das Rendering nach den Änderungen vor.
     *
     * @param name      der Name des zu bearbeitenden NPCs
     * @param race      die Rasse des NPCs, die aktualisiert werden soll
     * @param genClass  die Klasse des NPCs, die aktualisiert werden soll
     * @param alignment die Gesinnung des NPCs, die aktualisiert werden soll
     * @param model     das Model zur Speicherung der aktualisierten Attribute für die View-Darstellung
     * @return der Name der View, die für den Bearbeitungsbildschirm zurückgegeben werden soll
     */
    @PostMapping("/editFirst")
    public String editedNPC(@RequestParam String name, @RequestParam String race, @RequestParam String genClass, @RequestParam String alignment, Model model) {
        System.out.println(name + " " + race + " " + genClass + " " + alignment);
        
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
        
        updateNPC = uiHistoryEditUpdate.updateHistoryEdit(changes, npc);

       // updateNPC.setName(name);
       // updateNPC.setRace(race);
       // updateNPC.setGenClass(genClass);
       // updateNPC.setAlignment(alignment);

        // Anzeigen-Logik vorbereiten
        prepareEditAttributes(model, updateNPC);
        model.addAttribute("updateNPC", updateNPC);
        return "edit";
    }

    
    /**
     * Bereitet die Attribute für die Bearbeitung eines NPCs vor, indem verschiedene Eigenschaften,
     * wie allgemeine Attribute, zusätzliche Daten, Bewegungswerte, Sprachen,
     * Fähigkeitswerte, Inventar und Beschreibungen, in das bereitgestellte Model gesetzt werden.
     *
     * @param model das Model zur Vorbereitung der Attribute für die Anzeige der Bearbeitungsansicht
     * @param npc   das NPC-Objekt, dessen Attribute für die Bearbeitung vorbereitet werden
     * @author Marie
     */
    private void prepareEditAttributes(Model model, NpcClass npc) {
        // TODO Funktion verkleinern -> Auslagern/Refactoring

        // Gemeinsame Parameter vorbereiten
        model.addAttribute("name", npc.getName());
        model.addAttribute("selectedRace", npc.getRace());
        model.addAttribute("selectedClass", npc.getGenClass());
        model.addAttribute("selectedAlignment", npc.getAlignment());
        model.addAttribute("selectedSkills", npc.getSkillList());
        model.addAttribute("selectedSenses", npc.getSenses());
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
        formatNpcDataForStats(npc, model);
        
        // Inventar
        model.addAttribute("armors", dbms.onlyColumn("armor"));
        model.addAttribute("armor", String.join(", ", npc.getArmor())); // TODO ist String, sollte wahrscheinlich List sein, wegen mehrerer möglichen Objekten
        model.addAttribute("weapons", dbms.onlyColumn("weapon"));
        model.addAttribute("weapon", String.join(", ", npc.getWeaponList()));

        // Skills
        model.addAttribute("skills", filterSkills(npc.getSkillList()));
        model.addAttribute("availableSkills", dbms.onlyColumn("skill"));

        // Senses
        model.addAttribute("senses", getSelectedSenses());
        model.addAttribute("availableSenses", availableSenses);
        //System.out.println("Senses: " + getSelectedSenses());


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


    @PostMapping("saveNpc")
    public String saveEdit() {
        // TODO Das ganze Speichern des bearbeiteten NPC fehlt
        // TODO skills, nochmal durchlaufen, da +... Werte fehlen

        dbms.updateHistoryEntry(updateNPC, updateNPC.getId(), dbms.getHistoryEntry(updateNPC.getId()).getEntryAge());

        //return "redirect:/detail?id=" + npc.getId();
        return "redirect:/history";
    }

    
    /**
     * Bearbeitet die ausgewählten Sprachen für einen NPC durch Aktualisieren der Sprachen-Liste
     * und Vorbereitung des Models mit den aktualisierten Informationen.
     *
     * @param selectedLanguages die Liste der vom Benutzer ausgewählten Sprachen
     * @param model             das Model zur Vorbereitung der Attribute für die View-Darstellung
     * @return ein String, der den Namen der zu rendernden View angibt
     * @author Marie
     */
    @PostMapping("editLanguages")
    public String editLanguages(@RequestParam("lang") List<String> selectedLanguages, Model model) {
        editListAttributes(selectedLanguages, "lang", model);
        return "edit";

    }

    
    /**
     * Bearbeitet die ausgewählten Rüstungen für einen NPC durch Aktualisieren der Rüstungsliste
     * und Vorbereitung des Models mit den aktualisierten Informationen.
     *
     * @param selectedArmors die Liste der vom Benutzer ausgewählten Rüstungen
     * @param model          das Model zur Vorbereitung der Attribute für die View-Darstellung
     * @return ein String, der den Namen der zu rendernden View angibt
     * @author Marie
     */
    @PostMapping("editArmors")
    public String editArmors(@RequestParam("arm") List<String> selectedArmors, Model model) {
        editListAttributes(selectedArmors, "armor", model);
        return "edit";
    }


    /**
     * Bearbeitet die ausgewählten Waffen für einen NPC durch Aktualisieren der Waffen-Liste
     * und Vorbereitung des Models mit den aktualisierten Informationen.
     *
     * @param selectedWeapons die Liste der vom Benutzer ausgewählten Waffen
     * @param model           das Model zur Vorbereitung der Attribute für die View-Darstellung
     * @return ein String, der den Namen der zu rendernden View angibt
     * @author Marie
     */
    @PostMapping("editWeapons")
    public String editWeapons(@RequestParam("wea") List<String> selectedWeapons, Model model) {
        editListAttributes(selectedWeapons, "weapon", model);
        return "edit";
    }


    /**
     * Bearbeitet und aktualisiert spezifische Attribute einer Liste basierend auf dem angegebenen Typ und aktualisiert das Model entsprechend.
     *
     * @param selectedList die zu aktualisierende Liste von Elementen  
     * @param type der Typ des zu modifizierenden Attributs (z.B. "lang", "armor", "skill", "weapon")
     * @param model das Model-Objekt zur Aktualisierung der Attribute für die Anzeige
     * @throws IllegalArgumentException wenn ein unbekannter Attributstyp angegeben wird
     */
    private void editListAttributes(List<String> selectedList, String type, Model model) {
        model.addAttribute(switchValue, true);

        // Liste aktualisieren
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

        // Update der Anzeige vorbereiten
        prepareEditAttributes(model, updateNPC);

    }

    
    /**
     * Formatiert die NPC-Daten für die statistische Darstellung
     * und fügt Attribute wie Fähigkeitswerte, Modifikatoren und Rettungswürfe zum
     * Model für die Verwendung in einer Ansicht hinzu.
     *
     * @param npc   das NPC-Objekt, das die Rohdaten für Fähigkeitswerte, Modifikatoren
     *              und Rettungswürfe enthält
     * @param model das Model-Objekt zum Speichern der formatierten Daten als Attribute
     */
    private void formatNpcDataForStats(NpcClass npc, Model model) {
        List<String> allAbilities = Arrays.asList("Str", "Dex", "Con", "Int", "Wis", "Cha");

        // Die getrennten Werte und Modifikatoren direkt über die vorhandenen Funktionen abrufen
        List<Integer> abilityScores = npc.getAbilityScores();
        List<Integer> abilityModifiers = npc.getAbilityModifier();
        List<String> savingNpc = npc.getSavingThrows();


        // Liste für Saving Throws formatieren
        Map<String, Integer> savingThrows = new LinkedHashMap<>(); // LinkedHashMap: gewährleistet Reihenfolge

        // Die Namen der Schlüssel hinzufügen, mit null als Placeholder-Wert
        for (String ability : allAbilities) {
            // Überprüfen der Fähigkeiten aus der NPC-Liste
            for (String saving : savingNpc) {
                if (saving.substring(0, 3).contains(ability)) {
                    try {
                        // Substring ab Index 4 (nach dem ":") parsen
                        int parsedValue = Integer.parseInt(saving.substring(4));
                        savingThrows.put(ability, parsedValue); // Hinzufügen des Werts
                        break;
                    } catch (NumberFormatException e) {
                        // Im Fehlerfall wird kein neuer Wert hinzugefügt
                    }
                } else {
                    savingThrows.put(ability, 0); // Standardwert setzen
                }
            }
        }

        model.addAttribute("allAbilities", allAbilities); // Fähigkeitsnamen
        model.addAttribute("abilityScores", abilityScores); // Werte (Scores)
        model.addAttribute("abilityModifiers", abilityModifiers); // Modifikatoren
        model.addAttribute("savingThrows",savingThrows);
    }


    /**
     * Aktualisiert die Attributwerte eines gegebenen NPCs. Diese Methode verarbeitet die vorhandenen
     * Attributwerte und übernimmt die bereitgestellten Aktualisierungen durch Ändern des entsprechenden
     * Werts für den angegebenen Schlüssel. Die aktualisierte Liste der Attributwerte wird dann im
     * NPC-Objekt gespeichert.
     *
     * @param key       der Attributschlüssel (z.B. "0", "1", etc.), der den
     *                  Index in der Attributwerte-Liste darstellt
     * @param value     der neue Wert, der für den angegebenen Schlüssel in der
     *                  Attributwerte-Liste gesetzt werden soll
     * @param updateNPC das NPC-Objekt, dessen Attributwerte aktualisiert werden
     * @return ein ResponseEntity das das Ergebnis der Operation anzeigt
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
     * Aktualisiert die Fähigkeitsmodifikatoren eines angegebenen NPC. Diese Methode verarbeitet die vorhandenen
     * Fähigkeitsmodifikatoren und übernimmt die bereitgestellten Aktualisierungen durch Ändern des entsprechenden
     * Werts für den angegebenen Schlüssel. Die aktualisierte Liste der Fähigkeitsmodifikatoren wird dann im NPC gespeichert.
     *
     * @param key       der Attributschlüssel (z.B. "0", "1", etc.), der den zu aktualisierenden Index in der Fähigkeitsmodifikator-Liste darstellt
     * @param value     der neue Wert, der für den angegebenen Schlüssel in der Fähigkeitsmodifikator-Liste gesetzt werden soll
     * @param updateNPC das NPC-Objekt, dessen Fähigkeitsmodifikatoren aktualisiert werden
     * @return ein ResponseEntity, das das Ergebnis der Operation anzeigt
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
     * Aktualisiert die Rettungswürfe eines angegebenen NSCs. Diese Methode verarbeitet die vorhandenen
     * Rettungswürfe und übernimmt die bereitgestellten Aktualisierungen oder fügt einen neuen
     * Eintrag hinzu, falls der angegebene Schlüssel nicht vorhanden ist. Nach der Verarbeitung
     * wird die aktualisierte Liste der Rettungswürfe im NPC gespeichert.
     *
     * @param key       der Attributsschlüssel (z.B. "Str", "Dex", etc.) zum Aktualisieren oder Hinzufügen
     * @param value     der neue Wert für den angegebenen Attributsschlüssel
     * @param updateNPC das NPC-Objekt, dessen Rettungswürfe aktualisiert werden
     * @return ein ResponseEntity das das Ergebnis der Operation anzeigt
     */
    @PostMapping("/editSavingThrows")
    @ResponseBody
    public ResponseEntity<Void> editSavingThrows(@RequestParam String key, @RequestParam int value,
                                                 @ModelAttribute("updateNPC") NpcClass updateNPC) {
        List<String> throwsList = updateNPC.getSavingThrows();

        List<String> allAbilities = Arrays.asList("Str", "Dex", "Con", "Int", "Wis", "Cha");

        // Neue Liste für die aktualisierten Saving Throws
        List<String> updatedThrowsList = new ArrayList<>();

        // Aktualisiere die Liste basierend auf den vorhandenen Daten
        for (String ability : allAbilities) {
            boolean isUpdated = false;

            for (String saving : throwsList) {
                // Überprüfung, ob die Fähigkeit in der Liste enthalten ist
                if (saving.substring(0, 3).equalsIgnoreCase(ability)) {
                    try {
                        // Substring-Parsing: "+1" oder Zahlenwert ab Index 4 extrahieren
                        int parsedValue = Integer.parseInt(saving.substring(4));

                        // Wenn die Fähigkeit mit `key` übereinstimmt, aktualisiere den Wert
                        if (ability.equals(key)) {
                            updatedThrowsList.add(ability + " " + (value >= 0 ? "+" : "") + value); // Format: Con +1
                        } else {
                            updatedThrowsList.add(ability + " " + (parsedValue >= 0 ? "+" : "") + parsedValue); // Behalte aktuellen Wert
                        }
                        isUpdated = true;
                        break;
                    } catch (NumberFormatException e) {
                        System.err.println("Ungültiges Zahlenformat: " + saving); // Debuggingzwecke
                    }
                }
            }
            // Wenn die Fähigkeit nicht in throwsList enthalten war, füge sie mit 0 (Standardwert) hinzu oder aktualisiere sie
            if (!isUpdated) {
                if (ability.equals(key)) {
                    updatedThrowsList.add(ability + " " + (value >= 0 ? "+" : "") + value); // Neuer Wert
                } else {
                    updatedThrowsList.add(ability + " +0"); // Standardwert, falls nichts vorher gesetzt
                }
            }
        }

        // Überprüfen, ob `key` in der aktualisierten Liste korrekt enthalten ist
        if (!allAbilities.contains(key)) {
            updatedThrowsList.add(key + " " + (value >= 0 ? "+" : "") + value); // Füge das neue Element hinzu
        }

        // Die aktualisierte Liste zurück in den NPC setzen
        updateNPC.setSavingThrows(updatedThrowsList);

        return ResponseEntity.ok().build();

    }

    
    /**
     * Bearbeitet die ausgewählten Fähigkeiten eines NSCs durch Aktualisieren der
     * Skill Liste und Vorbereitung des Models mit den aktualisierten Informationen.
     *
     * @param selectedSkills die vom Benutzer ausgewählte Liste der Fähigkeiten
     * @param model          das Model, das für die Vorbereitung der Attribute für die View-Darstellung verwendet wird
     * @return ein String, der den Namen der zu rendernden View angibt
     * @author Marie
     */
    @PostMapping("editSkills")
    public String editSkills(@RequestParam("skill") List<String> selectedSkills, Model model) {
        editListAttributes(selectedSkills, "skill", model);
        return "edit";
    }

    
    /**
     * Filtert eine Liste von Fähigkeits-Strings, indem nachfolgende "+" mit Zahlenwerten
     * und umgebende Leerzeichen in den Strings entfernt werden.
     *
     * @param skills die zu filterende Liste der Fähigkeits-Strings
     * @return eine neue Liste der Fähigkeits-Strings nach Anwendung der Filteroperation
     */
    public static List<String> filterSkills(List<String> skills) {
        return skills.stream()
                .map(skill -> skill.replaceAll("\\s*\\+\\d+", "")) // Entfernt Leerzeichen und den "+" plus Zahl
                .collect(Collectors.toList());
    }

    public Map<String, Integer> getSelectedSenses() {
        if (npc.getSenses() == null || npc.getSenses().isEmpty()) {
            return Collections.emptyMap();
        }

        return Arrays.stream(npc.getSenses().split(","))
                .map(String::trim)
                .collect(Collectors.toMap(
                        // Entfernt die Zahl + alles dahinter, Name bleibt vollständig
                        sense -> sense.replaceAll("\\s+\\d+.*$", ""),
                        // Extrahiert nur die Zahl, ignoriert Einheit
                        sense -> {
                            Matcher matcher = Pattern.compile("\\b(\\d+)\\b").matcher(sense);
                            return matcher.find() ? Integer.parseInt(matcher.group(1)) : 0;  // Default 0 statt null
                        }
                ));
    }

    @PostMapping("editSenses")
    public String editSenses(@RequestParam("sense") List<String> selectedSenses, Model model) {
        editListAttributes(selectedSenses, "sense", model);
        return "edit";
    }




    @PostMapping("/updateField")
    @ResponseBody
    public ResponseEntity<Void> updateField(@RequestParam String value, @ModelAttribute("updateNPC") NpcClass updateNPC) {

        String[] parts = value.split(";", 2);
        value = parts[0];
        String field = parts.length > 1 ? parts[1] : "age";

        System.out.println("Update-Request erhalten für Feld: " + value + " " + field);


        // Update Logik
        switch (field) {
            case "job":
                updateNPC.setJob(value);
                break;
            case "level":
                updateNPC.setLevel(Integer.parseInt(value));
                break;
            case "deity":
                updateNPC.setDeity(value);
                break;
            case "age":
                updateNPC.setAge(Integer.parseInt(value));
                break;
            case "gender":
                if(value.equalsIgnoreCase("Random")){
                    updateNPC.setGender(randomGenerator.randGender());
                } else {
                    updateNPC.setGender(value);
                }
                break;
            default:
                throw new IllegalArgumentException("Unbekanntes Feld: " + field);
        }


        // Model aktualisieren, damit die Seite mit aktualisierten Werten neu geladen wird
        //model.addAttribute(switchValue, true);

        return ResponseEntity.ok().build();
    }

}
