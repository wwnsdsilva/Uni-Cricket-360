package com.nsbm.uni_cricket_360.controller;

import com.nsbm.uni_cricket_360.dto.InningDTO;
import com.nsbm.uni_cricket_360.service.InningService;
import com.nsbm.uni_cricket_360.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1/innings")
public class InningController {

    @Autowired
    InningService inningService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getAllInnings() {
        return new ResponseUtil(
                200,
                "OK",
                inningService.getAllInnings()
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil searchInningById(@PathVariable Long id) {
        return new ResponseUtil(
                200,
                "Inning details fetched successfully!",
                inningService.searchInningById(id)
        );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseUtil> saveInning(@RequestBody InningDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseUtil(
                        201,
                        "Inning saved successfully..!",
                        inningService.saveInning(dto)
                )
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil updateInning(@PathVariable Long id, @RequestBody InningDTO dto) {
        return new ResponseUtil(
                200,
                "Inning updated successfully!",
                inningService.updateInning(id, dto)
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil deleteInning(@PathVariable Long id) {
        inningService.deleteInning(id);
        return new ResponseUtil(
                200,
                "Inning deleted successfully!",
                null
        );
    }
}
