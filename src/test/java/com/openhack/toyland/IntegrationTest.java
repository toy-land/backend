package com.openhack.toyland;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class IntegrationTest extends PersistenceTest {
    public static MockMvc mockMvc;

    public static ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void beforeAll(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .build();
    }
}
