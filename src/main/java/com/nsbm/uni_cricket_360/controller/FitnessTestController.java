package com.nsbm.uni_cricket_360.controller;

import com.nsbm.uni_cricket_360.dto.FieldingPerformanceDTO;
import com.nsbm.uni_cricket_360.dto.FitnessTestDTO;
import com.nsbm.uni_cricket_360.service.FitnessTestService;
import com.nsbm.uni_cricket_360.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/fitness-test")
public class FitnessTestController {

    @Autowired
    FitnessTestService fitnessTestService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getAllFitnessTestResults() {
        return new ResponseUtil(
                200,
                "OK",
                fitnessTestService.getAllFitnessTestResults()
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil searchFitnessTestResults(@PathVariable Long id) {
        return new ResponseUtil(
                200,
                "Fitness test results fetched successfully!",
                fitnessTestService.searchFitnessTestResultsByPlayer(id)
        );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil saveFitnessTestResults(@RequestBody FitnessTestDTO dto) {
        return new ResponseUtil(
                201,
                "Fitness test results saved successfully!",
                fitnessTestService.saveFitnessTestResults(dto)
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil updateFitnessTestResults(@PathVariable Long id, @RequestBody FitnessTestDTO dto) {
        return new ResponseUtil(
                200,
                "Fitness test results updated successfully!",
                fitnessTestService.updateFitnessTestResults(id, dto)
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil deleteFitnessTestResults(@PathVariable Long id) {
        fitnessTestService.deleteFitnessTestResults(id);
        return new ResponseUtil(
                200,
                "Fitness test results deleted successfully!",
                null
        );
    }
}
