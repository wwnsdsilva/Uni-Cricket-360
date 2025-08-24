package com.nsbm.uni_cricket_360.entity;

import com.nsbm.uni_cricket_360.enums.DismissalType;
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
public class BattingPerformance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "inning_id", referencedColumnName = "id", nullable = false)
    private Inning inning;

    @ManyToOne
    @JoinColumn(name = "player_id", referencedColumnName = "id", nullable = false)
    private Player player;

    private int runs;
    private int ballsFaced;
    private int fours;
    private int sixes;

    @Enumerated(EnumType.STRING)
    private DismissalType dismissalType;
}
