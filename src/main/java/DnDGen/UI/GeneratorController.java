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
     * Verarbeitet die GET-Anfrage für den Endpunkt "/generator" und bereitet
     * die erforderlichen Model-Attribute für die NPC-Generator-Seite vor.
     *
     * @param model Das Model-Objekt, mit dem Daten an die View übergeben werden
     * @return Der Name des Thymeleaf-Templates, das gerendert werden soll ("generator")
     *
     * @author Marie
     * @author ChatGPT
     */
    @GetMapping("/generator")
    String getGeneratorPage(Model model) {
        // Mögliche Werte für die Drop-down-Menüs
        ArrayList<String> raceList = dbms.onlyColumn("race");
        Collections.sort(raceList);
        model.addAttribute("races", raceList);

        ArrayList<String> classList = dbms.onlyColumn("class");
        Collections.sort(classList);
        model.addAttribute("classes", classList);

        ArrayList<String> jobList = dbms.onlyColumn("job");
        Collections.sort(jobList);
        model.addAttribute("jobs", jobList);

        ArrayList<String> abilityScoreList = dbms.onlyColumn("abilisc");
        Collections.sort(abilityScoreList);
        model.addAttribute("abilityScores", abilityScoreList);

        return "generator"; // Zeigt die Thymeleaf Seite an
    }


    @PostMapping("/generate")
    public String generateNPC(@RequestParam(required = false) String name,
                              @RequestParam(required = false) String race,
                              @RequestParam(required = false) String genClass,
                              @RequestParam(required = false) String job,
                              @RequestParam(required = false) String level,
                              @RequestParam(required = false) String abilityScore,
                              @RequestParam(required = false) String gender,
                              Model model) {

        // Mögliche Werte für die Drop-down-Menüs
        ArrayList<String> raceList = dbms.onlyColumn("race");
        Collections.sort(raceList);
        model.addAttribute("races", raceList);

        ArrayList<String> classList = dbms.onlyColumn("class");
        Collections.sort(classList);
        model.addAttribute("classes", classList);

        ArrayList<String> jobList = dbms.onlyColumn("job");
        Collections.sort(jobList);
        model.addAttribute("jobs", jobList);

        ArrayList<String> abilityScoreList = dbms.onlyColumn("abilisc");
        Collections.sort(abilityScoreList);
        model.addAttribute("abilityScores", abilityScoreList);



        // Konvertiere die Eingaben in das richtige Format mit den vorhandenen Methoden
        String finalName = zufallString(name);
        String finalRace = zufall(race);
        String finalClass = zufall(genClass);
        String finalJob = zufall(job);
        Integer finalLevel = zufallLevel(level);
        String finalAbilityScore = zufall(abilityScore);
        String finalGender = zufallString(gender);

        // Erstelle den NPC
        NpcClass npc = randomNpc.createRandomNPC(finalName, finalRace, finalClass, finalJob, finalLevel, finalAbilityScore, finalGender);

        // Daten formatieren
        Map<String, Object> formattedNpcData = formatNpcData(npc);

        // Liste der Ability Scores mit Modifiers als "+X"
        List<String> abilityScoresWithModifiers = new ArrayList<>();
        List<Integer> abilityScores = npc.getAbilityScores();
        List<Integer> abilityModifiers = npc.getAbilityModifier();

        for (int i = 0; i < abilityScores.size(); i++) {
            String modifier = (abilityModifiers.get(i) >= 0) ? "+" + abilityModifiers.get(i) : String.valueOf(abilityModifiers.get(i));
            abilityScoresWithModifiers.add(abilityScores.get(i) + " " + modifier);
        }


        // Liste der Saving Throws passend zu den Ability Scores
        List<String> savingThrowsFormatted = Arrays.asList("", "", "", "", "", "");
        List<String> allAbilities = Arrays.asList("Str", "Dex", "Con", "Int", "Wis", "Cha");

        // Sicherstellen, dass die Liste nicht null ist
        List<String> savingThrows = npc.getSavingThrows();
        if (savingThrows == null || (savingThrows.size() == 1 && savingThrows.contains("none"))) {
            savingThrows = Collections.emptyList(); // Leere Liste, falls kein gültiger Eintrag vorhanden ist
        }

        for (String savingThrow : savingThrows) {
            // Sicherstellen, dass der Saving Throw nicht null oder leer ist
            if (savingThrow == null || savingThrow.trim().isEmpty() || savingThrow.equalsIgnoreCase("none")) {
                continue; // Überspringen von leeren, ungültigen oder "none"-Werten
            }

            // Überprüfen, ob das Element das erwartete Format hat
            String[] parts = savingThrow.split(" ");
            if (parts.length != 2) {
                // Optionale Protokollierung hier
                System.err.println("Ungültiges Format für Saving Throw: " + savingThrow);
                continue; // Überspringen von ungültig formatierten Saving Throws
            }

            // Extrahieren von Ability und Modifier
            String ability = parts[0].trim();
            String value = parts[1].trim();

            // Überprüfen, ob die Ability gültig ist
            int index = allAbilities.indexOf(ability);
            if (index != -1) {
                savingThrowsFormatted.set(index, value); // Speichern des Wertes für die Ability
            } else {
                // Optionale Protokollierung für unbekannte Ability
                System.err.println("Unbekannte Ability: " + ability);
            }
        }


        // Movement Speed verarbeiten
        String walkingSpeed = "";
        String flyingSpeed = "";
        List<String> movementSpeeds = npc.getMovementSpeedList();

        for (String speed : movementSpeeds) {
            if (speed.contains("walking")) {
                walkingSpeed = speed;
            } else if (speed.contains("flying")) {
                flyingSpeed = speed;
            }
        }


        // Senses (String trennen & als Liste übergeben)
        List<String> sensesList = Arrays.asList(npc.getSenses().split(",\\s*")); // Trenne nach ", "



        // Hilfsfunktion zur Umwandlung von Strings in Listen
        Function<String, List<String>> stringToList = str ->
                (str == null || str.isEmpty()) ? Collections.emptyList() : Arrays.asList(str.split("\\s*,\\s*"));

        // Spell-Listen umwandeln und an das Model übergeben
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


        // Titel für die Beschreibung
        List<String> descriptionTitles = Arrays.asList("Eyes", "Hair Color", "Hairstyle", "Skin", "Trait", "Mannerism",
                "Bond", "Flaw", "Ideal", "Voice", "Clothing", "Attitude", "Size", "Height");

        // Beschreibungsliste zusammen mit Titeln erstellen
        List<String> formattedDescription = new ArrayList<>();
        List<String> npcDescription = npc.getDescription();
        for (int i = 0; i < npcDescription.size(); i++) {
            formattedDescription.add(descriptionTitles.get(i) + ": " + npcDescription.get(i));
        }

        System.out.println(npc.getDescription());
        System.out.println(generateDescriptionText(npc));

        // Daten an Thymeleaf weitergeben
        model.addAttribute("npc", formattedNpcData);
        // AbilityScores und Saving throws
        model.addAttribute("abilityScores", abilityScoresWithModifiers);
        model.addAttribute("savingThrows", savingThrowsFormatted);
        model.addAttribute("allAbilities", allAbilities);
        // HP, AC, Movement und Languages
        model.addAttribute("hitPoints", npc.getHealthPoints());
        model.addAttribute("armorClass", npc.getArmorClass());
        model.addAttribute("walkingSpeed", walkingSpeed);
        model.addAttribute("flyingSpeed", flyingSpeed);
        model.addAttribute("languages", npc.getLanguages());
        // Rüstung & Waffen setzen
        model.addAttribute("armor", npc.getArmor());
        model.addAttribute("weapons", npc.getWeaponList());
        // Skills & Senses
        model.addAttribute("skills", npc.getSkillList());
        model.addAttribute("senses", sensesList);
        // Beschreibungsliste ins Model
        model.addAttribute("descriptionList", formattedDescription);
        // Fließtextbeschreibung mit Geschlechtserkennung erzeugen
        model.addAttribute("descriptionText", generateDescriptionText(npc));

        // Zur generator.html zurückkehren und NPC anzeigen
        return "generator";
    }


    private String generateDescriptionText(NpcClass npc) {
        List<String> desc = npc.getDescription();

        if (desc == null || desc.isEmpty()) return "Keine Beschreibung verfügbar.";

        // Geschlechtsspezifische Pronomen setzen
        String pronoun = npc.getGender().equalsIgnoreCase("male") ? "he" : "she";
        String possessivePronoun = npc.getGender().equalsIgnoreCase("male") ? "his" : "her";

        // Null-Werte durch sinnvolle Platzhalter ersetzen
        String hairStyle = desc.get(2) != null ? desc.get(2) : "a simple style";

        // Beschreibung formatieren
        return String.format("%s has striking %s eyes. %s %s hair is %s, worn in %s. %s skin has a %s tone. %s is generally %s in behavior and has a distinctive habit: %s. %s feels strongly about \"%s\" and struggles with \"%s\". %s values \"%s\" above all. %s speaks with a %s voice. They usually wear %s and tend to come across as %s. %s is %s in size and stands at %s feet tall.",
                npc.getName(), desc.get(0), possessivePronoun, desc.get(1), desc.get(1), hairStyle,
                npc.getName(), desc.get(3), pronoun, desc.get(4), desc.get(5),
                pronoun, desc.get(6), desc.get(7),
                pronoun, desc.get(8), pronoun, desc.get(9),
                pronoun, desc.get(10), pronoun, desc.get(11),
                pronoun, desc.get(12), desc.get(13));
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


    private List<String> formatAbilityScores(List<Integer> scores, List<Integer> modifiers) {
        List<String> formattedScores = new ArrayList<>();
        String[] abilityNames = {"Str", "Dex", "Con", "Int", "Wis", "Cha"};

        for (int i = 0; i < scores.size(); i++) {
            String modifier = modifiers.get(i) >= 0 ? "+" + modifiers.get(i) : String.valueOf(modifiers.get(i));
            formattedScores.add(abilityNames[i] + ": " + scores.get(i) + " (" + modifier + ")");
        }
        return formattedScores;
    }


    private String formatDescription(NpcClass npc) {
        List<String> listNpc = npc.getDescription();
        if (listNpc.isEmpty()) return "Keine Beschreibung verfügbar.";

        String name = npc.getName();
        String pronoun = npc.getGender().equals("male") ? "his" : "her";
        String possessivePronoun = npc.getGender().equals("male") ? "he" : "she";

        return name + "'s eyes are " + listNpc.get(0) + ". " +
                capitalize(pronoun) + " hair is " + listNpc.get(1) + " in the style of " + listNpc.get(2) + ". " +
                name + "'s skin appears to be " + listNpc.get(3) + ". " +
                capitalize(possessivePronoun) + " behaves " + listNpc.get(4) + ". " +
                capitalize(listNpc.get(5)) + " is " + pronoun + " mannerism. " +
                capitalize(possessivePronoun) + " has a bond: \"" + listNpc.get(6) + "\". " +
                name + "'s flaw is \"" + listNpc.get(7) + "\", " + pronoun + " ideal is " + listNpc.get(8) + ". When " +
                possessivePronoun + " speaks, " + pronoun + " voice sounds like " + listNpc.get(9) + ".";
    }


    // Erster Buchstabe groß
    private String capitalize(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }


}
