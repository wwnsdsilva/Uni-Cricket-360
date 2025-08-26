package com.nsbm.uni_cricket_360.controller;

import com.nsbm.uni_cricket_360.dto.UserDTO;
import com.nsbm.uni_cricket_360.service.UserService;
import com.nsbm.uni_cricket_360.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getAllUsers() {
        return new ResponseUtil(
                200,
                "OK",
                userService.getAllUsers()
        );
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseUtil> searchUserById(@PathVariable Long id) {
        return ResponseEntity.ok(
                new ResponseUtil(
                        200,
                        "User fetched successfully!",
                        userService.getUserById(id)
                )
        );
    }

    @GetMapping(path = "/email", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseUtil> searchUserByEmail(@RequestParam String email) {
        return ResponseEntity.ok(
                new ResponseUtil(
                        200,
                        "User fetched successfully!",
                        userService.getUserByEmail(email)
                )
        );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseUtil> saveUser(@ModelAttribute UserDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseUtil(
                        201,
                        "User registered successfully..!",
                        userService.saveUser(dto)
                )
        );
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseUtil> updateUser(@PathVariable Long id, @RequestBody UserDTO dto) {
        return ResponseEntity.ok(
                new ResponseUtil(
                        200,
                        "User updated successfully!",
                        userService.updateUser(id, dto)
                )
        );
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseUtil> patchUser(@PathVariable Long id, @RequestBody UserDTO dto) {
        return ResponseEntity.ok(
                new ResponseUtil(
                        200,
                        "User patched successfully!",
                        userService.patchUser(id, dto)
                )
        );
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseUtil> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(
                new ResponseUtil(
                        200,
                        "User deleted successfully!",
                        null
                )
        );
    }
}
