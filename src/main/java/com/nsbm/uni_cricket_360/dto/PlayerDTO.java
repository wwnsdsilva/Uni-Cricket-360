package com.nsbm.uni_cricket_360.dto;

import com.nsbm.uni_cricket_360.enums.PlayerRole;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@Data
@ToString
public class PlayerDTO extends UserDTO {
    private String first_name;
    private String last_name;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dob;

    private int age;
    private String contact;
    private PlayerRole player_role;
    private String image_url;
    private TeamDTO team;
}
