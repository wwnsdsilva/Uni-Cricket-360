package com.nsbm.uni_cricket_360.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nsbm.uni_cricket_360.enums.SessionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Entity
public class TrainingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private LocalDateTime date_time;
    private String title;
    private String venue;

    @Enumerated(EnumType.STRING)
    private SessionStatus status;

    /* // If want team-wide attendance
    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id", nullable = false)
    private Team team;*/

    // 🔹 Link back to Attendance
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("session")  // ✅ prevent Attendance → Session loop
    private List<Attendance> attendance = new ArrayList<>();
}
