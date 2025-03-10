package DnDGen.UI;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Marie
 */
@Controller
public class PageController {

    @GetMapping("/history")
    public String historyPage() {
        return "history";
    }
}

