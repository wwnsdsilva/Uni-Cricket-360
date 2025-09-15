package com.nsbm.uni_cricket_360.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class LoginResponseUtil {
    private String message;
    private String access_token;     // JWT token
    private Long user_id;
    private String username;
    private String email;
    private String user_role;
    private Date expiresAt; // access token expiry time
    private String refresh_token;     // Refresh token

    public LoginResponseUtil(String message, String access_token, String username, String email, String user_role) {
        this.message = message;
        this.access_token = access_token;
        this.username = username;
        this.email = email;
        this.user_role = user_role;
    }

    public LoginResponseUtil(String message, String access_token, Long user_id, String username, String email, String user_role) {
        this.message = message;
        this.access_token = access_token;
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.user_role = user_role;
    }
}
