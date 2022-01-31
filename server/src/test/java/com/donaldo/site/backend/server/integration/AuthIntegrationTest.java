package com.donaldo.site.backend.server.integration;

import com.donaldo.site.backend.server.models.User;
import com.donaldo.site.backend.server.payload.request.LoginRequest;
import com.donaldo.site.backend.server.payload.request.SignupRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.validation.constraints.NotNull;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = AuthIntegrationTest.Initializer.class)
public class AuthIntegrationTest {
    public static final String SERVER_URL = "http://localhost";

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(final ConfigurableApplicationContext applicationContext) {
            final PostgreSQLContainer container = new PostgreSQLContainer("postgres:12.9")
                    .withDatabaseName("integrations-tests")
                    .withUsername("admin")
                    .withPassword("admin");
            container.start();
            TestPropertyValues.of(
                    "spring.datasource.url=" + container.getJdbcUrl(),
                    "spring.datasource.username=" + container.getUsername(),
                    "spring.datasource.password=" + container.getPassword()
            ).applyTo(applicationContext.getEnvironment());
        }
    }

    @LocalServerPort
    private int port = 0;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Test
    public void registerAndLogin() {
        final RestTemplate restTemplate = restTemplateBuilder.build();

        final User ghislane = new User("Gsihlane", "BestGirl");

        final SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername(ghislane.getUsername());
        signupRequest.setPassword(ghislane.getPassword());
        final ResponseEntity<?> signUpResponse = restTemplate.postForEntity(
                SERVER_URL + ":" + port + "/spring/auth/signup",
                signupRequest,
                String.class
        );
        Assertions.assertEquals(HttpStatus.OK, signUpResponse.getStatusCode());

        final HttpHeaders cookieHeaders = new HttpHeaders();
        cookieHeaders.add("Cookie", signUpResponse.getHeaders().getValuesAsList("Set-Cookie").toString());

        final LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(ghislane.getUsername());
        loginRequest.setPassword(ghislane.getPassword());
        final ResponseEntity<?> signInResponse = restTemplate.exchange(
                SERVER_URL + ":" + port + "/spring/auth/signin",
                HttpMethod.POST,
                new HttpEntity(loginRequest, cookieHeaders),
                User.class
        );

        Assertions.assertEquals(HttpStatus.OK, signInResponse.getStatusCode());
//        Assertions.assertEquals(ghislane, signInResponse.getBody());
    }
}
