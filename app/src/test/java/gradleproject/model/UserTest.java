package gradleproject.model;

import com.gradleproject.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testNoArgsConstructorSetsCreatedAt() {
        User user = new User();
        assertNotNull(user.getCreatedAt(), "The no-args constructor should initialize createdAt");
    }

    @Test
    void testParameterizedConstructor() {
        String login = "testuser";
        String username = "testuser";
        String email = "test@example.com";
        String password = "secret";
        User user = new User(login, username, email, password);

        assertEquals(username, user.getName());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertNotNull(user.getCreatedAt(), "The parameterized constructor should initialize createdAt");
    }

    @Test
    void testSettersAndGetters() {
        User user = new User();
        user.setId(100L);
        user.setName("john_doe");
        user.setEmail("john@example.com");
        user.setPassword("password123");
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedAt(now);

        assertEquals(100L, user.getId());
        assertEquals("john_doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals(now, user.getCreatedAt());
    }
}
