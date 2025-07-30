package ch.zmotions.linkit.config;

import ch.zmotions.linkit.config.initializers.DbInitializer;
import ch.zmotions.linkit.service.domain.repository.PortalLinkRepository;
import ch.zmotions.linkit.service.domain.repository.PortalRepository;
import ch.zmotions.linkit.service.domain.repository.RoleRepository;
import ch.zmotions.linkit.service.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestDbInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PortalRepository portalRepository;

    @Autowired
    private PortalLinkRepository portalLinkRepository;

    @Autowired
    private DbInitializer dbInitializer;

    public void setup() {
        dbInitializer.run();
    }

    public void tearDown() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        portalRepository.deleteAll();
        portalLinkRepository.deleteAll();
    }
}
