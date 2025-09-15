package com.nsbm.uni_cricket_360.repository;

import com.nsbm.uni_cricket_360.entity.TrainingSession;
import com.nsbm.uni_cricket_360.enums.SessionStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainingSessionRepo extends JpaRepository<TrainingSession, Long> {

    List<TrainingSession> findAllByStatus(SessionStatus status);

    // Option A: EntityGraph (clean & recommended)
    @EntityGraph(attributePaths = {"attendance", "attendance.player"})
    List<TrainingSession> findAll();

    // Option B: Explicit JPQL fetch join
    @Query("SELECT DISTINCT ts FROM TrainingSession ts " +
            "LEFT JOIN FETCH ts.attendance a " +
            "LEFT JOIN FETCH a.player")
    List<TrainingSession> findAllWithAttendance();
}
