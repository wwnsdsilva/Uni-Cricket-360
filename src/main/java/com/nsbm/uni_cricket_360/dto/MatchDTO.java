package com.nsbm.uni_cricket_360.dto;

import com.nsbm.uni_cricket_360.enums.MatchStatus;
import com.nsbm.uni_cricket_360.enums.MatchType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class MatchDTO {
    private Long id;
    private String description;
    private TeamDTO homeTeam;
    private TeamDTO awayTeam;
    private LocalDateTime dateTime;
    private String venue;
    private int oversPerInning;
    private MatchStatus status;
    private MatchType matchType;
    private String result;
    private String imageUrl;
    private AdminDTO scheduledBy;
}
