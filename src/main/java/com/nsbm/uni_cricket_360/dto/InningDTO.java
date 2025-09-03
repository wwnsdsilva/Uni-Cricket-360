package com.nsbm.uni_cricket_360.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class InningDTO {
    private Long id;
    private MatchDTO match;
    private TeamDTO batting_team;
    private int runs;
    private int wickets;

    // private double overs;

    /*
     * Store total balls bowled instead of overs (to avoid decimal errors).
     * Example:
     * 19.3 overs = 19 * 6 + 3 = 117 balls
     */
    private int balls;

    // 🔹 Utility method to convert balls -> overs in decimal
    public double getOversDecimal() {
        return balls / 6 + (balls % 6) / 6.0;
    }

    // 🔹 Utility method to display overs in cricket notation (e.g., 19.3)
    public String getOversCricketFormat() {
        return (balls / 6) + "." + (balls % 6);
    }
}
