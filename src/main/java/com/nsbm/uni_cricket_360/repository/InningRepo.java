package com.nsbm.uni_cricket_360.repository;

import com.nsbm.uni_cricket_360.entity.Inning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InningRepo extends JpaRepository<Inning, Long> {

    @Query("SELECT i FROM Inning i WHERE i.match.home_team.id = :teamId OR i.match.away_team.id = :teamId")
    List<Inning> findByMatch_Teams(@Param("teamId") Long teamId);
}
