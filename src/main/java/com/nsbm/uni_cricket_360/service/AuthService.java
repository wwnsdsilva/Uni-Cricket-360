package com.nsbm.uni_cricket_360.service;

import com.nsbm.uni_cricket_360.dto.LoginDTO;
import com.nsbm.uni_cricket_360.util.ResponseUtil;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<ResponseUtil> login(LoginDTO loginDTO);
}
