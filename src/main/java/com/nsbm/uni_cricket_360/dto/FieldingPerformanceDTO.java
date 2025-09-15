package com.nsbm.uni_cricket_360.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class FieldingPerformanceDTO {
    private Long id;
    private MatchDTO match;
//    private PlayerDTO player;
    private Long player_id;  // instead of PlayerDTO
    private int catches;

    // private int run_outs;

    // 🔹 Split run-outs into direct & assisted
    private int direct_run_outs;    // direct hits
    private int assisted_run_outs;  // throws/assists

    private int stumpings;
}
