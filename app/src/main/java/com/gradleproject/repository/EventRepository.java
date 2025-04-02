package com.gradleproject.repository;

import com.gradleproject.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByEventTimestampBetween(LocalDateTime start, LocalDateTime end);

}
