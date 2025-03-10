package com.gradleproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gradleproject.GradleProjectApplication;
import com.gradleproject.model.User;
import com.gradleproject.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = GradleProjectApplication.class)
@AutoConfigureMockMvc
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {

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

    @BeforeAll
    void setUp() {
        userRepository.deleteAll();
        testUser = new User();
        testUser.setLogin(LOGIN);
        testUser.setPassword(passwordEncoder.encode(PASSWORD));
        testUser.setName("Jane Doe");
        testUser.setEmail("jane@example.com");
        userRepository.save(testUser);
    }

    @Test
    @WithMockUser(username = "jane_doe", roles = "USER")
    void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(testUser.getName())));
    }

    @Test
    @WithMockUser(username = "jane_doe", roles = "USER")
    void testGetUserById() throws Exception {
        mockMvc.perform(get("/users/{id}", testUser.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(testUser.getEmail())));
    }

    @Test
    @WithMockUser(username = "jane_doe", roles = "USER")
    void testCreateUser() throws Exception {
        User newUser = new User("new_login", "new_name", "new@example.com", "new_password");
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("new_name")));
    }

    @Test
    @WithMockUser(username = "jane_doe", roles = "USER")
    void testUpdateUser() throws Exception {
        testUser.setName("updated_name");

        mockMvc.perform(put("/users/{id}", testUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk());

        User updatedUser = userRepository.findById(testUser.getId()).orElseThrow();

        assertThat(updatedUser.getName()).isEqualTo("updated_name");

        mockMvc.perform(get("/users/{id}", testUser.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("updated_name")));
    }


    @Test
    @WithMockUser(username = "jane_doe", roles = "USER")
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/{id}", testUser.getId()))
                .andExpect(status().isOk());

        // Verify deletion
        mockMvc.perform(get("/users/{id}", testUser.getId()))
                .andExpect(status().isNotFound());
    }

}
