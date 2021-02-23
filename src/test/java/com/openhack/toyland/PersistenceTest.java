package com.openhack.toyland;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("test")
@Sql(scripts = {"/db/init-test.sql",
    "/db/insert-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(encoding = "utf-8"))
@Sql(scripts = {
    "/db/truncate-test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(encoding = "utf-8"))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = PersistenceTest.Initializer.class)
@Testcontainers
public abstract class PersistenceTest {

    public static MariaDBContainer mariaDBContainer = new MariaDBContainer<>("mariadb:latest")
        .withDatabaseName("toy_land_test");

    private static Logger log = LoggerFactory.getLogger(PersistenceTest.class);

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

    @BeforeEach
    public void setUp() {
        Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(log);
        mariaDBContainer.followOutput(logConsumer);
    }
}
