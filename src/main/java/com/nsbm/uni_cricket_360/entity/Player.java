package com.nsbm.uni_cricket_360.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nsbm.uni_cricket_360.enums.BattingStyle;
import com.nsbm.uni_cricket_360.enums.BowlingStyle;
import com.nsbm.uni_cricket_360.enums.PlayerRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Entity
@DiscriminatorValue("PLAYER")
@PrimaryKeyJoinColumn(name = "id") //  // Player.id is also User.id
public class Player extends User {

    private String university_id;
    private String first_name;
    private String last_name;
    private String name;
    private LocalDate dob;
    private int age;
    private String contact;

    @Enumerated(EnumType.STRING)
    private PlayerRole player_role;

    private String image_url;

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id", nullable = false)
    private Team team;

    @Enumerated(EnumType.STRING)
    private BattingStyle batting_style;

    @Enumerated(EnumType.STRING)
    private BowlingStyle bowling_style;

    private int jersey_no;
    private LocalDate joined_date;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true/*, fetch = FetchType.EAGER*/)
    @JsonIgnoreProperties("player")
    private List<BattingPerformance> batting_performances = new ArrayList<>();

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true/*, fetch = FetchType.EAGER*/)
    @JsonIgnoreProperties("player")
    private List<BowlingPerformance> bowling_performances = new ArrayList<>();

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true/*, fetch = FetchType.EAGER*/)
    @JsonIgnoreProperties("player")
    private List<FieldingPerformance> fielding_performances = new ArrayList<>();

}
