package com.nsbm.uni_cricket_360.controller;

import com.nsbm.uni_cricket_360.dto.PlayerDTO;
import com.nsbm.uni_cricket_360.dto.UserDTO;
import com.nsbm.uni_cricket_360.service.PlayerService;
import com.nsbm.uni_cricket_360.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1/player")
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseUtil> getAllPlayers() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseUtil(
                        200,
                        "OK",
                        playerService.getAllPlayers()
                )
        );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseUtil> savePlayer(@RequestBody PlayerDTO dto) {
        System.out.println("------------------- Inside PlayerContoller: savePlayer -------------------");
        System.out.println(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseUtil(
                        201,
                        "Player registered successfully..!",
                        playerService.savePlayer(dto)
                )
        );
    }
}
