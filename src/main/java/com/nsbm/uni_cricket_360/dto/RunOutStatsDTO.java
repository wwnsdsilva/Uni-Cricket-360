package com.nsbm.uni_cricket_360.dto;

import com.nsbm.uni_cricket_360.entity.Match;
import com.nsbm.uni_cricket_360.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class RunOutStatsDTO {
    private int directRunOuts;
    private int assistedRunOuts;
    private int totalRunOuts; // direct + assisted
    private PlayerDTO player;
    private MatchDTO match;


    public RunOutStatsDTO(int directRunOuts, int assistedRunOuts, int totalRunOuts) {
        this.directRunOuts = directRunOuts;
        this.assistedRunOuts = assistedRunOuts;
        this.totalRunOuts = totalRunOuts;
    }
}
