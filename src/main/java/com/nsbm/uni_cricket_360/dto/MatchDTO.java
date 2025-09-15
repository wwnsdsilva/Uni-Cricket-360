package com.nsbm.uni_cricket_360.dto;

import com.nsbm.uni_cricket_360.entity.Team;
import com.nsbm.uni_cricket_360.enums.MatchResult;
import com.nsbm.uni_cricket_360.enums.MatchStatus;
import com.nsbm.uni_cricket_360.enums.MatchType;
import com.nsbm.uni_cricket_360.enums.Toss;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class MatchDTO {
    private Long id;
    private String description;
//    private TeamDTO home_team;
//    private TeamDTO away_team;
    private TeamDTO opponent;
    private LocalDateTime date_time;
    private String venue;
    private int overs_per_inning;

    @Enumerated(EnumType.STRING)
    private MatchStatus status;

    @Enumerated(EnumType.STRING)
    private MatchType match_type;

    @Enumerated(EnumType.STRING)
    private MatchResult result;

    private String image_url;
    private AdminDTO scheduled_by;

    private String score;
    private String opponent_score;

//    @Enumerated(EnumType.STRING)
//    private Toss toss;
}

