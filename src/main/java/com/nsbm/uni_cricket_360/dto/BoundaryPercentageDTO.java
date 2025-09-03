package com.nsbm.uni_cricket_360.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class BoundaryPercentageDTO {
    private int totalRuns;
    private int boundaryRuns;
    private double boundaryPercentage;

}
