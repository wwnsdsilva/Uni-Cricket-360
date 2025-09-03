package com.nsbm.uni_cricket_360.controller;

import com.nsbm.uni_cricket_360.dto.FieldingPerformanceDTO;
import com.nsbm.uni_cricket_360.service.FieldingPerformanceService;
import com.nsbm.uni_cricket_360.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/fielding-performance")
public class FieldingPerformanceController {

    @Autowired
    FieldingPerformanceService fieldingPerformanceService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getAllFieldingPerformance() {
        return new ResponseUtil(
                200,
                "OK",
                fieldingPerformanceService.getAllFieldingPerformance()
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil searchFieldingPerformanceById(@PathVariable Long id) {
        return new ResponseUtil(
                200,
                "Fielding performance details fetched successfully!",
                fieldingPerformanceService.searchFieldingPerformanceById(id)
        );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseUtil> saveFieldingPerformance(@RequestBody FieldingPerformanceDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseUtil(
                        201,
                        "Fielding performance details saved successfully!",
                        fieldingPerformanceService.saveFieldingPerformance(dto)
                )
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil updateFieldingPerformance(@PathVariable Long id, @RequestBody FieldingPerformanceDTO dto) {
        return new ResponseUtil(
                200,
                "Fielding performance details updated successfully!",
                fieldingPerformanceService.updateFieldingPerformance(id, dto)
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil deleteFieldingPerformance(@PathVariable Long id) {
        fieldingPerformanceService.deleteFieldingPerformance(id);
        return new ResponseUtil(
                200,
                "Fielding performance details deleted successfully!",
                null
        );
    }
}
