package com.nsbm.uni_cricket_360.controller;

import com.nsbm.uni_cricket_360.dto.LoginDTO;
import com.nsbm.uni_cricket_360.service.AuthService;
import com.nsbm.uni_cricket_360.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping(path = "login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseUtil> login(@RequestBody LoginDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseUtil(
                        200,
                        "OK",
                        authService.login(dto)
                )
        );
    }
}
