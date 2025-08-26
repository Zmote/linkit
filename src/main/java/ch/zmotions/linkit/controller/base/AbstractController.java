package ch.zmotions.linkit.controller.base;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractController {
    protected final String PERSONAL_LINKS_IDENTIFIER = "personalLinks";
    protected final String GLOBAL_LINKS_IDENTIFIER = "portalLinks";
    protected final String USERS_IDENTIFIER = "users";

    protected boolean isAjax(HttpServletRequest request) {
        String requestedWithHeader = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(requestedWithHeader);
    }

    protected Map<String, List<String>> processBindingResult(BindingResult bindingResult) {
        Map<String, List<String>> errors = new HashMap<>();
        bindingResult.getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            if (errors.get(fieldName) != null) {
                errors.get(fieldName).add(errorMessage);
            } else {
                errors.put(fieldName, new ArrayList<String>() {{
                    add(errorMessage);
                }});
            }
        });
        return errors;
    }
}
