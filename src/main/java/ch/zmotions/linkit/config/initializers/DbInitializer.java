package ch.zmotions.linkit.config.initializers;

import ch.zmotions.linkit.config.properties.AuthProperties;
import ch.zmotions.linkit.service.domain.PortalEO;
import ch.zmotions.linkit.service.domain.RoleEO;
import ch.zmotions.linkit.service.domain.UserEO;
import ch.zmotions.linkit.service.domain.repository.PortalRepository;
import ch.zmotions.linkit.service.domain.repository.RoleRepository;
import ch.zmotions.linkit.service.domain.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@ConditionalOnProperty(name = "app.db-init", havingValue = "true")
public class DbInitializer implements CommandLineRunner {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PortalRepository portalRepository;
    private final AuthProperties authProperties;

    public DbInitializer(BCryptPasswordEncoder bCryptPasswordEncoder,
                         UserRepository userRepository, RoleRepository roleRepository,
                         PortalRepository portalRepository, AuthProperties authProperties) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.portalRepository = portalRepository;
        this.authProperties = authProperties;
    }

    @Override
    @Transactional
    public void run(String... args) {
        createDefaultRolesIfNotExists();
        createDefaultAdminUserIfNotExists();
        createDefaultPortalIfNotExists();
        System.out.println(" -- Database defaults have been initialized");
    }

    private void createDefaultRolesIfNotExists() {
        Optional<RoleEO> roleUserOptional = roleRepository.findByNameIs("ROLE_USER");
        if (!roleUserOptional.isPresent()) {
            RoleEO roleUser = new RoleEO();
            roleUser.setName("ROLE_USER");
            roleRepository.save(roleUser);
        }
        Optional<RoleEO> roleAdminOptional = roleRepository.findByNameIs("ROLE_ADMIN");
        if (!roleAdminOptional.isPresent()) {
            RoleEO roleAdmin = new RoleEO();
            roleAdmin.setName("ROLE_ADMIN");
            roleRepository.save(roleAdmin);
        }
    }

    private void createDefaultAdminUserIfNotExists() {
        boolean hasAdminUser = userRepository.findAll().stream()
                .anyMatch(user -> user.getRoles().stream()
                        .anyMatch(role -> role.getName().equals("ROLE_ADMIN")));
        if (!hasAdminUser) {
            UserEO adminUser = new UserEO();
            adminUser.setRoles(roleRepository.findAll());
            adminUser.setUsername("admin");
            adminUser.setPassword(bCryptPasswordEncoder.encode(authProperties.getInitialPass()));
            adminUser.setFirstname("Administrator");
            adminUser.setLastname("Administrator");
            adminUser.setNew(true);
            adminUser.setAccountNonLocked(true);
            adminUser.setEnabled(true);
            userRepository.save(adminUser);
        }
    }

    private void createDefaultPortalIfNotExists() {
        if (portalRepository.findAll().isEmpty()) {
            PortalEO portalEO = new PortalEO();
            portalEO.setName("LinkIt Portal");
            portalEO.setCopyright("© 2019 Copyright: Zafer Dogan");
            portalEO.setCustomCss("");
            portalRepository.save(portalEO);
        }
    }
}