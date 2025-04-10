package DnDGen.UI;

import DnDGen.DB.DBMS;
import DnDGen.DB.HistoryModel;
import DnDGen.TSWP1.NpcClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author Marie
 */
@Controller
public class EditController {
    private final DBMS dbms;

    @Autowired
    public EditController(DBMS dbms) {
        this.dbms = dbms;
    }

    @GetMapping("/edit")
    public String editNpc(@RequestParam Integer id, Model model) {

        // NPC aus der Datenbank abrufen
       // HistoryModel historyEntry = dbms.getHistoryEntry(id);

        // NPC-Objekt erstellen
        NpcClass npc = new NpcClass();
        npc.historyEntryToNpc(dbms.getHistoryEntry(id));
        //npc.historyEntryToNpc(historyEntry);

        model.addAttribute("npc", npc);
        return "edit";
    }
}
