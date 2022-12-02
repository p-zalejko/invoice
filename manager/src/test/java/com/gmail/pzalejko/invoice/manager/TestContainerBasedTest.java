package com.gmail.pzalejko.invoice.manager;

import com.gmail.pzalejko.invoice.manager.domain.invoice.infrastructure.JooqCompanyRepositoryTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.postgresql.ds.PGSimpleDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Scanner;

@Testcontainers
public class TestContainerBasedTest {

    @Container
    private PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer()
            .withDatabaseName("foo")
            .withUsername("foo")
            .withPassword("secret");
    protected JooqConfig jooqConfig;

    @BeforeEach
    @SneakyThrows
    public void initDb() {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setServerName("localhost");
        ds.setPortNumber(postgresqlContainer.getFirstMappedPort());
        ds.setDatabaseName("foo");
        ds.setUser("foo");
        ds.setPassword("secret");

        String text = getSqlScript("/db/migration/V1__init.sql");
        String text2 = getSqlScript("/db/migration/V2__sampleData.sql");

        ds.getConnection().createStatement().execute(text);
        ds.getConnection().createStatement().execute(text2);

        jooqConfig = new JooqConfig(ds);
    }

    private static String getSqlScript(String name) {
        return new Scanner(JooqCompanyRepositoryTest.class.getResourceAsStream(name), "UTF-8").useDelimiter("\\A").next();
    }
}
