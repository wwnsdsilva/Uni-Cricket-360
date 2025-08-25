package com.nsbm.uni_cricket_360.controller;

import com.nsbm.uni_cricket_360.dto.PlayerDTO;
import com.nsbm.uni_cricket_360.dto.UserDTO;
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseUtil> savePlayer(
            @Valid @RequestPart("player") PlayerDTO dto,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {

        System.out.println("------------------- Inside PlayerController: savePlayer -------------------");
        System.out.println(dto);

        // save the image and get URL/path
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl;
            try {
                imageUrl = playerService.savePlayerImage(imageFile);
            } catch (RuntimeException ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseUtil(500, ex.getMessage(), null));
            }
            dto.setImage_url(imageUrl);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseUtil(
                        201,
                        "Player registered successfully..!",
                        playerService.savePlayer(dto)
                )
        );
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseUtil> updatePlayer(
            @PathVariable Long id,
            @Valid @RequestPart("player") PlayerDTO dto,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {

        System.out.println("------------------- Inside PlayerController: updatePlayer -------------------");
        System.out.println(dto);

        // set the ID from the path variable to the DTO
        dto.setId(id);

        // update image if provided
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl;
            try {
                imageUrl = playerService.savePlayerImage(imageFile);
            } catch (RuntimeException ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseUtil(500, ex.getMessage(), null));
            }
            dto.setImage_url(imageUrl);
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseUtil(
                        200,
                        "Player updated successfully..!",
                        playerService.updatePlayer(dto)
                )
        );
    }

}
