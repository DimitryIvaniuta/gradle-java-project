package com.gradleproject.service.browser;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BrowserNavigationListImplTest {
    private BrowserNavigation navigation;

    @BeforeEach
    void setUp() {
        navigation = new BrowserNavigationListImpl();
    }

    @Test
    void testVisitAndGetCurrent() {
        navigation.visitPage("home");
        assertEquals("home", navigation.getCurrentPage());
    }

    @Test
    void testBackAndForward() {
        navigation.visitPage("a");
        navigation.visitPage("b");
        navigation.visitPage("c");

        assertEquals("b", navigation.back());
        assertEquals("c", navigation.forward());
    }

    @Test
    void testDroppingForwardHistory() {
        navigation.visitPage("1");
        navigation.visitPage("2");
        navigation.visitPage("3");
        navigation.back();          // now at "2"
        navigation.visitPage("4");
        assertEquals("4", navigation.getCurrentPage());
        assertEquals("4", navigation.forward()); // no forward history
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