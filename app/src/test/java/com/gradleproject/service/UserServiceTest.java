package com.gradleproject.service;

import com.gradleproject.model.User;
import com.gradleproject.repository.UserRepository;
import com.gradleproject.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new User("john_doe", "john_doe", "john@example.com", "password");
        testUser.setId(1L);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Collections.singletonList(testUser);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();
        assertEquals(1, result.size());
        assertEquals("john_doe", result.get(0).getName());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserByIdFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        Optional<User> result = userService.getUserById(1L);
        assertTrue(result.isPresent());
        assertEquals("john_doe", result.get().getName());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<User> result = userService.getUserById(1L);
        assertFalse(result.isPresent());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(testUser);

        User result = userService.createUser(testUser);
        assertNotNull(result);
        assertEquals("john_doe", result.getName());

        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void testUpdateUserFound() {
        User updatedUser = new User("jane_doe", "jane_doe", "jane@example.com", "newpassword");

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(updatedUser);

        Optional<User> result = userService.updateUser(1L, updatedUser);
        assertTrue(result.isPresent());
        assertEquals("jane_doe", result.get().getName());

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void testUpdateUserNotFound() {
        User updatedUser = new User("jane_doe", "jane_doe", "jane@example.com", "newpassword");

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<User> result = userService.updateUser(1L, updatedUser);
        assertFalse(result.isPresent());

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testDeleteUserFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        doNothing().when(userRepository).delete(testUser);

        boolean deleted = userService.deleteUser(1L);
        assertTrue(deleted);

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).delete(testUser);
    }

    @Test
    void testDeleteUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        boolean deleted = userService.deleteUser(1L);
        assertFalse(deleted);

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(0)).delete(any(User.class));
    }
}
