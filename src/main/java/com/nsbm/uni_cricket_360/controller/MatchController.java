package com.nsbm.uni_cricket_360.controller;

import com.nsbm.uni_cricket_360.dto.MatchDTO;
import com.nsbm.uni_cricket_360.dto.MatchStatsDTO;
import com.nsbm.uni_cricket_360.dto.TeamDTO;
import com.nsbm.uni_cricket_360.service.MatchService;
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
@RequestMapping("api/v1/match")
public class MatchController {

    @Autowired
    MatchService matchService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getAllMatches() {
        return new ResponseUtil(
                200,
                "OK",
                matchService.getAllMatches()
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil searchMatchById(@PathVariable Long id) {
        return new ResponseUtil(
                200,
                "Match details fetched successfully!",
                matchService.getMatchById(id)
        );
    }

    /** Saves only match data with image */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseUtil saveMatch(
            @Valid @RequestPart("match") MatchDTO dto,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {

        return new ResponseUtil(
                201,
                "Match saved successfully..!",
                matchService.saveMatch(dto, imageFile)
        );
    }

    /** Updates match scores with batting, bowling and fielding stats */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/stats", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil saveMatchStatistics(@RequestBody MatchStatsDTO dto) {

        return new ResponseUtil(
                200,
                "Match stats saved successfully..!",
                matchService.saveMatchStatistics(dto)
        );
    }

    /** Updates only match data with image */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseUtil updateMatch(
            @PathVariable Long id,
            @Valid @RequestPart("match") MatchDTO dto,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {

        return new ResponseUtil(
                200,
                "Match updated successfully..!",
                matchService.updateMatch(id, dto, imageFile)
        );
    }

    /** Updates only match data without image */
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = "/scheduled/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil updateScheduledMatch(@PathVariable Long id, @RequestBody MatchDTO dto) {
        return new ResponseUtil(
                200,
                "Scheduled match updated successfully..!",
                matchService.updateScheduledMatch(id, dto)
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseUtil updateMatchImage(
            @PathVariable Long id,
            @RequestPart("image") MultipartFile imageFile) {

        return new ResponseUtil(
                200, "" +
                "Match image updated successfully..!",
                matchService.updateMatchImage(id, imageFile)
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseUtil> deleteMatch(@PathVariable Long id) {
        matchService.deleteMatch(id);
        return ResponseEntity.ok(
                new ResponseUtil(200, "Match deleted successfully..!", null)
        );
    }
}
