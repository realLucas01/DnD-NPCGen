package DnDGen.UI;

import DnDGen.DB.DBMS;
import DnDGen.TSWP1.NpcClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Marie
 */
@Component
public class HelpFunctions {
    private DBMS dbms = new DBMS();

    @Autowired
    public HelpFunctions(DBMS dbms) {
        this.dbms = dbms;
    }

    /**
     * Ruft eine Spalte von Strings aus der Datenbank ab, sortiert sie in aufsteigender Reihenfolge und gibt die sortierte Liste zurück.
     *
     * @param columnName der Name der Spalte, die abgerufen und sortiert werden soll
     * @return eine Liste von Strings, die die sortierten Werte der angegebenen Spalte enthält
     */
    public List<String> getSortedColumn(String columnName) {
        ArrayList<String> list = dbms.onlyColumn(columnName);
        Collections.sort(list);
        return list;
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
     * Formatiert die Rettungswürfe einer gegebenen NPC-Klasse in eine Liste, wobei die Werte
     * mit spezifischen Attributen (Stärke, Geschicklichkeit, Konstitution, Intelligenz, Weisheit, Charisma) ausgerichtet werden.
     * Wenn der NPC keine Rettungswürfe oder ungültige Eingaben hat, wird eine Standardliste mit leeren Strings zurückgegeben.
     *
     * @param npc Das NPC-Objekt, das Informationen zu Rettungswürfen enthält.
     * @return Eine Liste der Rettungswürfe, formatiert nach Attributen, wobei jeder Wert den Attributen
     * "Str", "Dex", "Con", "Int", "Wis", "Cha" entspricht. Wenn keine gültigen Rettungswürfe
     * gefunden werden, enthält die Liste leere Strings.
     */
    public List<String> formatSavingThrows(NpcClass npc) {
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


    /**
     * Kombiniert die Attributswerte und ihre entsprechenden Modifikatoren eines gegebenen NPCs in eine formatierte String-Liste.
     * Jeder Eintrag in der Liste enthält einen Attributswert gefolgt von seinem Modifikator (z. B. "14 +2").
     *
     * @param npc Das NPC-Objekt, das die Attributswerte und Modifikatoren zur Formatierung enthält.
     * @return Eine Liste von Strings, wobei jeder String einen Attributswert und seinen Modifikator kombiniert.
     */
    public List<String> getAbilityScoresWithModifiers(NpcClass npc) {
        List<String> result = new ArrayList<>();
        List<Integer> scores = npc.getAbilityScores();
        List<Integer> mods = npc.getAbilityModifier();

        for (int i = 0; i < scores.size(); i++) {
            String mod = (mods.get(i) >= 0) ? "+" + mods.get(i) : String.valueOf(mods.get(i));
            result.add(scores.get(i) + " " + mod);
        }
        return result;
    }



}
