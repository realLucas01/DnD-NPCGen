package DnDGen.UI;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

/**
 * @author Marie
 */

    @Controller
    public class LoginController {
        /**
         * Leitende Nutzer, die "localhost:8080" aufrufen, auf die Login-Seite weiter.
         *
         * @return Umleitung auf "/login"
         */
        @GetMapping("/")
        public String redirectToLogin() {
            return "redirect:/login"; // Leitend weiter auf "/login"
        }

        /**
         * Zeigt die Login-Seite an.
         *
         * @param model Model für die Thymeleaf-View
         * @return Die Login-HTML-Seite
         */
        @GetMapping("/login")
        public String showLoginPage(Model model) {
            model.addAttribute("title", "Login");
            return "login"; // Zeigt login.html aus src/main/resources/templates
        }
    }

