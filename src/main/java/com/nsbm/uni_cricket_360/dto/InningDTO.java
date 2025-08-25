package com.nsbm.uni_cricket_360.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class InningDTO {
    private Long id;
    private MatchDTO match;
    private TeamDTO batting_team;
    private int runs;
    private int wickets;
    private double overs;
}
