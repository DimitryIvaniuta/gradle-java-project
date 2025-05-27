package com.gradleproject.service.browser;

/**
 * Defines the contract for browser-like navigation history.
 */
public interface BrowserNavigation {
    /**
     * Visit a new page by URL, pushing the current page onto the back stack and clearing the forward stack.
     *
     * @param url the URL of the page to visit
     */
    void visitPage(String url);

    /**
     * Navigate back in history. The current page is pushed onto the forward stack.
     * If there is no history, returns the current page unchanged.
     *
     * @return the URL of the new current page
     */
    String back();

    /**
     * Navigate forward in history. The current page is pushed onto the back stack.
     * If there is no forward history, returns the current page unchanged.
     *
     * @return the URL of the new current page
     */
    String forward();

    /**
     * Get the URL of the currently viewed page.
     *
     * @return the current page URL, or null if none visited yet
     */
    String getCurrentPage();
}