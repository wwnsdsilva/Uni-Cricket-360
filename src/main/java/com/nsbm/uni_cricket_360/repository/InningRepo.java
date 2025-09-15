package com.nsbm.uni_cricket_360.repository;

import com.nsbm.uni_cricket_360.entity.Inning;
import com.nsbm.uni_cricket_360.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InningRepo extends JpaRepository<Inning, Long> {

    @Query("SELECT i FROM Inning i WHERE i.match.opponent.id = :teamId")
    List<Inning> findByMatch_Teams(@Param("teamId") Long teamId);

    List<Inning> findByMatch(Match match);
}
