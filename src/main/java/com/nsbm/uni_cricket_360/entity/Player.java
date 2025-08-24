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

    private String firstName;
    private String lastName;
    private LocalDate dob;
    private int age;
    private int contact;

    @Enumerated(EnumType.STRING)
    private PlayerRole role;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id", nullable = false)
    private Team team;

}
