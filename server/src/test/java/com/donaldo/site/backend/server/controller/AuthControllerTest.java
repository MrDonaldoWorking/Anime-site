package com.donaldo.site.backend.server.controller;

import com.donaldo.site.backend.server.controllers.AuthController;
import com.donaldo.site.backend.server.models.User;
import com.donaldo.site.backend.server.payload.request.LoginRequest;
import com.donaldo.site.backend.server.payload.request.SignupRequest;
import com.donaldo.site.backend.server.service.AuthService;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {
    private final String API_URL = "/spring/auth";
    private final String SIGN_UP = API_URL + "/signup";
    private final String SIGN_IN = API_URL + "/signin";

    @MockBean
    private AuthService service;

    @Autowired
    private MockMvc mockMvc;

    private final User admin = new User("admin", "admin");
    private final User donaldo = new User("donaldo", "donaldo");
    private final User user = new User("user", "useruser");

    private SignupRequest makeRequest(final User user) {
        final SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername(user.getUsername());
        signupRequest.setPassword(user.getPassword());
        return signupRequest;
    }

    private String serializeObject(final Object o) {
        return new ObjectMapper().valueToTree(o).toString();
    }

    private ResultActions doPost(final String url, final Object o) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(serializeObject(o)));
    }

    @Test
    public void existingRegisterFailure() throws Exception {
        final SignupRequest adminRequest = makeRequest(admin);
        final SignupRequest donaldoRequest = makeRequest(donaldo);
        final SignupRequest userRequest = makeRequest(user);

        Mockito.when(service.registerUser(Mockito.any())).thenReturn(true);

        final ArgumentMatcher<SignupRequest> adminMatcher = req -> adminRequest.getUsername().equals(req.getUsername())
                && req.getUsername().equals(req.getPassword());
        Mockito.when(service.registerUser(Mockito.argThat(adminMatcher))).thenReturn(false);

        final ArgumentMatcher<SignupRequest> donaldoMatcher = req -> donaldoRequest.getPassword().equals(req.getPassword())
                && req.getPassword().equals(req.getUsername());
        Mockito.doReturn(false).when(service).registerUser(Mockito.argThat(donaldoMatcher));

        doPost(SIGN_UP, adminRequest)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        final Matcher<String> matcher = Matchers.endsWith("already exists");
        doPost(SIGN_UP, donaldoRequest)
                .andExpect(MockMvcResultMatchers.content().string(matcher));

        doPost(SIGN_UP, userRequest)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private String makeRandomString() {
        final Random random = new Random();
        final byte[] arr = new byte[random.nextInt(5) + 10];
        random.nextBytes(arr);
        return new String(arr, StandardCharsets.UTF_8);
    }

    @Test
    public void authenticateExisting() throws Exception {
        Mockito.when(service.authUser(Mockito.any())).thenReturn(null);
        final List<LoginRequest> requests = new ArrayList<>();
        Arrays.stream(AuthControllerTest.class.getFields())
                .filter(field -> field.getType().equals(User.class))
                .forEach(field -> {
                    final User user;
                    try {
                        user = (User) field.get(field);
                        final ArgumentMatcher<LoginRequest> matcher = lgn -> user.getUsername().equals(lgn.getUsername())
                                && user.getPassword().equals(lgn.getPassword());
                        Mockito.when(service.authUser(Mockito.argThat(matcher))).thenReturn(user);

                        final LoginRequest loginRequest = new LoginRequest();
                        loginRequest.setUsername(user.getUsername());
                        loginRequest.setPassword(user.getPassword());
                        requests.add(loginRequest);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });

        requests.forEach(rqt -> {
            try {
                doPost(SIGN_IN, rqt).andExpect(MockMvcResultMatchers.status().isOk());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        final Matcher<String> matcher = Matchers.startsWith("Cannot");
        for (int i = 0; i < 100; ++i) {
            final LoginRequest randRequest = new LoginRequest();
            randRequest.setUsername(makeRandomString());
            randRequest.setPassword(makeRandomString());
            doPost(SIGN_IN, randRequest)
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content().string(matcher));
        }
    }
}
