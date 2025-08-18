package ch.zmotions.linkit.config.initializers;

import ch.zmotions.linkit.commons.types.PortalLinkType;
import ch.zmotions.linkit.config.properties.AppProperties;
import ch.zmotions.linkit.config.properties.AuthProperties;
import ch.zmotions.linkit.config.properties.app.DatabaseProperties;
import ch.zmotions.linkit.config.properties.app.database.PopulateProperties;
import ch.zmotions.linkit.config.properties.app.database.links.LinkProperties;
import ch.zmotions.linkit.service.domain.PortalEO;
import ch.zmotions.linkit.service.domain.PortalLinkEO;
import ch.zmotions.linkit.service.domain.RoleEO;
import ch.zmotions.linkit.service.domain.UserEO;
import ch.zmotions.linkit.service.domain.repository.PortalLinkRepository;
import ch.zmotions.linkit.service.domain.repository.PortalRepository;
import ch.zmotions.linkit.service.domain.repository.RoleRepository;
import ch.zmotions.linkit.service.domain.repository.UserRepository;
import ch.zmotions.linkit.service.util.auth.AESEncryptorDecryptor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@ConditionalOnProperty(name = "app.db.initialize", havingValue = "true")
public class DbInitializer implements CommandLineRunner {
    private final AppProperties appProperties;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AESEncryptorDecryptor aesEncryptorDecryptor;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PortalRepository portalRepository;
    private final PortalLinkRepository portalLinkRepository;
    private final AuthProperties authProperties;

    public DbInitializer(BCryptPasswordEncoder bCryptPasswordEncoder,
                         AESEncryptorDecryptor aesEncryptor,
                         UserRepository userRepository,
                         RoleRepository roleRepository,
                         PortalRepository portalRepository,
                         PortalLinkRepository portalLinkRepository,
                         AuthProperties authProperties,
                         AppProperties appProperties) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.aesEncryptorDecryptor = aesEncryptor;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.portalRepository = portalRepository;
        this.portalLinkRepository = portalLinkRepository;
        this.authProperties = authProperties;
        this.appProperties = appProperties;
    }

    @Override
    @Transactional
    public void run(String... args) {
        createDefaultRolesIfNotExists();
        createDefaultAdminUserIfNotExists();
        createDefaultPortalIfNotExists();
        DatabaseProperties appDb = this.appProperties.getDb();
        PopulateProperties populate = appDb.getPopulate();
        if(appDb.getInitialize()){
            populateWithDummyData(populate);
        }
        System.out.println(" -- Database defaults have been initialized");
    }

    private void populateWithDummyData(PopulateProperties populate) {
        System.out.println(" -- Populate with dummy data");
        LinkProperties links = populate.getLinks();
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for(int i = 0; i < links.getCount(); i++) {
            PortalLinkEO portalLinkEO = new PortalLinkEO();
            char prefix = alphabet.charAt(i % (alphabet.length()));
            portalLinkEO.setName(prefix + "ummy Link");
            portalLinkEO.setDescription("A Dummy Link");
            portalLinkEO.setType(PortalLinkType.WEB);
            portalLinkEO.setCategory(prefix + "ummy");
            portalLinkEO.setHref("https://www.20min.ch");
            portalLinkEO.setPublic(true);
            if(i % 3 == 0){
                portalLinkEO.setLogin(prefix + "dmin");
                portalLinkEO.setPassword(aesEncryptorDecryptor.encrypt(prefix + "dmin"));
            }
            portalLinkRepository.save(portalLinkEO);
        }
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