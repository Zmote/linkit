package ch.zmotions.linkit.controller.base;

import ch.zmotions.linkit.commons.dto.PortalLinkDto;
import ch.zmotions.linkit.commons.types.PortalLinkType;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public abstract class AbstractPortalController extends AbstractController {

    private SortedMap<String, List<PortalLinkDto>>
    transformToAlphabeticalMap(List<PortalLinkDto> portalLinks) {
        SortedMap<String, List<PortalLinkDto>> alphabeticallyMappedPortalLinks = new TreeMap<>();
        for (PortalLinkDto portalLink : portalLinks) {
            String firstLetter = !portalLink.getName().isEmpty() ? portalLink.getName().charAt(0) + "" : "?";
            firstLetter = firstLetter.toUpperCase();
            alphabeticallyMappedPortalLinks
                    .computeIfAbsent(firstLetter, k -> new ArrayList<>())
                    .add(portalLink);
        }
        return alphabeticallyMappedPortalLinks;
    }

    protected void initLinks(Model model, List<PortalLinkDto> portalLinks) {
        model.addAttribute("portalLinks", transformToAlphabeticalMap(
                portalLinks
                        .stream()
                        .peek(this::populateTypeUrls)
                        .collect(Collectors.toList()))
        );
        model.addAttribute("availableCategories", portalLinks.stream()
                .map(PortalLinkDto::getCategory)
                .filter(category -> category != null && !category.isEmpty())
                .distinct()
                .collect(Collectors.toList()));
        model.addAttribute("availableTypes", PortalLinkType.values());
    }

    private void populateTypeUrls(PortalLinkDto portalLink) {
        if (portalLink.getType().equals(PortalLinkType.RDP)) {
            portalLink.setHref(
                    String.format("/files/download/%s.rdp", portalLink.getId()));
        }
        if (portalLink.getType().equals(PortalLinkType.WEB)) {
            String href = portalLink.getHref();
            portalLink.setHref(UrlUtils.isAbsoluteUrl(href) ? href : "//" + href);
        }
        if (portalLink.getType().equals(PortalLinkType.TEAMVIEWER)) {
            String teamViewerWebStarterUrlTeamplate =
                    "https://start.teamviewer.com/device/%s/authorization/password/mode/control";
            portalLink.setHref(String.format(teamViewerWebStarterUrlTeamplate, portalLink.getHref()));
        }
    }
}
