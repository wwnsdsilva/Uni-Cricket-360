package com.nsbm.uni_cricket_360.repository;

import com.nsbm.uni_cricket_360.entity.BattingPerformance;
import com.nsbm.uni_cricket_360.entity.Inning;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BattingPerformanceRepo extends JpaRepository<BattingPerformance, Long> {

    List<BattingPerformance> findByPlayer_Id(Long playerId);

    void deleteByInning(Inning inning);

}
