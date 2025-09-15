package com.nsbm.uni_cricket_360.dto;

import com.nsbm.uni_cricket_360.enums.BattingStyle;
import com.nsbm.uni_cricket_360.enums.BowlingStyle;
import com.nsbm.uni_cricket_360.enums.PlayerRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class PlayerUpdateDTO {

    private String university_id;
    private String first_name;
    private String last_name;
    private String name;
    private LocalDate dob;
    private int age;

    @Pattern(regexp = "^[0-9]{10}$", message = "Contact number must be exactly 10 digits")
    private String contact;

    private PlayerRole player_role;
    private TeamDTO team;

    @Enumerated(EnumType.STRING)
    private BattingStyle batting_style;

    @Enumerated(EnumType.STRING)
    private BowlingStyle bowling_style;

    private int jersey_no;
    private LocalDate joined_date;
}