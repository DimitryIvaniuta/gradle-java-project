package com.gradleproject.service;


import com.gradleproject.model.User;
import com.gradleproject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserAuthTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String LOGIN = "jane_doe";
    private static final String PASSWORD = "secret";

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        User user = new User();
        user.setLogin(LOGIN);
        user.setPassword(passwordEncoder.encode(PASSWORD));
        user.setName("Jane Doe");
        user.setEmail("jane@example.com");
        userRepository.save(user);
    }

    @Test
    void shouldAuthenticateWithValidCredentials() throws Exception {
        mockMvc.perform(get("/actuator/health").with(httpBasic(LOGIN, PASSWORD)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFailAuthenticationWithInvalidCredentials() throws Exception {
        mockMvc.perform(get("/actuator/health").with(httpBasic(LOGIN, "wrongpassword")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldDenyAccessToProtectedEndpointsWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldAllowAccessToHealthCheckForRegisteredUser() throws Exception {
        mockMvc.perform(get("/actuator/health").with(httpBasic(LOGIN, PASSWORD)))
                .andExpect(status().isOk()); // Expect 200 OK for authenticated user
    }

}
