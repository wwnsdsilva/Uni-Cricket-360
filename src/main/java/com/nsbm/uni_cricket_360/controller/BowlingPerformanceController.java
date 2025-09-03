package com.nsbm.uni_cricket_360.controller;

import com.nsbm.uni_cricket_360.dto.BowlingPerformanceDTO;
import com.nsbm.uni_cricket_360.service.BowlingPerformanceService;
import com.nsbm.uni_cricket_360.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/bowling-performance")
public class BowlingPerformanceController {

    @Autowired
    BowlingPerformanceService bowlingPerformanceService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getAllBowlingPerformance() {
        return new ResponseUtil(
                200,
                "OK",
                bowlingPerformanceService.getAllBowlingPerformance()
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil searchBowlingPerformanceById(@PathVariable Long id) {
        return new ResponseUtil(
                200,
                "Bowling performance details fetched successfully!",
                bowlingPerformanceService.searchBowlingPerformanceById(id)
        );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseUtil> saveBowlingPerformance(@RequestBody BowlingPerformanceDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseUtil(
                        201,
                        "Bowling performance details saved successfully!",
                        bowlingPerformanceService.saveBowlingPerformance(dto)
                )
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil updateBowlingPerformance(@PathVariable Long id, @RequestBody BowlingPerformanceDTO dto) {
        return new ResponseUtil(
                200,
                "Bowling performance details updated successfully!",
                bowlingPerformanceService.updateBowlingPerformance(id, dto)
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil deleteBowlingPerformance(@PathVariable Long id) {
        bowlingPerformanceService.deleteBowlingPerformance(id);
        return new ResponseUtil(
                200,
                "Bowling performance details deleted successfully!",
                null
        );
    }
}
