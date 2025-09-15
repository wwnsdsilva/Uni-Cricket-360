package com.nsbm.uni_cricket_360.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class MatchStatsDTO {

    private MatchDTO match;
    private InningDTO inning;
    private List<BattingPerformanceDTO> batting_stats;
    private List<BowlingPerformanceDTO> bowling_stats;
    private List<FieldingPerformanceDTO> fielding_stats;
}
