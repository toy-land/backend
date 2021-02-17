package com.openhack.toyland;

import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = IntegrationTest.Initializer.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers
public abstract class IntegrationTest {

    public static MariaDBContainer mariaDBContainer = new MariaDBContainer<>("mariadb:latest")
        .withDatabaseName("toy_land_test");

    static {
        mariaDBContainer.start();
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {

            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                applicationContext,
                "spring.datasource.url=" + mariaDBContainer.getJdbcUrl(),
                "spring.datasource.username=" + mariaDBContainer.getUsername(),
                "spring.datasource.password=" + mariaDBContainer.getPassword()
            );
        }
    }
}
