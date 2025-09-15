package com.nsbm.uni_cricket_360.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class UserDTO {
    private Long id;

    @NotBlank(message = "Username is required")
    private String username;

    @Email(message = "Invalid email")
//    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    // keep password only for incoming requests
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotBlank(message = "User role is required")
    private String user_role;

    // Only required if user_role is ADMIN
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String admin_key;

    public UserDTO(Long id) {
        this.id = id;
    }

    public UserDTO(@NotBlank(message = "User role is required") String user_role) {
        this.user_role = user_role;
    }

    public UserDTO(@NotBlank(message = "Username is required") String username, @NotBlank(message = "Password is required") @Size(min = 6, message = "Password must be at least 6 characters") String password) {
        this.username = username;
        this.password = password;
    }

    public UserDTO(@NotBlank(message = "Username is required") String username, @Email(message = "Invalid email") @NotBlank(message = "Email is required") String email, @NotBlank(message = "Password is required") @Size(min = 6, message = "Password must be at least 6 characters") String password, @NotBlank(message = "User role is required") String user_role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.user_role = user_role;
    }
}
