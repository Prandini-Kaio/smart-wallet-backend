package com.prandini.smartwallet.db;

import com.maps.commons.data.liquibase.LiquibaseDiffHelper;
import jakarta.annotation.Resource;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author kaiooliveira
 * created 08/02/2025
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = DatabaseApplication.class)
public class LiquibaseDiffIT {

    @Resource
    private DataSource dataSource;

    @Test
    void diff() throws Exception {
        try (Connection conn = dataSource.getConnection()){
            LiquibaseDiffHelper diff = LiquibaseDiffHelper.forSchemas(conn, "liquibase", "hibernate").diff();
            System.err.println(diff.getChangelog());
            MatcherAssert.assertThat(diff.isChangelogEmpty(), CoreMatchers.is(true));
        }
    }
}
