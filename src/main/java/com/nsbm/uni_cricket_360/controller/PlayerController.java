package com.nsbm.uni_cricket_360.controller;

import com.nsbm.uni_cricket_360.dto.EventDTO;
import com.nsbm.uni_cricket_360.dto.PlayerDTO;
import com.nsbm.uni_cricket_360.dto.PlayerUpdateDTO;
import com.nsbm.uni_cricket_360.dto.UserDTO;
import com.nsbm.uni_cricket_360.entity.Player;
import com.nsbm.uni_cricket_360.enums.PlayerRole;
import com.nsbm.uni_cricket_360.service.PlayerService;
import com.nsbm.uni_cricket_360.util.LoginResponseUtil;
import com.nsbm.uni_cricket_360.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

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

    @GetMapping(value = "/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseUtil> getPlayerCount() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseUtil(
                        200,
                        "OK",
                        playerService.getPlayerCount()
                )
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil searchPlayerById(@PathVariable Long id) {
        return new ResponseUtil(
                200,
                "Player fetched successfully!",
                playerService.getPlayerById(id)
        );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseUtil savePlayer(
            @Valid @RequestPart("player") PlayerDTO dto,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {

        return new ResponseUtil(
                201,
                "Player registered successfully..!",
                playerService.savePlayer(dto, imageFile)
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseUtil updatePlayer(
            @PathVariable Long id,
            @Valid @RequestPart("player") PlayerUpdateDTO dto,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {

        return new ResponseUtil(
                200,
                "Player updated successfully..!",
                playerService.updatePlayer(id, dto, imageFile)
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseUtil updateEventImage(
            @PathVariable Long id,
            @RequestPart("image") MultipartFile imageFile) {

        return  new ResponseUtil(
                200, "" +
                "Player image updated successfully..!",
                playerService.updatePlayerImage(id, imageFile)
        );
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<ResponseUtil> updatePlayerRole(
            @PathVariable Long id,
            @RequestBody PlayerDTO dto) {

        String newRoleStr = String.valueOf(dto.getPlayer_role());
        if (newRoleStr == null || newRoleStr.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseUtil(400, "player_role is required", null));
        }

        PlayerRole newRole;
        try {
            newRole = PlayerRole.valueOf(newRoleStr.toUpperCase());
        } catch (IllegalArgumentException ex) {
            String allowedValues = Arrays.stream(PlayerRole.values())
                    .map(Enum::name)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseUtil(400, "Invalid player_role. Allowed values: [" + allowedValues + "]", null));
        }

        try {
            PlayerDTO playerDTO = playerService.updatePlayerRole(id, newRole);
            return ResponseEntity.ok(new ResponseUtil(200, "Player role updated successfully!", playerDTO));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseUtil(404, ex.getMessage(), null));
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public ResponseUtil deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return new ResponseUtil(
                200,
                "Player deleted successfully..!",
                null)
        ;
    }

}
