package ch.zmotions.linkit.base;

import ch.zmotions.linkit.config.TestDbInitializer;
import ch.zmotions.linkit.config.properties.AuthProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public abstract class IntegrationBaseTest extends BaseTest {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TestDbInitializer testDbInitializer;

    @Autowired
    private AuthProperties authProperties;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    protected MockMvc mvc;

    @BeforeEach
    public void setup() {
        testDbInitializer.setup();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("admin", authProperties.getInitialPass()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @AfterEach
    public void tearDown() {
        SecurityContextHolder.getContext().setAuthentication(null);
        testDbInitializer.tearDown();
    }
}
