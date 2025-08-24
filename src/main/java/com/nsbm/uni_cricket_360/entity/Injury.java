package com.nsbm.uni_cricket_360.entity;

import com.nsbm.uni_cricket_360.enums.InjuryStatus;
import com.nsbm.uni_cricket_360.enums.InjuryType;
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
public class Injury {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id", referencedColumnName = "id", nullable = false)
    private Player player;

    @Enumerated(EnumType.STRING)
    private InjuryType injuryType;

    private LocalDate dateReported;
    private int recoveryDays;

    @Enumerated(EnumType.STRING)
    private InjuryStatus status;

}
