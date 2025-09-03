package com.nsbm.uni_cricket_360.repository;

import com.nsbm.uni_cricket_360.entity.FitnessTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FitnessTestRepo extends JpaRepository<FitnessTest, Long> {
    List<FitnessTest> findByPlayer_Id(Long playerId);
}
