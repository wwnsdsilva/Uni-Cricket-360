package com.nsbm.uni_cricket_360.repository;

import com.nsbm.uni_cricket_360.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepo extends JpaRepository<Event, Long> {
}
