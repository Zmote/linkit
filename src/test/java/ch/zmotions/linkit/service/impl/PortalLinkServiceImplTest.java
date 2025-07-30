package ch.zmotions.linkit.service.impl;

import ch.zmotions.linkit.base.IntegrationBaseTest;
import ch.zmotions.linkit.commons.types.PortalLinkType;
import ch.zmotions.linkit.service.PortalLinkService;
import ch.zmotions.linkit.service.domain.PortalLinkEO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class PortalLinkServiceImplTest extends IntegrationBaseTest {
    @Autowired
    protected PortalLinkService portalLinkService;

    @Before
    public void setup() {
        super.setup();
        addPortalLinks();
    }

    private void addPortalLinks() {
        String hrefUrl = "https://www.20min.ch";
        for (int i = 0; i < 10; i++) {
            portalLinkService.create(new PortalLinkEO(UUID.randomUUID(), "Portal " + i, "Some Portal Link", hrefUrl, "", PortalLinkType.WEB));
        }
    }

    @Test
    public void findAll() {
        List<PortalLinkEO> portalLinks = portalLinkService.findAll();
        assertThat(portalLinks).isNotNull();
        assertThat(portalLinks.size()).isEqualTo(10);
    }

    @Test
    public void create() {
    }

    @Test
    public void findById() {
    }

    @Test
    public void removeById() {
    }

    @Test
    public void save() {
    }

    @Test
    public void update() {
    }

    @Test
    public void removeAll() {
    }

    @Test
    public void getSharedLinks() {
    }

    @Test
    public void getGlobalLinks() {
    }

    @Test
    public void getPersonalLinks() {
    }

    @Test
    public void getCategories() {
    }
}