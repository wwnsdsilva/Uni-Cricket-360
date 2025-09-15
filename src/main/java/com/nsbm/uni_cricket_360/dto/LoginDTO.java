package com.nsbm.uni_cricket_360.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class LoginDTO {
    private String username;
    private String email;
    private String password;
    private String user_role;

    public LoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginDTO(String username, String password, String user_role) {
        this.username = username;
        this.password = password;
        this.user_role = user_role;
    }
}
