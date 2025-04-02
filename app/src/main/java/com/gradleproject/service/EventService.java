package com.gradleproject.service;

import com.gradleproject.model.Event;
import com.gradleproject.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Transactional
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> getEventsBetween(LocalDateTime start, LocalDateTime end) {
        return eventRepository.findByEventTimestampBetween(start, end);
    }
}
