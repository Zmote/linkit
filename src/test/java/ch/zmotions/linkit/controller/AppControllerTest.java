package ch.zmotions.linkit.controller;

import ch.zmotions.linkit.base.IntegrationBaseTest;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class AppControllerTest extends IntegrationBaseTest {

    @Test
    public void indexLoadsWithLayoutWhenAuthenticated() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
}