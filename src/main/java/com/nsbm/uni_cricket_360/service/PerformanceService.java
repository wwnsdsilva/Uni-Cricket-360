package com.nsbm.uni_cricket_360.service;

import com.nsbm.uni_cricket_360.dto.BattingAverageDTO;
import com.nsbm.uni_cricket_360.dto.BoundaryPercentageDTO;
import com.nsbm.uni_cricket_360.dto.StrikeRateDTO;
import com.nsbm.uni_cricket_360.enums.DismissalType;

import java.util.Map;

public interface PerformanceService {

    // ---------------- Batting ----------------

    BattingAverageDTO getBattingAverage(Long playerId);

    StrikeRateDTO getStrikeRate(Long playerId);

    BoundaryPercentageDTO getBoundaryPercentage(Long playerId);

    Map<DismissalType, Double> getDismissalAnalysis(Long playerId);
//
//    // ---------------- Bowling ----------------
//
//    Double getBowlingAverage(Long playerId);
//
//    Double getEconomyRate(Long playerId);
//
//    // ---------------- Fielding ----------------
//
//    Integer getFieldingContribution(Long playerId);
//
//    // ---------------- Fitness ----------------
//
//    Double getAverageSprintTime(Long playerId);
//
//    Double getAverageBeepLevel(Long playerId);
//
//    // ---------------- Injury Metrics ----------------
//
//    Map<String, Object> getInjuryImpact(Long playerId);
//
//    // ---------------- Team ----------------
//
//    Double getWinLossRatio(Long teamId);
//
//    Double getNetRunRate(Long teamId);
//
//    // ---------------- Training Attendance Metrics ----------------
//
//    Double getTrainingAttendance(Long teamId);

}
