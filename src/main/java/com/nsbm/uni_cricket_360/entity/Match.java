package com.nsbm.uni_cricket_360.entity;

import com.nsbm.uni_cricket_360.enums.MatchStatus;
import com.nsbm.uni_cricket_360.enums.MatchType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToOne
    @JoinColumn(name = "home_team_id", referencedColumnName = "id")
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(name = "away_team_id", referencedColumnName = "id")
    private Team awayTeam;

    private LocalDateTime dateTime;
    private String venue;
    private int oversPerInning;

    @Enumerated(EnumType.STRING)
    private MatchStatus status;

    @Enumerated(EnumType.STRING)
    private MatchType matchType;

    private String result;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "scheduled_by", referencedColumnName = "id", nullable = false)
    private Admin scheduledBy;
}
