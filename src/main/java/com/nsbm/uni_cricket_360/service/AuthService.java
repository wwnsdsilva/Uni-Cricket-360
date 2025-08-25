package com.nsbm.uni_cricket_360.service;

import com.nsbm.uni_cricket_360.dto.LoginDTO;
import com.nsbm.uni_cricket_360.util.LoginResponseUtil;
import com.nsbm.uni_cricket_360.util.ResponseUtil;
import com.nsbm.uni_cricket_360.util.TokenResponseUtil;
import org.springframework.http.ResponseEntity;

public interface AuthService {
//    ResponseEntity<ResponseUtil> login(LoginDTO loginDTO);
    LoginResponseUtil login(LoginDTO loginDTO);

    TokenResponseUtil refresh(String currentRefreshToken);

    void logout(String refreshTokenCookie);
}
