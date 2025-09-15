package com.nsbm.uni_cricket_360.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nsbm.uni_cricket_360.enums.AttendanceStatus;
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
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id", referencedColumnName = "id", nullable = false)
    private Player player;

    @ManyToOne
    @JoinColumn(name = "session_id", referencedColumnName = "id", nullable = false)
    @JsonIgnoreProperties("attendance")  // ✅ prevent Session → Attendance loop
    private TrainingSession session;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;
}
