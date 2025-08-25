package com.nsbm.uni_cricket_360.service.impl;

import com.nsbm.uni_cricket_360.dto.LoginDTO;
import com.nsbm.uni_cricket_360.entity.RefreshToken;
import com.nsbm.uni_cricket_360.entity.User;
import com.nsbm.uni_cricket_360.exception.InvalidCredentialsException;
import com.nsbm.uni_cricket_360.exception.NotFoundException;
import com.nsbm.uni_cricket_360.repository.UserRepo;
import com.nsbm.uni_cricket_360.service.AuthService;
import com.nsbm.uni_cricket_360.service.RefreshTokenService;
import com.nsbm.uni_cricket_360.util.JwtUtil;
import com.nsbm.uni_cricket_360.util.LoginResponseUtil;
import com.nsbm.uni_cricket_360.util.TokenResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Override
    public LoginResponseUtil login(LoginDTO req) {
        User user = userRepo.findByEmail(req.getEmail());

        if (user == null) {
            throw new NotFoundException("User not found. Please check your email.");
        }

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid password. Please check your password.");
        }

        String user_role = user.getUser_role(); // from discriminator
        String accessToken = jwtUtil.generateAccessToken(user.getUsername(), user.getEmail(), user_role);
        Date expiration = jwtUtil.extractExpiration(accessToken);

        RefreshToken refreshToken = refreshTokenService.createForUser(user.getId());

        return new LoginResponseUtil(
                "User with email " + user.getEmail() + " login successfully!",
                accessToken,
                user.getUsername(),
                user.getEmail(),
                user_role,
                expiration,
                refreshToken.getToken() // you won't return this in JSON if you use HttpOnly cookie (recommended)
        );
    }

    @Override
    public TokenResponseUtil refresh(String currentRefreshToken) {
        RefreshToken oldRt = refreshTokenService.verifyUsable(currentRefreshToken);

        User user = userRepo.findById(oldRt.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found."));

        String role = user.getUser_role();
        String newAccess = jwtUtil.generateAccessToken(user.getUsername(), user.getEmail(), role);
        Date expiration = jwtUtil.extractExpiration(newAccess);

        RefreshToken newRefreshToken = refreshTokenService.rotate(oldRt);

        return new TokenResponseUtil(newAccess, expiration, newRefreshToken.getToken());
    }

    @Override
    public void logout(String refreshTokenCookie) {
        refreshTokenService.revokeToken(refreshTokenCookie);
    }
}
