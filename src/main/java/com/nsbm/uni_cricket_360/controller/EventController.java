package com.nsbm.uni_cricket_360.controller;

import com.nsbm.uni_cricket_360.dto.EventDTO;
import com.nsbm.uni_cricket_360.dto.PlayerDTO;
import com.nsbm.uni_cricket_360.dto.TeamDTO;
import com.nsbm.uni_cricket_360.service.EventService;
import com.nsbm.uni_cricket_360.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("api/v1/event")
public class EventController {

    @Autowired
    EventService eventService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getAllEvents() {
        return new ResponseUtil(
                200,
                "OK",
                eventService.getAllEvents()
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil searchEventById(@PathVariable Long id) {
        return new ResponseUtil(
                200,
                "Event fetched successfully!",
                eventService.getEventById(id)
        );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseUtil saveEvent(
            @Valid @RequestPart("event") EventDTO dto,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {

        return new ResponseUtil(
                201,
                "Event saved successfully..!",
                eventService.saveEvent(dto, imageFile)
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseUtil updateEvent(
            @PathVariable Long id,
            @Valid @RequestPart("event") EventDTO dto,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {

        return new ResponseUtil(
                200,
                "Event updated successfully..!",
                eventService.updateEvent(id, dto, imageFile)
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseUtil updateEventImage(
            @PathVariable Long id,
            @RequestPart("image") MultipartFile imageFile) {

        return  new ResponseUtil(
                200, "" +
                "Event image updated successfully..!",
                eventService.updateEventImage(id, imageFile)
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseUtil> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok(
                new ResponseUtil(200, "Event deleted successfully..!", null)
        );
    }
}
