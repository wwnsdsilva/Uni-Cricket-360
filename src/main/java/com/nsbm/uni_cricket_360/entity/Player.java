package com.nsbm.uni_cricket_360.entity;

import com.nsbm.uni_cricket_360.enums.PlayerRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Entity
@DiscriminatorValue("PLAYER")
@PrimaryKeyJoinColumn(name = "id") //  // Player.id is also User.id
public class Player extends User {

    private String first_name;
    private String last_name;
    private LocalDate dob;
    private int age;
    private String contact;

    @Enumerated(EnumType.STRING)
    private PlayerRole player_role;

    private String image_url;

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id", nullable = false)
    private Team team;

}
