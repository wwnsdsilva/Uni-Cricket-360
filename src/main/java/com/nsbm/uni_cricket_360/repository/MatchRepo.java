package com.nsbm.uni_cricket_360.repository;

import com.nsbm.uni_cricket_360.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MatchRepo extends JpaRepository<Match, Long> {

//    @Query("SELECT m FROM Match m WHERE m.homeTeam.id = :teamId OR m.awayTeam.id = :teamId")
//    List<Match> findByTeam(Long teamId);
}
