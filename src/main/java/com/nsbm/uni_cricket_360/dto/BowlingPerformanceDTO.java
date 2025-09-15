package com.nsbm.uni_cricket_360.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class BowlingPerformanceDTO {
    private Long id;
    private InningDTO inning;
//    private PlayerDTO player;
    private Long player_id;  // instead of PlayerDTO
    private double overs;
    private int balls_bowled;
    private int runs_conceded;
    private int wickets;
}
