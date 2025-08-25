package com.nsbm.uni_cricket_360.service.impl;

import com.nsbm.uni_cricket_360.dto.LoginDTO;
import com.nsbm.uni_cricket_360.entity.User;
import com.nsbm.uni_cricket_360.repository.UserRepo;
import com.nsbm.uni_cricket_360.service.AuthService;
import com.nsbm.uni_cricket_360.util.JwtUtil;
import com.nsbm.uni_cricket_360.util.LoginResponse;
import com.nsbm.uni_cricket_360.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }


    @Override
    public ResponseEntity<ResponseUtil> login(LoginDTO loginDTO) {
//        User user = userRepo.findByEmail(loginDTO.getEmail()).orElseThrow(()->throw new RuntimeException("Invalid password"););
        User user = userRepo.findByEmail(loginDTO.getEmail());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseUtil(
                            500,
                            "User not found. Please check your email."
                    )
            );
        }

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
//            throw new RuntimeException("Invalid password");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseUtil(
                            500,
                            "Invalid password. Please check your password."
                    )
            );
        }

        String user_role = user.getClass().getSimpleName().toUpperCase(); // ADMIN, PLAYER, COACH
        String token = jwtUtil.generateToken(user.getUsername(), user.getEmail(), user_role);

        return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseUtil(
                200,
                "Login successful!",
                new LoginResponse(
                        "User with email "+user.getEmail()+" login successfully!",
                        token,
                        user.getUsername(),
                        user.getEmail(),
                        user_role
                )
            )
        );
    }
}
