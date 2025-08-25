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
public class TokenResponseUtil {
    private String access_token;
    private Date access_token_expires_at;
    private String refresh_token; // omit in JSON if using HttpOnly cookie
}
