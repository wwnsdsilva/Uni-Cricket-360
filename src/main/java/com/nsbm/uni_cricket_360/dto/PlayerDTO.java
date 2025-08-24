package com.nsbm.uni_cricket_360.dto;

import com.nsbm.uni_cricket_360.enums.PlayerRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class PlayerDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private int age;
    private int contact;
    private PlayerRole role;
    private String imageUrl;
    private TeamDTO team;
}
