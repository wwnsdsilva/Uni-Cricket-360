package com.nsbm.uni_cricket_360.controller;

import com.nsbm.uni_cricket_360.dto.LoginDTO;
import com.nsbm.uni_cricket_360.dto.UserDTO;
import com.nsbm.uni_cricket_360.service.AuthService;
import com.nsbm.uni_cricket_360.service.UserService;
import com.nsbm.uni_cricket_360.util.LoginResponseUtil;
import com.nsbm.uni_cricket_360.util.ResponseUtil;
import com.nsbm.uni_cricket_360.util.TokenResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.Duration;

@RestController
@CrossOrigin
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    private UserService userService;

    @Value("${refresh-token.cookie-name}")
    private String cookieName;

    @Value("${refresh.token.ttl}")
    private Duration refreshTtl;

    @Value("${refresh-token.cookie-domain}")
    private String cookieDomain;

    @Value("${refresh-token.cookie-secure}")
    private boolean cookieSecure;

    @PostMapping("/register")
    public ResponseEntity<ResponseUtil> register(@Valid @RequestBody UserDTO dto) {
        try {
            UserDTO savedUser = userService.saveUser(dto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseUtil(201, "User registered successfully!", savedUser));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseUtil(400, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseUtil(500, "Registration failed!", null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseUtil> login(@RequestBody LoginDTO req, HttpServletResponse resp) {
        LoginResponseUtil res;
        try {
            res = authService.login(req);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseUtil(401, ex.getMessage(), null));
        }

        // Set HttpOnly refresh cookie
        ResponseCookie cookie = ResponseCookie.from(cookieName, res.getRefresh_token())
                .httpOnly(true)
                .secure(cookieSecure)
                .path("/api/v1/auth")         // limit path to refresh/logout endpoints
                .domain(cookieDomain)         // set your domain in prod
                .maxAge(Duration.ofDays(refreshTtl.toDays()))
                .sameSite("Strict")           // or Lax/None (if cross-site + HTTPS)
                .build();
        resp.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        // Do NOT include refresh_token in body in production
        res.setRefresh_token(null);

        return ResponseEntity.ok(new ResponseUtil(200, "Login successful!", res));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ResponseUtil> refresh(
            @CookieValue(name = "${refresh-token.cookie-name}", required = false) String rtCookie,
            HttpServletResponse resp) {

        if (rtCookie == null || rtCookie.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseUtil(404, "Missing refresh token cookie", null));
        }

        try {
            TokenResponseUtil tr = authService.refresh(rtCookie);

            // rotate cookie with new refresh token
            ResponseCookie cookie = ResponseCookie.from(cookieName, tr.getRefresh_token())
                    .httpOnly(true)
                    .secure(cookieSecure)
                    .path("/api/v1/auth")
                    .domain(cookieDomain)
                    .maxAge(Duration.ofDays(refreshTtl.toDays()))
                    .sameSite("Strict")
                    .build();
            resp.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            // Do not expose refresh in body
            tr.setRefresh_token(null);

            return ResponseEntity.ok(new ResponseUtil(200, "Token refreshed successfully!", tr));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseUtil(401, e.getMessage(), null));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseUtil> logout(@CookieValue(name = "${refresh-token.cookie-name}", required = false) String refreshTokenCookie,
                                               HttpServletResponse resp) {
        if (refreshTokenCookie != null && !refreshTokenCookie.isEmpty()) {
            authService.logout(refreshTokenCookie);
        }

        // clear cookie
        ResponseCookie clear = ResponseCookie.from(cookieName, "")
                .httpOnly(true)
                .secure(cookieSecure)
                .path("/api/v1/auth")
                .domain(cookieDomain)
                .maxAge(0)
                .sameSite("Strict")
                .build();
        resp.addHeader(HttpHeaders.SET_COOKIE, clear.toString());

        return ResponseEntity.ok(new ResponseUtil(200, "User logged out successfully!", null));
    }
}
