package com.nsbm.uni_cricket_360.repository;

import com.nsbm.uni_cricket_360.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepo extends JpaRepository<Team, Long> {
}
