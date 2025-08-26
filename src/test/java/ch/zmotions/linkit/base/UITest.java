package ch.zmotions.linkit.base;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class UITest extends BaseTest {
    @Autowired
    protected TestRestTemplate restTemplate;

    @LocalServerPort
    private int basePort;

    @Value("${selenide.browser}")
    private String selenideBrowser;

    @Value("${selenide.headless}")
    private String selenideHeadless;

    protected String baseUrl = "";

    @BeforeEach
    public void setup() {
        this.baseUrl = "http://localhost:" + this.basePort;

        //Selenide configuration
        Configuration.browser = selenideBrowser;
        Configuration.headless = Boolean.parseBoolean(selenideHeadless);
    }
}
