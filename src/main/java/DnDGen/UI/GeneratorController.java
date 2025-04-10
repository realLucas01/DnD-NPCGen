package DnDGen.UI;

import DnDGen.DB.DBMS;
import DnDGen.TSWP1.NpcClass;
import DnDGen.TSWP1.RandomNpc;
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
public class GeneratorController {
    private final DBMS dbms;
    private final RandomNpc randomNpc;

    @Autowired // Spring injiziert automatisch eine Instanz von DBMS
    public GeneratorController(DBMS dbms, RandomNpc randomNpc) {
        this.dbms = dbms;
        this.randomNpc = randomNpc;
    }

    /**
     * Behandelt GET-Anfragen an den "/generator"-Endpunkt und bereitet das Model
     * mit den notwendigen Attributen für die Ansicht vor.
     *
     * @param model Das Model-Objekt, das verwendet wird, um Attribute an die Ansicht zu übergeben
     * @return der Name der Thymeleaf-Vorlage, um die Generator-Seite anzuzeigen
     */
    @GetMapping("/generator")
    String getGeneratorPage(Model model) {

        // Werte für die Dropdown-Menüs vorbereiten
        populateDropdowns(model, "", "Zufall", "Zufall", "Zufall", "Zufall", "Zufall", "Zufall");

        return "generator"; // Zeigt die Thymeleaf-Seite an
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
     * @return der Name des Thymeleaf-Templates, das die NPC-Generator-Seite rendert
     */
    @PostMapping("/generate")
    public String generateNPC(@RequestParam(required = false) String name,
                              @RequestParam(required = false) String race,
                              @RequestParam(required = false) String genClass,
                              @RequestParam(required = false) String job,
                              @RequestParam(required = false) String level,
                              @RequestParam(required = false) String abilisc,
                              @RequestParam(required = false) String gender,
                              Model model) {
        populateDropdowns(model, name, race, genClass, job, level, abilisc, gender); // Menü-Daten vorbereiten

        NpcClass npc = createNPC(name, race, genClass, job, level, abilisc, gender); // NPC erstellen

        formatNpcDataForModel(npc, model); // NPC-Daten formatieren und ins Model einfügen

        return "generator"; // Thymeleaf-Template zurückgeben
    }


    /**
     * Füllt die Dropdown-Optionen im angegebenen Model mit sortierten Daten für verschiedene Kategorien.
     *
     * @param model Das Model-Objekt, zu dem Dropdown-Attribute hinzugefügt werden
     */
    private void populateDropdowns(Model model, String name, String race, String genClass, String job, String level, String abiliscor, String gender) {
        // Dropdown erstellen
        model.addAttribute("races", getSortedColumn("race"));
        model.addAttribute("classes", getSortedColumn("class"));
        model.addAttribute("jobs", getSortedColumn("job"));
        model.addAttribute("abilisc", getSortedColumn("abilisc"));

        // vorher gewählte Daten übergeben
        model.addAttribute("name", name);
        model.addAttribute("selectedRace", race);
        model.addAttribute("selectedClass", genClass);
        model.addAttribute("selectedJob", job);
        model.addAttribute("selectedLevel", level);
        model.addAttribute("selectedAbilisc", abiliscor);
        model.addAttribute("selectedGender", gender);
    }

    
    /**
     * Ruft eine Spalte von Strings aus der Datenbank ab, sortiert sie in aufsteigender Reihenfolge und gibt die sortierte Liste zurück.
     *
     * @param columnName der Name der Spalte, die abgerufen und sortiert werden soll
     * @return eine Liste von Strings, die die sortierten Werte der angegebenen Spalte enthält
     */
    private List<String> getSortedColumn(String columnName) {
        ArrayList<String> list = dbms.onlyColumn(columnName);
        Collections.sort(list);
        return list;
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

        model.addAttribute("abilityScores", getAbilityScoresWithModifiers(npc));
        model.addAttribute("savingThrows", formatSavingThrows(npc));
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
     * Kombiniert die Attributswerte und ihre entsprechenden Modifikatoren eines gegebenen NPCs in eine formatierte String-Liste.
     * Jeder Eintrag in der Liste enthält einen Attributswert gefolgt von seinem Modifikator (z. B. "14 +2").
     *
     * @param npc Das NPC-Objekt, das die Attributswerte und Modifikatoren zur Formatierung enthält.
     * @return Eine Liste von Strings, wobei jeder String einen Attributswert und seinen Modifikator kombiniert.
     */
    private List<String> getAbilityScoresWithModifiers(NpcClass npc) {
        List<String> abilityScoresWithModifiers = new ArrayList<>();
        List<Integer> abilityScores = npc.getAbilityScores();
        List<Integer> abilityModifiers = npc.getAbilityModifier();

        for (int i = 0; i < abilityScores.size(); i++) {
            String modifier = (abilityModifiers.get(i) >= 0) ? "+" + abilityModifiers.get(i) : String.valueOf(abilityModifiers.get(i));
            abilityScoresWithModifiers.add(abilityScores.get(i) + " " + modifier);
        }
        return abilityScoresWithModifiers;
    }

    
    /**
     * Formatiert die Rettungswürfe einer gegebenen NPC-Klasse in eine Liste, wobei die Werte
     * mit spezifischen Attributen (Stärke, Geschicklichkeit, Konstitution, Intelligenz, Weisheit, Charisma) ausgerichtet werden.
     * Wenn der NPC keine Rettungswürfe oder ungültige Eingaben hat, wird eine Standardliste mit leeren Strings zurückgegeben.
     *
     * @param npc Das NPC-Objekt, das Informationen zu Rettungswürfen enthält.
     * @return Eine Liste der Rettungswürfe, formatiert nach Attributen, wobei jeder Wert den Attributen
     * "Str", "Dex", "Con", "Int", "Wis", "Cha" entspricht. Wenn keine gültigen Rettungswürfe
     * gefunden werden, enthält die Liste leere Strings.
     */
    private List<String> formatSavingThrows(NpcClass npc) {
        List<String> savingThrowsFormatted = Arrays.asList("", "", "", "", "", "");
        List<String> allAbilities = Arrays.asList("Str", "Dex", "Con", "Int", "Wis", "Cha");
        List<String> savingThrows = npc.getSavingThrows();

        if (savingThrows == null || (savingThrows.size() == 1 && savingThrows.contains("none"))) {
            return savingThrowsFormatted; // Leere Liste bei ungültigen Saving Throws
        }

        for (String savingThrow : savingThrows) {
            if (savingThrow == null || savingThrow.trim().isEmpty() || savingThrow.equalsIgnoreCase("none")) {
                continue;
            }
            String[] parts = savingThrow.split(" ");
            if (parts.length == 2) {
                String ability = parts[0].trim();
                String value = parts[1].trim();
                int index = allAbilities.indexOf(ability);
                if (index != -1) {
                    savingThrowsFormatted.set(index, value);
                }
            }
        }
        return savingThrowsFormatted;
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
            formattedDescription.add(descriptionTitles.get(i) + ": " + npcDescription.get(i));
        }

        model.addAttribute("descriptionList", formattedDescription);
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


    /**
     * Überprüft, ob ein String "zufall" ist. Falls ja, wird `null` zurückgegeben.
     * @param input Der Eingabestring
     * @return `null`, falls "zufall", sonst der Originalstring
     */
    public String zufall(String input) {
        return ("Zufall".equalsIgnoreCase(input) || input.isEmpty()) ? null : input;
    }


    /**
     * Überprüft eine Level-Eingabe. Falls "zufall" ausgewählt wurde, gibt sie `null` zurück.
     * @param input Die Level-Eingabe als String
     * @return `null` falls "zufall", sonst Integer-Wert
     */
    public Integer zufallLevel(String input) {
        return ("Zufall".equalsIgnoreCase(input) || input.isEmpty()) ? null : Integer.parseInt(input);
    }


    /**
     * Überprüft, ob ein String leer ist. Falls ja, wird `null` zurückgegeben.
     * @param string Der Eingabestring
     * @return `null` falls leer oder "zufall", sonst der String selbst
     */
    public String zufallString(String string) {
        return (string == null || string.equalsIgnoreCase("Zufall") || string.isEmpty()) ? null : string;
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
