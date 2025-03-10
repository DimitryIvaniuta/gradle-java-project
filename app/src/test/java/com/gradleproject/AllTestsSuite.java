package com.gradleproject;

import com.gradleproject.controller.UserControllerTest;
import com.gradleproject.model.UserRepositoryTest;
import com.gradleproject.model.UserTest;
import com.gradleproject.service.UserAuthTest;
import com.gradleproject.service.UserServiceTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Gradle Project - Test Suite")
@SelectClasses({
        UserControllerTest.class,
        UserRepositoryTest.class,
        UserTest.class,
        UserAuthTest.class,
        UserServiceTest.class
})
public class AllTestsSuite {
}