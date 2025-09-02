package com.nsbm.uni_cricket_360.repository;

import com.nsbm.uni_cricket_360.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepo extends JpaRepository<Match, Long> {
}
