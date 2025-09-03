package com.nsbm.uni_cricket_360.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Entity
public class Inning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "match_id", referencedColumnName = "id", nullable = false)
    private Match match;

    @ManyToOne
    @JoinColumn(name = "batting_team_id", referencedColumnName = "id", nullable = false)
    private Team batting_team;

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
