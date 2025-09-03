package com.nsbm.uni_cricket_360.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class NetRunRateDTO {
    private int runsScored;
    private double oversFaced;
    private int runsConceded;
    private double oversBowled;
    private double runsScoredPerOver;
    private double runsConcededPerOver;
    private double netRunRate;
}
