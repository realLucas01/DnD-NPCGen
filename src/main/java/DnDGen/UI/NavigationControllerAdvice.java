package DnDGen.UI;

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
        // Liefert die aktuelle URI für jedes Template
        return request.getRequestURI();
    }
}

