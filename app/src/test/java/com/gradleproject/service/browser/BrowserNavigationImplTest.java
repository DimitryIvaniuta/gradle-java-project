package com.gradleproject.service.browser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for BrowserNavigationImpl.
 */
class BrowserNavigationImplTest {
    private BrowserNavigation navigation;

    @BeforeEach
    void setUp() {
        navigation = new BrowserNavigationImpl();
    }

    @Test
    void testVisitAndGetCurrent() {
        navigation.visitPage("home");
        assertEquals("home", navigation.getCurrentPage());
    }

    @Test
    void testBackAndForward() {
        navigation.visitPage("page1");
        navigation.visitPage("page2");

        String back1 = navigation.back();
        assertEquals("page1", back1);
        assertEquals("page1", navigation.getCurrentPage());

        String forward1 = navigation.forward();
        assertEquals("page2", forward1);
        assertEquals("page2", navigation.getCurrentPage());
    }

    @Test
    void testBackEmpty() {
        navigation.visitPage("only");
        assertEquals("only", navigation.back());
    }

    @Test
    void testForwardEmpty() {
        navigation.visitPage("only");
        assertEquals("only", navigation.forward());
    }
}
