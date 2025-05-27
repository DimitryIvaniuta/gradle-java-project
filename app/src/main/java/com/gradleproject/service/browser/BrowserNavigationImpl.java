package com.gradleproject.service.browser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Thread-safe default implementation of BrowserNavigation using two stacks:
 * one for back-history and one for forward-history.
 */
@Service
@Slf4j
public class BrowserNavigationImpl implements BrowserNavigation {

    private final Deque<String> backStack = new ArrayDeque<>();

    private final Deque<String> forwardStack = new ArrayDeque<>();

    private String currentPage;

    @Override
    public synchronized void visitPage(String url) {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("URL must not be null or empty");
        }
        if (currentPage != null) {
            backStack.push(currentPage);
            log.debug("Pushed to backStack: {}", currentPage);
        }
        currentPage = url;
        forwardStack.clear();
        log.info("Visited new page: {}", url);
    }

    @Override
    public synchronized String back() {
        if (backStack.isEmpty()) {
            log.warn("Back stack is empty; staying on current page: {}", currentPage);
            return currentPage;
        }
        forwardStack.push(currentPage);
        currentPage = backStack.pop();
        log.info("Navigated back to: {}", currentPage);
        return currentPage;
    }

    @Override
    public synchronized String forward() {
        if (forwardStack.isEmpty()) {
            log.warn("Forward stack is empty; staying on current page: {}", currentPage);
            return currentPage;
        }
        backStack.push(currentPage);
        currentPage = forwardStack.pop();
        log.info("Navigated forward to: {}", currentPage);
        return currentPage;
    }

    @Override
    public synchronized String getCurrentPage() {
        return currentPage;
    }
}
