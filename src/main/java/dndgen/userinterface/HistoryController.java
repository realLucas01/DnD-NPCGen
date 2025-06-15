package dndgen.userinterface;

import dndgen.database.DBMS;
import dndgen.tswp1.NpcClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Controller für die History-Seite, die die letzten generierten NPCs anzeigt.
 * Beim Klicken auf einen NPC wird man zur Detailansicht weitergeleitet.
 *
 * @author Marie
 */
@Controller
public class HistoryController {

    private final DBMS dbms;

    @Autowired
    public HistoryController(DBMS dbms) {
        this.dbms = dbms;
    }

    /**
     * Holt die letzten 10 generierten NPCs aus der Datenbank und stellt sie als Kacheln dar.
     * @param model Spring Model für Thymeleaf
     * @return Thymeleaf Template "history"
     */
    @GetMapping("/history")
    public String showHistoryPage(Model model) {

        // Letzte 10 NPCs aus der History abrufen
        List<NpcClass> npcHistory = dbms.getHistory().stream()
                .map(npcData -> {
                    NpcClass npc = new NpcClass();
                    npc.historyEntryToNpc(npcData);
                    return npc;
                }).toList();


        model.addAttribute("npcHistory", npcHistory);
        return "history"; // Verweist auf die Thymeleaf-HTML-Seite
    }
}
