package ru.netology.conditional;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;
    private static GenericContainer<?> devContainer = new GenericContainer<>("devapp")
            .withExposedPorts(8080);
    private static GenericContainer<?> prodContainer = new GenericContainer<>("prodapp")
            .withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        devContainer.start();
        prodContainer.start();
    }

    @Test
    void contextLoads() {
        ResponseEntity<String> devEntity = restTemplate.getForEntity("http://localhost:" + devContainer.getMappedPort(8080)
                + "/profile", String.class);
        Assertions.assertEquals("Current profile is dev", devEntity.getBody());

        ResponseEntity<String> prodEntity = restTemplate.getForEntity("http://localhost:" + prodContainer.getMappedPort(8081)
                + "/profile", String.class);
        Assertions.assertEquals("Current profile is production", devEntity.getBody());
    }
}
