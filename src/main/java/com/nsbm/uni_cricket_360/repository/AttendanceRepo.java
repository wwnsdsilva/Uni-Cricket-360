package com.nsbm.uni_cricket_360.repository;

import com.nsbm.uni_cricket_360.entity.Attendance;
import com.nsbm.uni_cricket_360.entity.Player;
import com.nsbm.uni_cricket_360.entity.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AttendanceRepo extends JpaRepository<Attendance, Long> {
    List<Attendance> findByPlayer_Id(Long playerId);
    List<Attendance> findBySession_Id(Long sessionId);

    Optional<Attendance> findAttendanceBySessionAndPlayer(TrainingSession session, Player player);

    /*// Find attendance records for a team (via training sessions linked to team) - not linked to teams
    @Query("SELECT a FROM Attendance a WHERE a.session.id IN " +
            "(SELECT ts.id FROM TrainingSession ts WHERE ts.team.id = :teamId)")
    List<Attendance> findByTeamId(@Param("teamId") Long teamId);*/
}