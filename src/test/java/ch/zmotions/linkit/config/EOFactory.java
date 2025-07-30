package ch.zmotions.linkit.config;

import ch.zmotions.linkit.commons.types.PortalLinkType;
import ch.zmotions.linkit.service.domain.PortalEO;
import ch.zmotions.linkit.service.domain.PortalLinkEO;
import ch.zmotions.linkit.service.domain.RoleEO;
import ch.zmotions.linkit.service.domain.UserEO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EOFactory {
    public static final UUID knownPortalLinkId = UUID.randomUUID();
    public static final UUID knownUserId = UUID.randomUUID();
    public static final UUID knownPortalId = UUID.randomUUID();
    public static final UUID knownRoleUserId = UUID.randomUUID();
    public static final UUID knownRoleAdminId = UUID.randomUUID();


    public static UserEO createDummyUser() {
        UserEO user = new UserEO();
        user.setId(knownUserId);
        user.setUsername("Test");
        user.setFirstname("Test");
        user.setLastname("Test");
        user.setPassword("testpassword");
        user.setNew(true);
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        user.setLoginAttempts(0);
        user.setRoles(createDummyRoles());
        return user;
    }

    public static UserEO createDummyRandomUser() {
        UserEO user = createDummyUser();
        user.setId(UUID.randomUUID());
        user.setUsername("Test" + user.getId());
        return user;
    }

    public static RoleEO createDummyRoleUser() {
        RoleEO role = new RoleEO();
        role.setId(knownRoleUserId);
        role.setName("ROLE_USER");
        return role;
    }

    public static RoleEO createDummyRoleAdmin() {
        RoleEO role = new RoleEO();
        role.setId(knownRoleAdminId);
        role.setName("ROLE_ADMIN");
        return role;
    }

    public static List<RoleEO> createDummyRoles() {
        return new ArrayList<RoleEO>() {{
            add(createDummyRoleUser());
            add(createDummyRoleAdmin());
        }};
    }

    public static PortalEO createDummyPortal() {
        PortalEO portal = new PortalEO();
        portal.setId(knownPortalId);
        portal.setMaxLoginAttempts(3);
        portal.setName("Portal");
        portal.setCustomCss("CustomCss");
        portal.setCopyright("Copyright");
        return portal;
    }

    public static PortalLinkEO createDummyPortalLink() {
        PortalLinkEO portalLink = new PortalLinkEO();
        portalLink.setId(knownPortalLinkId);
        portalLink.setName("Name");
        portalLink.setDescription("Description");
        portalLink.setHref("Href");
        portalLink.setMedia("Media");
        portalLink.setCategory("Category");
        portalLink.setType(PortalLinkType.WEB);
        portalLink.setLogin("admin");
        portalLink.setPassword("testpassword");
        portalLink.setOwner(null);
        return portalLink;
    }

    public static PortalLinkEO createDummyRandomPortalLink() {
        PortalLinkEO portalLink = createDummyPortalLink();
        portalLink.setId(UUID.randomUUID());
        return portalLink;
    }
}
