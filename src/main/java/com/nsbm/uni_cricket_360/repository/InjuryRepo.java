package com.nsbm.uni_cricket_360.repository;

import com.nsbm.uni_cricket_360.entity.Injury;
import com.nsbm.uni_cricket_360.enums.InjuryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InjuryRepo extends JpaRepository<Injury, Long> {
    List<Injury> findByPlayer_Id(Long playerId);
    Injury findByStatus(InjuryStatus status);
}
