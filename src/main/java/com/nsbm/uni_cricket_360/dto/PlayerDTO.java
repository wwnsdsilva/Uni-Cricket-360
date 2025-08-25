package com.nsbm.uni_cricket_360.dto;

import com.nsbm.uni_cricket_360.enums.PlayerRole;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Pattern;
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

    @Pattern(regexp = "^[0-9]{10}$", message = "Contact number must be exactly 10 digits")
    private String contact;

    @Enumerated(EnumType.STRING)
    private PlayerRole player_role;

    private String image_url;
    private TeamDTO team;

    public PlayerDTO (PlayerRole player_role) {
        this.player_role = player_role;
    }

}
