package com.nsbm.uni_cricket_360.dto;

import com.nsbm.uni_cricket_360.enums.DismissalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class BattingPerformanceDTO {
    private Long id;
    private InningDTO inning;
    private PlayerDTO player;
    private int runs;
    private int balls_faced;
    private int fours;
    private int sixes;
    private DismissalType dismissal_type;
}
