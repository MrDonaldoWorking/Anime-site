package com.donaldo.site.backend.server.controllers;

import com.donaldo.site.backend.server.ServerApplication;
import com.donaldo.site.backend.server.models.Streams;
import com.donaldo.site.backend.server.models.projections.IdAndTitle;
import com.donaldo.site.backend.server.security.WebSecurityConfig;
import com.donaldo.site.backend.server.service.TestService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(TestController.class)
public class TestControllerTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TestService service;

    @MockBean
    private ServerApplication serverApplication;

    @MockBean
    private WebSecurityConfig webSecurityConfig;

    @BeforeEach
    public void prepare() {
        service = Mockito.mock(TestService.class);
        Mockito.when(service.all()).thenReturn("all");
        Mockito.when(service.getStreams()).thenReturn(List.of(new Streams("//localhost", "Aniwatcher")));
        Mockito.when(service.getTitles()).thenReturn(List.of(new IdAndTitle(1, "Hunter x Hunter")));

        serverApplication = Mockito.mock(ServerApplication.class);
        webSecurityConfig = Mockito.mock(WebSecurityConfig.class);
    }

    @Test
    public void testAllAccess() throws Exception {
        final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertEquals("all", result.getResponse().getContentAsString());
    }
}
