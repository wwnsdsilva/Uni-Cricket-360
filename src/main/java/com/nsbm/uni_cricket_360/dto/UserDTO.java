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
public class UserDTO {
    private Long id;
    private String username;
    private String email;

    // keep password only for incoming requests
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String user_role;
}
