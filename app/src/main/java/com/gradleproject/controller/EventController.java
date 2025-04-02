package com.gradleproject.controller;

import com.gradleproject.model.Event;
import com.gradleproject.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        if (event.getEventTimestamp() == null) {
            event.setEventTimestamp(LocalDateTime.now());
        }
        Event createdEvent = eventService.createEvent(event);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Event>> getEvents(
            @RequestParam("start") String startStr,
            @RequestParam("end") String endStr) {
        LocalDateTime start = LocalDateTime.parse(startStr);
        LocalDateTime end = LocalDateTime.parse(endStr);
        List<Event> events = eventService.getEventsBetween(start, end);
        return ResponseEntity.ok(events);
    }
}
