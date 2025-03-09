package com.gradleproject.model;

import com.gradleproject.dto.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getLogin(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }

}
