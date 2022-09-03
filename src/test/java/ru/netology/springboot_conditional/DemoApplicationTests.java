package ru.netology.springboot_conditional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;

    @Container
    private final GenericContainer<?> dev = new GenericContainer<>("devapp:latest")
            .withExposedPorts(8080);

    @Container
    private final GenericContainer<?> prod = new GenericContainer<>("prodapp:latest")
            .withExposedPorts(8081);

    @Test
    void devProfileTest() {
        ResponseEntity<String> devEntity = restTemplate.getForEntity("http://localhost:" +
                dev.getMappedPort(8080) + "/profile", String.class);
        Assertions.assertEquals("Current profile is dev", devEntity.getBody());
    }

    @Test
    void prodProfileTest() {
        ResponseEntity<String> prodEntity = restTemplate.getForEntity("http://localhost:" +
                prod.getMappedPort(8081) + "/profile", String.class);
        Assertions.assertEquals("Current profile is production", prodEntity.getBody());
    }

}
