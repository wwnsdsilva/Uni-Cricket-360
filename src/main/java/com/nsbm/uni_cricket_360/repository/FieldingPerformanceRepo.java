package com.nsbm.uni_cricket_360.repository;

import com.nsbm.uni_cricket_360.entity.FieldingPerformance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FieldingPerformanceRepo extends JpaRepository<FieldingPerformance, Long> {
    List<FieldingPerformance> findByPlayer_Id(Long playerId);
}
