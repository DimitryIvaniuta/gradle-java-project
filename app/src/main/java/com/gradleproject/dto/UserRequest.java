package com.gradleproject.dto;

import com.gradleproject.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private String login;
    private String name;
    private String email;
    private String password;

    public User toUser() {
        return new User(this.getLogin(),
                this.getName(),
                this.getEmail(),
                this.getPassword());
    }

}
