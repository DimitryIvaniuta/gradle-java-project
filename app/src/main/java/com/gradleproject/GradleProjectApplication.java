package com.gradleproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.gradleproject")
public class GradleProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(GradleProjectApplication.class, args);
    }

}
