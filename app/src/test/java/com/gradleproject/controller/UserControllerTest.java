package com.gradleproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gradleproject.GradleProjectApplication;
import com.gradleproject.model.User;
import com.gradleproject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = GradleProjectApplication.class)
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    private static final String LOGIN = "jane_doe";
    private static final String PASSWORD = "secret";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private User testUser;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        User user = new User();
        user.setLogin(LOGIN);
        user.setPassword(passwordEncoder.encode(PASSWORD));
        user.setName("Jane Doe");
        user.setEmail("jane@example.com");
        testUser = userRepository.save(user);
    }


    @Test
    @WithMockUser
    void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/users").with(httpBasic(LOGIN, PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(testUser.getName())));
    }

    @Test
    @WithMockUser
    void testGetUserById() throws Exception {
        mockMvc.perform(get("/users/{id}", testUser.getId()).with(httpBasic(LOGIN, PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(testUser.getName())));
    }

    @Test
    void testCreateUser() throws Exception {
        User newUser = new User("jane_doe", "jane_doe", "jane@example.com", "secret");
        mockMvc.perform(post("/users").with(httpBasic(LOGIN, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("jane_doe")));
    }

    @Test
    void testUpdateUser() throws Exception {
        testUser.setName("updated_name");
        mockMvc.perform(put("/users/{id}", testUser.getId()).with(httpBasic(LOGIN, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("updated_name")));
    }

    @Test
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/{id}", testUser.getId()).with(httpBasic(LOGIN, PASSWORD)))
                .andExpect(status().isOk());

        // Verify deletion
        mockMvc.perform(get("/users/{id}", testUser.getId()).with(httpBasic(LOGIN, PASSWORD)))
                .andExpect(status().isNotFound());
    }
}

