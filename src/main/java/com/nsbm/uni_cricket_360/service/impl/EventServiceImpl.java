package com.nsbm.uni_cricket_360.service.impl;

import com.nsbm.uni_cricket_360.dto.EventDTO;
import com.nsbm.uni_cricket_360.dto.PlayerDTO;
import com.nsbm.uni_cricket_360.dto.UserDTO;
import com.nsbm.uni_cricket_360.entity.*;
import com.nsbm.uni_cricket_360.exception.ImageFileException;
import com.nsbm.uni_cricket_360.exception.NotFoundException;
import com.nsbm.uni_cricket_360.repository.EventRepo;
import com.nsbm.uni_cricket_360.repository.UserRepo;
import com.nsbm.uni_cricket_360.service.EventService;
import com.nsbm.uni_cricket_360.util.UploadImageUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class EventServiceImpl implements EventService {

    @Autowired
    EventRepo eventRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UploadImageUtil uploadImageUtil;

    @Value("${event.upload-dir}")
    private String uploadDir;

    @Override
    public List<EventDTO> getAllEvents() {
        return mapper.map(eventRepo.findAll(), new TypeToken<List<EventDTO>>() {}.getType());
    }

    @Override
    public EventDTO getEventById(Long id) {
        Event event = eventRepo.findById(id).orElseThrow(() -> new NotFoundException("Event not found with id:" + id));
        return mapper.map(event, EventDTO.class);
    }

    @Override
    public EventDTO saveEvent(EventDTO dto, MultipartFile imageFile) {
        try {
            Event event = mapper.map(dto, Event.class);

            // Set created_by (Admin)
            if (dto.getCreated_by() != null && dto.getCreated_by().getId() != null) {
                Admin admin = (Admin) userRepo.findById(dto.getCreated_by().getId())
                        .orElseThrow(() -> new NotFoundException("Admin not found with id: " + dto.getCreated_by().getId()));
                event.setCreated_by(admin);
            }

            // Save image if provided
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageUrl = saveEventImage(imageFile);
                event.setImage_url(imageUrl);
            }

            Event saved = eventRepo.save(event);
            return mapper.map(saved, EventDTO.class);

        } catch (Exception ex) {
            throw new RuntimeException("Failed to save event: " + ex.getMessage(), ex);
        }
    }

    @Override
    public EventDTO updateEvent(Long id, EventDTO dto, MultipartFile imageFile) {
        Event event = eventRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Event not found with id: " + id));

        // Update fields (keep existing values if null in dto)
        if (dto.getEvent_title() != null) event.setEvent_title(dto.getEvent_title());
        if (dto.getDate_time() != null) event.setDate_time(dto.getDate_time());
        if (dto.getVenue() != null) event.setVenue(dto.getVenue());
        if (dto.getDescription() != null) event.setDescription(dto.getDescription());
        System.out.println(dto.isIs_featured());
        event.set_featured(dto.isIs_featured());

        String oldImage = null;
        String newImageUrl;

        // Handle image replacement if new one provided
        if (imageFile != null && !imageFile.isEmpty()) {
            oldImage = event.getImage_url();
            newImageUrl = saveEventImage(imageFile);
            event.setImage_url(newImageUrl);
        }

        Event updated = eventRepo.save(event);

        // Delete old image file
        deleteOldImageFile(oldImage);

        return mapper.map(updated, EventDTO.class);
    }

    @Override
    public EventDTO updateEventImage(Long id, MultipartFile imageFile) {
        Event event = eventRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Event not found with id: " + id));

        String oldImage = event.getImage_url();
        String newImageUrl = saveEventImage(imageFile);
        event.setImage_url(newImageUrl);

        Event updated = eventRepo.save(event);

        // Delete old image
        deleteOldImageFile(oldImage);

        return mapper.map(updated, EventDTO.class);
    }

    @Override
    public void deleteEvent(Long id) {
        Event event = eventRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Event not found with id: " + id));

        // Delete image file if exists
        deleteOldImageFile(event.getImage_url());

        // Delete event from DB
        eventRepo.delete(event);
    }

    private String saveEventImage(MultipartFile imageFile) {
        return uploadImageUtil.saveImage(uploadDir, "events", imageFile);
    }

    private void deleteOldImageFile(String oldImageUrl){
        if (oldImageUrl != null) {
            try {
                Files.deleteIfExists(Paths.get(oldImageUrl));
            } catch (IOException e) {
                throw new ImageFileException("Failed to delete event image: " + e.getMessage());
            }
        }
    }
}
