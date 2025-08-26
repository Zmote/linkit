package ch.zmotions.linkit.controller;

import ch.zmotions.linkit.controller.base.AbstractController;
import ch.zmotions.linkit.facade.PageServiceFacade;
import ch.zmotions.linkit.service.LoginAttemptService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/configurations")
public class ConfigurationController extends AbstractController {

    private final PageServiceFacade pageServiceFacade;
    private final LoginAttemptService loginAttemptService;

    public ConfigurationController(PageServiceFacade pageServiceFacade, LoginAttemptService loginAttemptService) {
        this.pageServiceFacade = pageServiceFacade;
        this.loginAttemptService = loginAttemptService;
    }


    @GetMapping
    public String all(Model model, HttpServletRequest request) {
        initPaged(model, GLOBAL_LINKS_IDENTIFIER, pageServiceFacade.getPagedGlobalLinks());
        initPaged(model, PERSONAL_LINKS_IDENTIFIER, pageServiceFacade.getPagedPersonalLinks());
        initPaged(model, USERS_IDENTIFIER, pageServiceFacade.getPagedUsers());
        model.addAttribute("blockedIps", String.join("\r\n", loginAttemptService.getBlockedList()));
        return isAjax(request) ? "configurations/index :: body" : "configurations/index";
    }

    @GetMapping("/personal-links")
    public String personalLinks(Model model,
                                @RequestParam(required = false) Integer pageNo,
                                @RequestParam(required = false) Integer size,
                                @RequestParam(required = false) String sort) {
        if (pageNo != null && size != null) {
            initPaged(model, PERSONAL_LINKS_IDENTIFIER, pageServiceFacade.getPagedPersonalLinks(pageNo, size, sort));
        } else {
            initPaged(model, PERSONAL_LINKS_IDENTIFIER, pageServiceFacade.getPagedPersonalLinks());
        }
        return "configurations/portal-links/personal-links/list";
    }

    @GetMapping("/global-links")
    public String portalLinks(Model model,
                              @RequestParam(required = false) Integer pageNo,
                              @RequestParam(required = false) Integer size,
                              @RequestParam(required = false) String sort) {
        if (pageNo != null && size != null && sort != null) {
            initPaged(model, GLOBAL_LINKS_IDENTIFIER, pageServiceFacade.getPagedGlobalLinks(pageNo, size, sort));
        } else {
            initPaged(model, GLOBAL_LINKS_IDENTIFIER, pageServiceFacade.getPagedGlobalLinks());
        }
        return "configurations/portal-links/global-links/list";
    }

    @GetMapping("/users")
    public String users(Model model,
                        @RequestParam(required = false) Integer pageNo,
                        @RequestParam(required = false) Integer size,
                        @RequestParam(required = false) String sort) {
        if (pageNo != null && size != null && sort != null) {
            initPaged(model, "users", pageServiceFacade.getPagedUsers(pageNo, size, sort));
        } else {
            initPaged(model, "users", pageServiceFacade.getPagedUsers());
        }
        return "configurations/users/userlist";
    }

    private void initPaged(Model model, String contentIdentifier, Page currentPage) {
        String[] sortProps = currentPage.getSort().toString().split(":");
        if (!sortProps[0].trim().equals("UNSORTED")) {
            model.addAttribute("currentSort", sortProps[0].trim());
            model.addAttribute("currentSortDir", sortProps[1].trim());
        }
        model.addAttribute(contentIdentifier, currentPage);
    }
}
