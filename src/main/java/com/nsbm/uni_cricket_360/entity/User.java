package com.nsbm.uni_cricket_360.entity;

import com.nsbm.uni_cricket_360.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Entity
public class User {

    @Id
    private Long id;
    private String username;
    private String email;
    private String password;
    private UserRole role;
}
