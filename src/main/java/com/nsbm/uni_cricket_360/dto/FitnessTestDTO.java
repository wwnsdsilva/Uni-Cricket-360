package com.nsbm.uni_cricket_360.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class FitnessTestDTO {
    private Long id;
    private PlayerDTO player;
    private LocalDate date;
    private double sprintTime;
    private double beepLevel;
}
