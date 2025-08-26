package com.nsbm.uni_cricket_360.service;

import com.nsbm.uni_cricket_360.dto.EventDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EventService {
    List<EventDTO> getAllEvents();

    EventDTO getEventById(Long id);

    EventDTO saveEvent(EventDTO dto, MultipartFile imageFile);

    EventDTO updateEvent(Long id, EventDTO dto, MultipartFile imageFile);

    EventDTO updateEventImage(Long id, MultipartFile imageFile);

    void deleteEvent(Long id);
}
