package com.gradleproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = "com.gradleproject")
@EnableCaching
public class GradleProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(GradleProjectApplication.class, args);
    }

}
