package com.nsbm.uni_cricket_360.controller;

import com.nsbm.uni_cricket_360.dto.BattingPerformanceDTO;
import com.nsbm.uni_cricket_360.service.BattingPerformanceService;
import com.nsbm.uni_cricket_360.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/batting-performance")
public class BattingPerformanceController {

    @Autowired
    BattingPerformanceService battingPerformanceService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getAllBattingPerformance() {
        return new ResponseUtil(
                200,
                "OK",
                battingPerformanceService.getAllBattingPerformance()
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil searchBattingPerformanceById(@PathVariable Long id) {
        return new ResponseUtil(
                200,
                "Performance details fetched successfully!",
                battingPerformanceService.searchBattingPerformanceById(id)
        );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseUtil> saveBattingPerformance(@RequestBody BattingPerformanceDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseUtil(
                        201,
                        "Batting performance details saved successfully!",
                        battingPerformanceService.saveBattingPerformance(dto)
                )
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil updateBattingPerformance(@PathVariable Long id, @RequestBody BattingPerformanceDTO dto) {
        return new ResponseUtil(
                200,
                "Batting performance details updated successfully!",
                battingPerformanceService.updateBattingPerformance(id, dto)
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil deleteBattingPerformance(@PathVariable Long id) {
        battingPerformanceService.deleteBattingPerformance(id);
        return new ResponseUtil(
                200,
                "Batting performance details deleted successfully!",
                null
        );
    }
}
