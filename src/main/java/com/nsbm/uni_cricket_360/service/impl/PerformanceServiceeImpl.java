package com.nsbm.uni_cricket_360.service.impl;

import com.nsbm.uni_cricket_360.service.PerformanceServicee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceServiceeImpl implements PerformanceServicee {

    // ---------------- Batting Metrics ----------------

    // Batting Average = Runs / Dismissals
    @Override
    public Double calculateBattingAverage(int totalRuns, int dismissals) {
        if (dismissals == 0) return null;
        return (double) totalRuns / dismissals;
    }

    // Strike Rate = (Runs / Balls) * 100
    @Override
    public double calculateStrikeRate(int runs, int balls) {
        return balls > 0 ? ((double) runs / balls) * 100 : 0;
    }

    // Boundary % = (Boundary Runs / Total Runs) * 100
    @Override
    public double calculateBoundaryPercentage(int fours, int sixes, int totalRuns) {
        int boundaryRuns = (fours * 4) + (sixes * 6);
        return totalRuns > 0 ? ((double) boundaryRuns / totalRuns) * 100 : 0;
    }

    // ---------------- Bowling Metrics ----------------

    // Bowling Average = Runs Conceded / Wickets
    @Override
    public Double calculateBowlingAverage(int runsConceded, int wickets) {
        if (wickets == 0) return null;
        return (double) runsConceded / wickets;
    }

    // Economy Rate = Runs Conceded / Overs
    @Override
    public double calculateEconomyRate(int runsConceded, String oversBowled) {
        double decimalOvers = convertOversToDecimal(oversBowled);
        return decimalOvers > 0 ? runsConceded / decimalOvers : 0;
    }

    // Strike Rate (bowling) = balls bowled per wicket
    @Override
    public Double bowlingStrikeRate(int ballsBowled, int wickets) {
        return wickets == 0 ? null : (double) ballsBowled / wickets;
    }

    // ---------------- Fielding Metrics ----------------

    // Fielding contribution = catches + runouts + stumpings
    @Override
    public int fieldingContribution(int catches, int runOuts, int stumpings) {
        return catches + runOuts + stumpings;
    }

    // Simple count metrics (extendable later for ratios)
    @Override
    public int totalCatches(int catches) {
        return catches;
    }

    @Override
    public int totalRunOuts(int runOuts) {
        return runOuts;
    }

    // ---------------- Fitness Metrics ----------------

    // Beep Test Progress (just return level difference)
    @Override
    public int calculateBeepTestProgress(int previousLevel, int currentLevel) {
        return currentLevel - previousLevel;
    }

    // Sprint Improvement (lower is better)
    @Override
    public double calculateSprintImprovement(double previousTime, double currentTime) {
        return previousTime - currentTime;
    }

    // Average Sprint Time
    @Override
    public double avgSprintTime(List<Double> sprintTimes) {
        return sprintTimes.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    // Max Beep Level
    @Override
    public double maxBeepLevel(List<Double> beepLevels) {
        return beepLevels.stream().mapToDouble(Double::doubleValue).max().orElse(0.0);
    }

    // ---------------- Injury Metrics ----------------

    @Override
    public String injuryStatus(int recoveryDaysLeft) {
        return recoveryDaysLeft > 0 ? "Injured" : "Recovered";
    }

    @Override
    public double injuryImpact(int totalDaysUnavailable, int totalSeasonDays) {
        return totalSeasonDays > 0 ? ((double) totalDaysUnavailable / totalSeasonDays) * 100 : 0;
    }

    // ---------------- Team Metrics ----------------

    // Win/Loss Ratio = Wins / (Wins + Losses)
    @Override
    public double calculateWinLossRatio(int wins, int losses) {
        return (wins + losses) > 0 ? (double) wins / (wins + losses) : 0;
    }

    // Net Run Rate = (Runs Scored / Overs Faced) - (Runs Conceded / Overs Bowled)

    public double calculateNetRunRate(int runsScored, String oversFaced, int runsConceded, String oversBowled) {
        double rsPerOver = (double) runsScored / convertOversToDecimal(oversFaced);
        double rcPerOver = (double) runsConceded / convertOversToDecimal(oversBowled);
        return rsPerOver - rcPerOver;
    }

    // ---------------- Attendance Metrics ----------------

    // Attendance % = (Present / Total Sessions) * 100
    @Override
    public double calculateTrainingAttendanceRate(int sessionsAttended, int totalSessions) {
        return totalSessions > 0 ? ((double) sessionsAttended / totalSessions) * 100 : 0;
    }

    private double convertOversToDecimal(String overs) {
        // e.g. "25.3" → 25 + 3/6 = 25.5
        String[] parts = overs.split("\\.");
        int wholeOvers = Integer.parseInt(parts[0]);
        int balls = parts.length > 1 ? Integer.parseInt(parts[1]) : 0;
        return wholeOvers + (balls / 6.0);
    }
}
