package com.nsbm.uni_cricket_360.dto;

import com.nsbm.uni_cricket_360.enums.BattingStyle;
import com.nsbm.uni_cricket_360.enums.BowlingStyle;
import com.nsbm.uni_cricket_360.enums.PlayerRole;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Data
@ToString
public class PlayerDTO extends UserDTO {

    private String university_id;
    private String first_name;
    private String last_name;
    private String name;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dob;

    private int age;

    @Pattern(regexp = "^[0-9]{10}$", message = "Contact number must be exactly 10 digits")
    private String contact;

    @Enumerated(EnumType.STRING)
    private PlayerRole player_role;

    private String image_url;
    private TeamDTO team;

    @Enumerated(EnumType.STRING)
    private BattingStyle batting_style;

    @Enumerated(EnumType.STRING)
    private BowlingStyle bowling_style;

    private int jersey_no;
    private LocalDate joined_date;

    private List<BattingPerformanceDTO> batting_performances;
    private List<BowlingPerformanceDTO> bowling_performances;
    private List<FieldingPerformanceDTO> fielding_performances;

    public PlayerDTO (PlayerRole player_role) {
        this.player_role = player_role;
    }

    public PlayerDTO(String university_id, String first_name, String last_name) {
        this.university_id = university_id;
        this.first_name = first_name;
        this.last_name = last_name;
    }
}
