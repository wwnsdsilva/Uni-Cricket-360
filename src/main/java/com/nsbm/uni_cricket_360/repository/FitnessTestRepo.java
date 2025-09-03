package com.nsbm.uni_cricket_360.repository;

import com.nsbm.uni_cricket_360.entity.FitnessTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FitnessTestRepo extends JpaRepository<FitnessTest, Long> {

    @Query("SELECT f FROM FitnessTest f WHERE f.player.id = :playerId")
    List<FitnessTest> findByPlayer(Long playerId);

}
