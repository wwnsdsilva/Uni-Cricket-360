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
public class BowlingPerformance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "inning_id", referencedColumnName = "id", nullable = false)
    private Inning inning;

    @ManyToOne
    @JoinColumn(name = "player_id", referencedColumnName = "id", nullable = false)
    private Player player;

    /*
    * Cricket overs = 6 balls.
    * Store as int ballsBowled.
    * Example: 25.3 overs = 25 x 6 + 3 = 153 balls
    *
    * Store only total balls instead of overs
    * */
    private int balls_bowled;

    /*
     * Then compute overs only when needed:
     * double oversDecimal = balls_bowled / 6.0;
     * */
    private double overs;

    private int runs_conceded;
    private int wickets;

    /*
    * Utility method to convert balls -> overs in decimal
    * Example: ballsBowled = 27
    * 27 / 6 = 4 full overs
    * 27 % 6 = 3 balls
    * 3 / 6.0 = 0.5
    * Result = 4.5 overs (decimal)
    * */
    public double getOversInDecimal() {
        return balls_bowled / 6 + (balls_bowled % 6) / 6.0;
    }

    /*
    * Utility method to display overs in cricket notation (e.g., 25.3)
    * */
    public String getOversInCricketFormat() {
        return (balls_bowled / 6) + "." + (balls_bowled % 6);
    }
}
