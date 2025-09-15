package com.nsbm.uni_cricket_360.dto;

import com.nsbm.uni_cricket_360.enums.DismissalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class BattingPerformanceDTO {
    private Long id;
    private InningDTO inning;
//    private PlayerDTO player;
    private Long player_id;  // instead of PlayerDTO
    private int runs;
    private int balls_faced;
    private int fours;
    private int sixes;

    @Enumerated(EnumType.STRING)
    private DismissalType dismissal_type;
}
