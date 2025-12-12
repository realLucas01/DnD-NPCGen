package dndgen.userinterface;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @author Marie
 */

@ControllerAdvice
public class NavigationControllerAdvice {

    @ModelAttribute("currentUri")
    public String addCurrentUri(HttpServletRequest request) {
        String uri = request.getRequestURI();

        // /detail and /edit should NOT appear in the navigation
        if (uri.startsWith("/detail") || uri.startsWith("/edit")) {
            return "";
        }
        return uri;
    }
}

