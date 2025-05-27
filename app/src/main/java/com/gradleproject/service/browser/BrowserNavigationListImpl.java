package com.gradleproject.service.browser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BrowserNavigationListImpl implements BrowserNavigation {
    private final List<String> history = new ArrayList<>();
    private int currentIndex = -1;


    @Override
    public synchronized void visitPage(String url) {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("URL must not be null or empty");
        }
        // Drop any forward history
        if (currentIndex < history.size() - 1) {
            history.subList(currentIndex + 1, history.size()).clear();
            log.debug("Cleared forward history, new size: {}", history.size());
        }
        history.add(url);
        currentIndex++;
        log.info("Visited new page: {} (index {})", url, currentIndex);
    }

    @Override
    public synchronized String back() {
        if (!validateBackAction()) {
            log.warn("No back history; staying on index {}: {}", currentIndex, getCurrentPage());
            return getCurrentPage();
        }
        currentIndex--;
        String page = history.get(currentIndex);
        log.info("Navigated back to: {} (index {})", page, currentIndex);
        return page;
    }

    @Override
    public synchronized String forward() {
        if (!validateForwardAction()) {
            log.warn("No forward history; staying on index {}: {}", currentIndex, getCurrentPage());
            return getCurrentPage();
        }
        currentIndex++;
        String page = history.get(currentIndex);
        log.info("Navigated forward to: {} (index {})", page, currentIndex);
        return page;
    }

    @Override
    public synchronized String getCurrentPage() {
        return currentIndex >= 0 ? history.get(currentIndex) : null;
    }

    /**
     * Validates whether a back action can be performed.
     *
     * @return true if there is at least one page to go back to.
     */
    private boolean validateBackAction() {
        return currentIndex > 0;
    }

    /**
     * Validates whether a forward action can be performed.
     *
     * @return true if there is at least one page to go forward to.
     */
    private boolean validateForwardAction() {
        return currentIndex < history.size() - 1;
    }
    
}
