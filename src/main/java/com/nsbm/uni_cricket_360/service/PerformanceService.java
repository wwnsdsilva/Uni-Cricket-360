package com.nsbm.uni_cricket_360.service;

import com.nsbm.uni_cricket_360.dto.*;
import com.nsbm.uni_cricket_360.enums.DismissalType;

import java.util.Map;

public interface PerformanceService {

    // ---------------- Batting ----------------

    BattingAverageDTO getBattingAverage(Long playerId);

    StrikeRateDTO getStrikeRate(Long playerId);

    BoundaryPercentageDTO getBoundaryPercentage(Long playerId);

    Map<DismissalType, Double> getDismissalAnalysis(Long playerId);

    // ---------------- Bowling ----------------

    BowlingAverageDTO getBowlingAverage(Long playerId);

    EconomyRateDTO getEconomyRate(Long playerId);


    // ---------------- Fielding ----------------

    FieldingStatsDTO getFieldingStats(Long playerId);

    FieldingStatsDTO getFieldingStatsPerMatch(Long playerId, Long matchId);

    RunOutStatsDTO getRunOutStats(Long playerId);

    RunOutStatsDTO getRunOutStatsPerMatch(Long playerId, Long matchId);

    // ---------------- Fitness ----------------

    SprintTimeAverageDTO getAverageSprintTime(Long playerId);

    BeepLevelAverageDTO getAverageBeepLevel(Long playerId);

    // ---------------- Injury Metrics ----------------

    InjuryImpactDTO getInjuryImpact(Long playerId);

    // ---------------- Team ----------------

    WinLossRatioDTO getWinLossRatio(Long teamId);

    NetRunRateDTO getNetRunRate(Long teamId);

    // ---------------- Training Attendance Metrics ----------------

    TrainingAttendancePercentageDTO getTrainingAttendance(Long playerId);

}
