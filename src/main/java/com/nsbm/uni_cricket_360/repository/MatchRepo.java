package com.nsbm.uni_cricket_360.repository;

import com.nsbm.uni_cricket_360.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MatchRepo extends JpaRepository<Match, Long> {

    @Query("SELECT m FROM Match m WHERE m.home_team.id = :teamId OR m.away_team.id = :teamId")
    List<Match> findByTeam(@Param("teamId")Long teamId);

}
