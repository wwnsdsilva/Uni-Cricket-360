package com.nsbm.uni_cricket_360.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class FieldingStatsDTO {

    private int totalCatches;
    private int totalDirectRunOuts;
    private int totalAssistedRunOuts;
    private int totalStumpings;
    private PlayerDTO player;
    private MatchDTO match;
}
