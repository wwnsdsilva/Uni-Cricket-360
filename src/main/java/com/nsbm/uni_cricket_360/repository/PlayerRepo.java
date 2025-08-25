package com.nsbm.uni_cricket_360.repository;

import com.nsbm.uni_cricket_360.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepo extends JpaRepository<Player, Long> {
}
