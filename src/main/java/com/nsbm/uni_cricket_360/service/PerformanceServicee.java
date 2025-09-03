package com.nsbm.uni_cricket_360.service;

import java.util.List;

public interface PerformanceServicee {

    // ---------------- Batting Metrics ----------------

    Double calculateBattingAverage(int totalRuns, int dismissals);

    double calculateStrikeRate(int runs, int balls);

    double calculateBoundaryPercentage(int fours, int sixes, int totalRuns);

    // ---------------- Bowling Metrics ----------------

    Double calculateBowlingAverage(int runsConceded, int wickets);

    double calculateEconomyRate(int runsConceded, String oversBowled);

    Double bowlingStrikeRate(int ballsBowled, int wickets);

    // ---------------- Fielding Metrics ----------------

    int fieldingContribution(int catches, int runOuts, int stumpings);

    int totalCatches(int catches);

    int totalRunOuts(int runOuts);

    // ---------------- Fitness Metrics ----------------

    int calculateBeepTestProgress(int previousLevel, int currentLevel);

    double calculateSprintImprovement(double previousTime, double currentTime);

    double avgSprintTime(List<Double> sprintTimes);

    double maxBeepLevel(List<Double> beepLevels);

    // ---------------- Injury Metrics ----------------

    String injuryStatus(int recoveryDaysLeft);

    double injuryImpact(int totalDaysUnavailable, int totalSeasonDays);

    // ---------------- Team Metrics ----------------

    double calculateWinLossRatio(int wins, int losses);

    double calculateNetRunRate(int runsScored, String oversFaced, int runsConceded, String oversBowled);

    // ---------------- Attendance Metrics ----------------

    double calculateTrainingAttendanceRate(int sessionsAttended, int totalSessions);
}
