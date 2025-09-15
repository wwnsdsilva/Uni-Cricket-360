package com.nsbm.uni_cricket_360.repository;

import com.nsbm.uni_cricket_360.entity.FieldingPerformance;
import com.nsbm.uni_cricket_360.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FieldingPerformanceRepo extends JpaRepository<FieldingPerformance, Long> {
    List<FieldingPerformance> findByPlayer_Id(Long playerId);
    Optional<FieldingPerformance> findByPlayer_IdAndMatch_Id(Long playerId, Long matchId);

    void deleteByMatch(Match match);

}
