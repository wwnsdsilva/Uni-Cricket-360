package com.nsbm.uni_cricket_360.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginResponseUtil {
    private String message;
    private String token;     // JWT token
    private String username;
    private String email;
    private String user_role;

}
