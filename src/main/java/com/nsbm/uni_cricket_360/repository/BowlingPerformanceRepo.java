package com.nsbm.uni_cricket_360.repository;

import com.nsbm.uni_cricket_360.entity.BowlingPerformance;
import com.nsbm.uni_cricket_360.entity.Inning;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BowlingPerformanceRepo extends JpaRepository<BowlingPerformance, Long> {

    List<BowlingPerformance> findByPlayer_Id(Long playerId);

    void deleteByInning(Inning inning);
}
