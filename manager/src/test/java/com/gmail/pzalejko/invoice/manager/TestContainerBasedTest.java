package com.gmail.pzalejko.invoice.manager;

import com.gmail.pzalejko.invoice.manager.domain.invoice.infrastructure.JooqCompanyRepositoryTest;
import lombok.SneakyThrows;
import org.jooq.Table;
import org.junit.jupiter.api.BeforeEach;
import org.postgresql.ds.PGSimpleDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Scanner;

@Testcontainers
public class TestContainerBasedTest {

    @Container
    private PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer("postgres:14")
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

        ds.getConnection().createStatement().execute(text);

        jooqConfig = new JooqConfig(ds);
    }

    protected void clearTables(Table<?> t1, Table<?>... tables) {
        var dsl = jooqConfig.dsl();

        dsl.delete(t1).execute();
        for (var table : tables) {
            dsl.delete(table).execute();
        }
    }

    private static String getSqlScript(String name) {
        return new Scanner(JooqCompanyRepositoryTest.class.getResourceAsStream(name), "UTF-8").useDelimiter("\\A").next();
    }
}
