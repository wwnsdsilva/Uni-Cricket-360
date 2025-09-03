package com.nsbm.uni_cricket_360.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class BowlingAverageDTO {
    private int totalRunsConceded;
    private int totalWickets;
    private Double bowlingAverage; // null if no wickets
}
