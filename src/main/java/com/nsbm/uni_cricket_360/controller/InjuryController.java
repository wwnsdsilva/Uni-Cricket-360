package com.nsbm.uni_cricket_360.controller;

import com.nsbm.uni_cricket_360.dto.InjuryDTO;
import com.nsbm.uni_cricket_360.service.InjuryService;
import com.nsbm.uni_cricket_360.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/injury")
public class InjuryController {

    @Autowired
    InjuryService injuryService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getAllInjuries() {
        return new ResponseUtil(
                200,
                "OK",
                injuryService.getAllInjuries()
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getInjuriesCount() {
        return new ResponseUtil(
                200,
                "OK",
                injuryService.getInjuriesCount()
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil searchInjury(@PathVariable Long id) {
        return new ResponseUtil(
                200,
                "Injury details fetched successfully!",
                injuryService.searchInjury(id)
        );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil saveInjury(@RequestBody InjuryDTO dto) {
        return new ResponseUtil(
                201,
                "Injury details saved successfully!",
                injuryService.saveInjury(dto)
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil updateInjury(@PathVariable Long id, @RequestBody InjuryDTO dto) {
        return new ResponseUtil(
                200,
                "Injury details updated successfully!",
                injuryService.updateInjury(id, dto)
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil deleteInjury(@PathVariable Long id) {
        injuryService.deleteInjury(id);
        return new ResponseUtil(
                200,
                "Injury details deleted successfully!",
                null
        );
    }
}
