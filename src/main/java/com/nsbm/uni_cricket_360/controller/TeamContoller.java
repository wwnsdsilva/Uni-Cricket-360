package com.nsbm.uni_cricket_360.controller;

import com.nsbm.uni_cricket_360.dto.TeamDTO;
import com.nsbm.uni_cricket_360.service.TeamService;
import com.nsbm.uni_cricket_360.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1/team")
public class TeamContoller {

    @Autowired
    TeamService teamService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getAllTeams() {
        return new ResponseUtil(
                200,
                "OK",
                teamService.getAllTeams()
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil searchTeamById(@PathVariable Long id) {
        return new ResponseUtil(
                200,
                "Team fetched successfully!",
                teamService.getTeamById(id)
        );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseUtil> saveTeam(@ModelAttribute TeamDTO dto) {
        System.out.println("------------------- Inside TeamContoller: saveTeam -------------------");
        System.out.println(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseUtil(
                        201,
                        "Team saved successfully..!",
                        teamService.saveTeam(dto)
                )
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil updateTeam(@PathVariable Long id, @RequestBody TeamDTO dto) {
        return new ResponseUtil(
                200,
                "Team updated successfully!",
                teamService.updateTeam(id, dto)
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return new ResponseUtil(
                200,
                "Team deleted successfully!",
                null
        );
    }

}
