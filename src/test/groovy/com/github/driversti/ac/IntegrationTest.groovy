package com.github.driversti.ac

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.jdbc.datasource.init.ScriptUtils
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlConfig
import org.springframework.test.context.jdbc.SqlGroup
import spock.lang.Specification

@SqlGroup([
        @Sql(value = "classpath:/sql/clear-db.sql", executionPhase = BEFORE_TEST_METHOD, config = @SqlConfig(separator = ScriptUtils.EOF_STATEMENT_SEPARATOR)),
        @Sql(value = "classpath:/sql/clear-db.sql", executionPhase = AFTER_TEST_METHOD, config = @SqlConfig(separator = ScriptUtils.EOF_STATEMENT_SEPARATOR))
])
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTest extends Specification {
    @Autowired
    protected ObjectMapper objectMapper
    @Autowired
    protected TestRestTemplate testRestTemplate
}
