package ch.zmotions.linkit.ui;

import ch.zmotions.linkit.base.UITest;
import com.codeborne.selenide.SelenideElement;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;

public class IndexPageTest extends UITest {
    @Test
    public void nonAuthenticatedUserLandsAtLogin() {
        String path = this.baseUrl + "/";
        open(path);
        SelenideElement loginBtn = $(By.tagName("button"));
        assertThat(loginBtn.getText()).isEqualTo("Log in");
    }
}
