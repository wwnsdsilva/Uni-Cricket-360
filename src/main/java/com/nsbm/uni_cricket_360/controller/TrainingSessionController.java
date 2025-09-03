package com.nsbm.uni_cricket_360.controller;

import com.nsbm.uni_cricket_360.dto.TrainingSessionDTO;
import com.nsbm.uni_cricket_360.service.TrainingSessionService;
import com.nsbm.uni_cricket_360.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1/training-session")
public class TrainingSessionController {

    @Autowired
    TrainingSessionService trainingSessionService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getAllTrainingSessions() {
        return new ResponseUtil(
                200,
                "OK",
                trainingSessionService.getAllTrainingSessions()
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil searchSessionById(@PathVariable Long id) {
        return new ResponseUtil(
                200,
                "Training session details fetched successfully!",
                trainingSessionService.searchSessionById(id)
        );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil saveSession(@RequestBody TrainingSessionDTO dto) {
        return new ResponseUtil(
                201,
                "Training session saved successfully..!",
                trainingSessionService.saveSession(dto)
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil updateSession(@PathVariable Long id, @RequestBody TrainingSessionDTO dto) {
        return new ResponseUtil(
                200,
                "Training session updated successfully!",
                trainingSessionService.updateSession(id, dto)
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil deleteSession(@PathVariable Long id) {
        trainingSessionService.deleteSession(id);
        return new ResponseUtil(
                200,
                "Training session deleted successfully!",
                null
        );
    }
}
