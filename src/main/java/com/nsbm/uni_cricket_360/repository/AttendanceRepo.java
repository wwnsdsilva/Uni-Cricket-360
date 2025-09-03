package com.nsbm.uni_cricket_360.repository;

import com.nsbm.uni_cricket_360.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepo extends JpaRepository<Attendance, Long> {
    List<Attendance> findByPlayer_Id(Long playerId);
    List<Attendance> findBySession_Id(Long sessionId);

//    If want team-wide attendance
//    List<Attendance> findBySession_Team_Id(Long teamId);
}