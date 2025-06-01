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
    private final String zufall = "Zufall";



    @Autowired // Spring injiziert automatisch eine Instanz von DBMS
    public IndexController(RandomNpc randomNpc, HelpFunctions helpFunctions) {
        this.randomNpc = randomNpc;
        this.helpFunctions = helpFunctions;
    }

    /**
     * Behandelt GET-Anfragen an den "/index"-Endpunkt und bereitet das Model
     * mit den notwendigen Attributen für die Ansicht vor.
     *
     * @param model Das Model-Objekt, das verwendet wird, um Attribute an die Ansicht zu übergeben
     * @return der Name der Thymeleaf-Vorlage, um den Index (Generator)-Seite anzuzeigen
     */
    @GetMapping(value = {"/", "/index"})
    String getGeneratorPage(Model model, HttpSession session) {
        // Prüfe, ob ein NPC in der Session existiert
        NpcClass existingNpc = (NpcClass) session.getAttribute("currentNpc");
        DropdownSelection existingSelection = (DropdownSelection) session.getAttribute("currentSelection");

        if (existingNpc != null) {
            // Wenn ein NPC existiert, verwende die gespeicherten Daten
            formatNpcDataForModel(existingNpc, model);
            populateDropdowns(model, existingSelection);
            model.addAttribute("npc", existingNpc);
        } else {
            // Wenn kein NPC existiert, initialisiere nur die Dropdown-Menüs
            populateDropdowns(model, new DropdownSelection("", zufall, zufall, zufall, zufall, zufall, zufall));
        }
        return "index";

    }



    /**
     * Generiert einen NPC (Nicht-Spieler-Charakter) basierend auf den angegebenen Parametern und fügt diesen dem Model hinzu.
     * Bereitet Dropdown-Menüdaten vor, erstellt den NPC, formatiert die NPC-Daten und gibt das entsprechende
     * Thymeleaf-Template zurück.
     *
     * @param name     der Name des NPCs; kann null oder leer sein
     * @param race     die Rasse des NPCs; kann null oder leer sein
     * @param genClass die allgemeine Klasse oder der Archetyp des NPCs; kann null oder leer sein
     * @param job      die spezifische Aufgabe oder Rolle des NPCs; kann null oder leer sein
     * @param level    die Stufe oder Machtstufe des NPCs; kann null oder leer sein
     * @param abilisc  die Fähigkeitspunkte oder Beschreibung des NPCs; kann null oder leer sein
     * @param gender   das Geschlecht des NPCs; kann null oder leer sein
     * @param model    das Model, dem Dropdown-Menüdaten und NPC-Daten hinzugefügt werden
     * @return der Name des Thymeleaf-Templates, das die NPC-Index(Generator)-Seite rendert
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

        // Speichere NPC und Selection in der Session
        session.setAttribute("currentNpc", npc);
        session.setAttribute("currentSelection", selection);

        return "redirect:/index";


    }




    /**
     * Füllt die Dropdown-Optionen im angegebenen Model mit sortierten Daten für verschiedene Kategorien.
     *
     * @param model Das Model-Objekt, zu dem Dropdown-Attribute hinzugefügt werden
     */
    private void populateDropdowns(Model model, DropdownSelection selection) {
        // Dropdown-Daten hinzufügen
        model.addAttribute("races", helpFunctions.getSortedColumn("race"));
        model.addAttribute("classes", helpFunctions.getSortedColumn("class"));
        model.addAttribute("jobs", helpFunctions.getSortedColumn("job"));
        model.addAttribute("abilisc", helpFunctions.getSortedColumn("abilisc"));

        // vorher gewählte Daten übergeben
        model.addAttribute("name", selection.getName());
        model.addAttribute("selectedRace", selection.getRace());
        model.addAttribute("selectedClass", selection.getGenClass());
        model.addAttribute("selectedJob", selection.getJob());
        model.addAttribute("selectedLevel", selection.getLevel());
        model.addAttribute("selectedAbilisc", selection.getAbilisc());
        model.addAttribute("selectedGender", selection.getGender());

    }

    
    /**
     * Erstellt einen neuen NPC (Non-Player Character) mit zufälligen oder spezifischen Attributen
     * basierend auf den bereitgestellten Eingabeparametern.
     *
     * @param name         Der Name des NPCs; wenn "zufall" angegeben wird, wird ein zufälliger Name generiert.
     * @param race         Die Rasse des NPCs; wenn "zufall" angegeben wird, wird eine zufällige Rasse ausgewählt.
     * @param genClass     Die generelle Klasse (z. B. Krieger, Magier) des NPCs; wenn "zufall" angegeben wird, wird eine zufällige Klasse ausgewählt.
     * @param job          Der spezifische Beruf oder die Aufgabe des NPCs; wenn "zufall" angegeben wird, wird ein zufälliger Beruf zugewiesen.
     * @param level        Die Stufe des NPCs; wenn "zufall" angegeben wird, wird eine zufällige Stufe als Ganzzahl generiert.
     * @param abilityScore Die Attributswerte des NPCs (z. B. Stärke, Intelligenz); wenn "zufall" angegeben wird, wird ein zufälliger Wert generiert.
     * @param gender       Das Geschlecht des NPCs; wenn "zufall" angegeben wird, wird ein zufälliges Geschlecht ausgewählt.
     * @return Eine Instanz der Klasse NpcClass, die den generierten NPC mit spezifischen oder zufälligen Attributen darstellt.
     */
    private NpcClass createNPC(String name, String race, String genClass, String job, String level, String abilityScore, String gender) {
        String finalName = zufallString(name);
        String finalRace = zufall(race);
        String finalClass = zufall(genClass);
        String finalJob = zufall(job);
        Integer finalLevel = zufallLevel(level);
        String finalAbilityScore = zufall(abilityScore);
        String finalGender = zufallString(gender);

        return randomNpc.createRandomNPC(finalName, finalRace, finalClass, finalJob, finalLevel, finalAbilityScore, finalGender);
    }

    
    /**
     * Formatiert NPC-Daten und fügt dem bereitgestellten Modell verschiedene Attribute zur Anzeige in der Benutzeroberfläche hinzu.
     *
     * @param npc   Das NPC-Objekt, das Daten enthält, die formatiert und dem Modell hinzugefügt werden sollen.
     * @param model Das Model-Objekt, zu dem die NPC-Daten und Attribute als Schlüssel-Wert-Paare hinzugefügt werden.
     */
    private void formatNpcDataForModel(NpcClass npc, Model model) {
        Map<String, Object> formattedNpcData = formatNpcData(npc); // Main-NPC-Daten
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
     * Fügt die Bewegungsraten und Sinne eines NPCs in das bereitgestellte Model ein.
     * Die Methode extrahiert Geh- und Fluggeschwindigkeiten aus dem NPC und fügt diese zusammen
     * mit einer Liste der Sinne in das Model ein.
     *
     * @param npc   Das NPC-Objekt, aus dem die Bewegungsraten und Sinne entnommen werden.
     * @param model Das Model-Objekt, dem die Bewegungsraten und Sinne als Attribute hinzugefügt werden.
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
     * Fügt die Zauber eines NPCs auf verschiedenen Stufen als Attribute zum bereitgestellten Model hinzu.
     * Wandelt kommagetrennte Zauberstrings in Listen um und weist sie den entsprechenden
     * Zauberstufen-Attributen im Model zur Anzeige in der Ansicht zu.
     *
     * @param npc   Das NPC-Objekt, das die Zauberdetails für verschiedene Stufen enthält.
     * @param model Das Model-Objekt, dem die Zauberattribute hinzugefügt werden.
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


    // Inventory


    
    /**
     * Fügt eine formatierte Beschreibung eines NPCs zum bereitgestellten Model hinzu.
     * Die Methode formatiert einzelne beschreibende Attribute des NPCs, wie Augen, Haare und Eigenschaften,
     * in eine strukturierte Liste und erzeugt außerdem einen zusammenhängenden Beschreibungstext zur Anzeige.
     *
     * @param npc   Das NPC-Objekt, das die Beschreibungsdetails enthält und dem Model hinzugefügt werden soll.
     * @param model Das Model-Objekt, dem die formatierte Beschreibungsliste und der Text hinzugefügt werden.
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
     * Überprüft, ob ein String "zufall" ist. Falls ja, wird `null` zurückgegeben.
     * @param input Der Eingabestring
     * @return `null`, falls "zufall", sonst der Originalstring
     */
    public String zufall(String input) {
        return (zufall.equalsIgnoreCase(input) || input.isEmpty()) ? null : input;
    }


    /**
     * Überprüft eine Level-Eingabe. Falls "zufall" ausgewählt wurde, gibt sie `null` zurück.
     * @param input Die Level-Eingabe als String
     * @return `null` falls "zufall", sonst Integer-Wert
     */
    public Integer zufallLevel(String input) {
        return (zufall.equalsIgnoreCase(input) || input.isEmpty()) ? null : Integer.parseInt(input);
    }


    /**
     * Überprüft, ob ein String leer ist. Falls ja, wird `null` zurückgegeben.
     * @param string Der Eingabestring
     * @return `null` falls leer oder "zufall", sonst der String selbst
     */
    public String zufallString(String string) {
        return (string == null || string.equalsIgnoreCase(zufall) || string.isEmpty()) ? null : string;
    }


    /**
     * Formatiert die bereitgestellten NPC-Daten in eine Kartenstruktur zur weiteren Verarbeitung oder Anzeige.
     *
     * @param npc Das NPC-Objekt, das zu formatierende Daten enthält.
     * @return Eine Map, bei der die Schlüssel Eigenschaftsnamen (z. B. "name", "level") und die Werte die entsprechenden NPC-Daten sind.
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
