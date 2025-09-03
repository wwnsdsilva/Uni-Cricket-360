package com.nsbm.uni_cricket_360.service.impl;

import com.nsbm.uni_cricket_360.dto.*;
import com.nsbm.uni_cricket_360.entity.*;
import com.nsbm.uni_cricket_360.enums.DismissalType;
import com.nsbm.uni_cricket_360.exception.NotFoundException;
import com.nsbm.uni_cricket_360.repository.*;
import com.nsbm.uni_cricket_360.service.PerformanceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class PerformanceServiceImpl implements PerformanceService {

    @Autowired
    private BattingPerformanceRepo battingRepo;

    @Autowired
    private BowlingPerformanceRepo bowlingRepo;

    @Autowired
    private FieldingPerformanceRepo fieldingRepo;

    @Autowired
    private FitnessTestRepo fitnessRepo;

    @Autowired
    private InjuryRepo injuryRepo;

    @Autowired
    private PlayerRepo playerRepo;

    @Autowired
    private MatchRepo matchRepo;

    @Autowired
    private AttendanceRepo attendanceRepo;

    @Autowired
    private ModelMapper mapper;

    // ---------------- Batting ----------------
    @Override
    public BattingAverageDTO getBattingAverage(Long playerId) {

        // Fetch all batting performances for the player
        List<BattingPerformance> records = battingRepo.findByPlayer_Id(playerId);

        BattingAverageDTO battingAverageDTO = new BattingAverageDTO();

        // Sum total runs
        int totalRuns = records.stream()
                .mapToInt(BattingPerformance::getRuns)
                .sum();
        battingAverageDTO.setTotalRuns(totalRuns);

        // Count dismissals (dismissal_type != null)
        long dismissals = records.stream()
                .filter(bp -> bp.getDismissal_type() != DismissalType.NOTOUT)
                .count();
        battingAverageDTO.setDismissals(dismissals);

        // Calculate batting average
        if (dismissals > 0) {
            battingAverageDTO.setBattingAverage((double) totalRuns / dismissals);
            return battingAverageDTO;
        } else {
            return null; // "N/A" if never dismissed
        }
    }

    @Override
    public StrikeRateDTO getStrikeRate(Long playerId) {

        // Fetch all batting performances for the player
        List<BattingPerformance> records = battingRepo.findByPlayer_Id(playerId);

        StrikeRateDTO strikeRateDTO = new StrikeRateDTO();

        // Sum total runs and total balls faced across all innings
        int totalRuns = records.stream()
                .mapToInt(BattingPerformance::getRuns)
                .sum();
        strikeRateDTO.setTotalRuns(totalRuns);

        int totalBalls = records.stream()
                .mapToInt(BattingPerformance::getBalls_faced)
                .sum();
        strikeRateDTO.setTotalBalls(totalBalls);

        // Calculate strike rate
        double strikeRate = totalBalls > 0 ? ((double) totalRuns / totalBalls) * 100 : 0.0;
        strikeRateDTO.setStrikeRate(strikeRate);
        return strikeRateDTO;
    }

    @Override
    public BoundaryPercentageDTO getBoundaryPercentage(Long playerId) {
        List<BattingPerformance> records = battingRepo.findByPlayer_Id(playerId);

        BoundaryPercentageDTO boundaryPercentageDTO = new BoundaryPercentageDTO();

        // Sum total runs
        int totalRuns = records.stream()
                .mapToInt(BattingPerformance::getRuns)
                .sum();
        boundaryPercentageDTO.setTotalRuns(totalRuns);

        // Sum boundary runs (4s and 6s)
        int boundaryRuns = records.stream()
                .mapToInt(bp -> bp.getFours() * 4 + bp.getSixes() * 6)
                .sum();
        boundaryPercentageDTO.setBoundaryRuns(boundaryRuns);

        // Calculate boundary percentage
        // Edge Case: If total_runs = 0, set percentage = 0 to avoid division by zero.

        double boundaryPerc = totalRuns > 0 ? (boundaryRuns * 100.0) / totalRuns : 0.0;
        boundaryPercentageDTO.setBoundaryPercentage(boundaryPerc);
        return boundaryPercentageDTO;
    }

    @Override
    public Map<DismissalType, Double> getDismissalAnalysis(Long playerId) {
        List<BattingPerformance> records = battingRepo.findByPlayer_Id(playerId);

        // Filter out not-out innings
        Map<DismissalType, Long> counts = records.stream()
                .filter(bp -> bp.getDismissal_type() != DismissalType.NOTOUT)
                .collect(Collectors.groupingBy(BattingPerformance::getDismissal_type, Collectors.counting()));

        // Calculate total dismissals
        long totalDismissals = counts.values().stream().mapToLong(Long::longValue).sum();

        // Calculate percentage per dismissal type
        Map<DismissalType, Double> percentages = new HashMap<>();
        counts.forEach((type, count) -> percentages.put(type, (count * 100.0) / totalDismissals));

        return percentages;
    }

    // ---------------- Bowling ----------------
    @Override
    public BowlingAverageDTO getBowlingAverage(Long playerId) {
        List<BowlingPerformance> records = bowlingRepo.findByPlayer_Id(playerId);

        BowlingAverageDTO bowlingAverageDTO = new BowlingAverageDTO();

        int totalRunsConceded = records.stream()
                .mapToInt(BowlingPerformance::getRuns_conceded)
                .sum();
        bowlingAverageDTO.setTotalRunsConceded(totalRunsConceded);

        int totalWickets = records.stream()
                .mapToInt(BowlingPerformance::getWickets)
                .sum();
        bowlingAverageDTO.setTotalWickets(totalWickets);

        if (totalWickets > 0) {
            bowlingAverageDTO.setBowlingAverage((double) totalRunsConceded / totalWickets);
        } else {
            bowlingAverageDTO.setBowlingAverage(null); // controller will handle "N/A"
        }

        return bowlingAverageDTO;
    }

    @Override
    public EconomyRateDTO getEconomyRate(Long playerId) {
        List<BowlingPerformance> records = bowlingRepo.findByPlayer_Id(playerId);

        EconomyRateDTO economyRateDTO = new EconomyRateDTO();

        int runsConceded = records.stream().mapToInt(BowlingPerformance::getRuns_conceded).sum();
        int ballsBowled = records.stream().mapToInt(BowlingPerformance::getBalls_bowled).sum();

        double overs = ballsBowled / 6 + (ballsBowled % 6) / 6.0; // balls_bowled -> overs(decimal)
        double economyRate = overs > 0 ? runsConceded / overs : 0.0;

        economyRateDTO.setRunsConceded(runsConceded);
        economyRateDTO.setBallsBowled(ballsBowled);
        economyRateDTO.setOvers(overs);
        economyRateDTO.setEconomyRate(economyRate);

        return economyRateDTO;
    }

    // ---------------- Fielding ----------------
    @Override
    public FieldingStatsDTO getFieldingStats(Long playerId) {
        List<FieldingPerformance> records = fieldingRepo.findByPlayer_Id(playerId);

        FieldingStatsDTO fieldingStatsDTO = new FieldingStatsDTO();

        int totalCatches = records.stream().mapToInt(FieldingPerformance::getCatches).sum();
        int totalDirectRunOuts = records.stream().mapToInt(FieldingPerformance::getDirect_run_outs).sum();
        int totalAssistedRunOuts = records.stream().mapToInt(FieldingPerformance::getAssisted_run_outs).sum();
        int totalStumpings = records.stream().mapToInt(FieldingPerformance::getStumpings).sum();

        fieldingStatsDTO.setTotalCatches(totalCatches);
        fieldingStatsDTO.setTotalDirectRunOuts(totalDirectRunOuts);
        fieldingStatsDTO.setTotalAssistedRunOuts(totalAssistedRunOuts);
        fieldingStatsDTO.setTotalStumpings(totalStumpings);

        return fieldingStatsDTO;
    }

    @Override
    public FieldingStatsDTO fieldingStatsPerMatch(Long playerId, Long matchId) {
        // Fetch fielding performance for the specific player in the specific match
        FieldingPerformance performance = fieldingRepo
                .findByPlayer_IdAndMatch_Id(playerId, matchId)
                .orElseThrow(() -> new NotFoundException("No fielding performance found for this player in this match"));

        // Compute total run-outs for this match
        int totalRunOuts = performance.getDirect_run_outs() + performance.getAssisted_run_outs();

        Player player = playerRepo.findById(playerId).orElseThrow(() -> new NotFoundException("Player not found with id: " + playerId));
        Match match = matchRepo.findById(matchId).orElseThrow(() -> new NotFoundException("Match details not found with id: " + matchId));

        // Create DTO
        FieldingStatsDTO fieldingStatsDTO = new FieldingStatsDTO();
        fieldingStatsDTO.setTotalCatches(performance.getCatches());
        fieldingStatsDTO.setTotalDirectRunOuts(performance.getDirect_run_outs());
        fieldingStatsDTO.setTotalAssistedRunOuts(performance.getAssisted_run_outs());
        fieldingStatsDTO.setTotalStumpings(performance.getStumpings());
        fieldingStatsDTO.setPlayer(mapper.map(player, PlayerDTO.class));
        fieldingStatsDTO.setMatch(mapper.map(match, MatchDTO.class));

        return fieldingStatsDTO;
    }

    @Override
    public RunOutStatsDTO getRunOutStats(Long playerId) {
        // Fetch all fielding records for the player
        List<FieldingPerformance> records = fieldingRepo.findByPlayer_Id(playerId);

        // Aggregate direct & assisted
        int directRunOuts = records.stream().mapToInt(FieldingPerformance::getDirect_run_outs).sum();
        int assistedRunOuts = records.stream().mapToInt(FieldingPerformance::getAssisted_run_outs).sum();

        // Total = direct + assisted
        int totalRunOuts = directRunOuts + assistedRunOuts;

        // Wrap in DTO
        return new RunOutStatsDTO(directRunOuts, assistedRunOuts, totalRunOuts);
    }

    @Override
    public RunOutStatsDTO getRunOutStatsPerMatch(Long playerId, Long matchId) {
        FieldingPerformance performance = fieldingRepo
                .findByPlayer_IdAndMatch_Id(playerId, matchId)
                .orElseThrow(() -> new NotFoundException("No fielding performance found for this player in this match"));

        int totalRunOuts = performance.getDirect_run_outs() + performance.getAssisted_run_outs();

        Player player = playerRepo.findById(playerId).orElseThrow(() -> new NotFoundException("Player not found with id: " + playerId));
        Match match = matchRepo.findById(matchId).orElseThrow(() -> new NotFoundException("Match details not found with id: " + matchId));

        return new RunOutStatsDTO(
                performance.getDirect_run_outs(),
                performance.getAssisted_run_outs(),
                totalRunOuts,
                mapper.map(player, PlayerDTO.class),
                mapper.map(match, MatchDTO.class)
        );
    }

    // ---------------- Fitness ----------------
    @Override
    public SprintTimeAverageDTO getAverageSprintTime(Long playerId) {
        List<FitnessTest> tests = fitnessRepo.findByPlayer(playerId);
        if (tests.isEmpty()) throw new NotFoundException("Fitness test records not found for player with id: " + playerId); // or 0.0 if you prefer
        // return tests.stream().mapToDouble(FitnessTest::getSprint_time).average().orElse(0.0);
        return new SprintTimeAverageDTO(tests.stream().mapToDouble(FitnessTest::getSprint_time).average().orElse(0.0));

    }

    @Override
    public BeepLevelAverageDTO getAverageBeepLevel(Long playerId) {
        List<FitnessTest> tests = fitnessRepo.findByPlayer(playerId);
        if (tests.isEmpty()) throw new NotFoundException("Fitness test records not found for player with id: " + playerId); // or 0.0 if you prefer
        return new BeepLevelAverageDTO(tests.stream().mapToDouble(FitnessTest::getBeep_level).average().orElse(0.0));
    }

    // ---------------- Injury Metrics ----------------

    @Override
    public InjuryImpactDTO getInjuryImpact(Long playerId) {
        List<Injury> injuries = injuryRepo.findByPlayer_Id(playerId);
        int totalDays = injuries.stream().mapToInt(Injury::getRecovery_days).sum();

        return new InjuryImpactDTO(
                injuries.size(), // total injuries
                totalDays,       // total unavailable days
                injuries.stream().filter(i -> i.getStatus().toString().equals("INJURED")).count() // active injuries
        );
    }

    /*// ---------------- Team ----------------
    @Override
    public Double getWinLossRatio(Long teamId) {
        var matches = matchRepo.findByTeam(teamId);
        long wins = matches.stream().filter(m -> "WIN".equalsIgnoreCase(m.getResult())).count();
        long losses = matches.stream().filter(m -> "LOSS".equalsIgnoreCase(m.getResult())).count();
        return (wins + losses) > 0 ? (double) wins / (wins + losses) : 0.0;
    }

    @Override
    public Double getNetRunRate(Long teamId) {
        var matches = matchRepo.findByTeam(teamId);
        int runsScored = matches.stream().mapToInt(Match::getRunsScored).sum();
        int runsConceded = matches.stream().mapToInt(Match::getRunsConceded).sum();
        double oversFaced = matches.stream().mapToDouble(m -> convertOversToDecimal(m.getOversFaced())).sum();
        double oversBowled = matches.stream().mapToDouble(m -> convertOversToDecimal(m.getOversBowled())).sum();

        double rsPerOver = oversFaced > 0 ? runsScored / oversFaced : 0;
        double rcPerOver = oversBowled > 0 ? runsConceded / oversBowled : 0;
        return rsPerOver - rcPerOver;
    }

    @Override
    public Double getTrainingAttendance(Long teamId) {
        var records = attendanceRepo.findByTeamId(teamId);
        long attended = records.stream().filter(a -> a.getStatus() == AttendanceStatus.PRESENT).count();
        return records.size() > 0 ? (attended * 100.0) / records.size() : 0.0;
    }*/

    // ---------------- Helper ----------------
    private double convertOversToDecimal(double overs) {
        int whole = (int) overs;
        int balls = (int) Math.round((overs - whole) * 10); // e.g., 25.3 -> 3 balls
        return whole + (balls / 6.0);
    }
}
