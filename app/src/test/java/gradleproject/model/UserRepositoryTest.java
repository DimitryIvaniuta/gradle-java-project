package gradleproject.model;

import com.gradleproject.GradleProjectApplication;
import com.gradleproject.model.User;
import com.gradleproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = GradleProjectApplication.class)
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testCreateAndFindUser() {
        User user = new User("testuser", "testuser", "test@example.com", "secret");
        User savedUser = userRepository.save(user);
        assertThat(savedUser.getId()).isNotNull();

        User found = userRepository.findById(savedUser.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("testuser");
    }

}
