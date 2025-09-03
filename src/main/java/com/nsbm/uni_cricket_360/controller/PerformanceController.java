package com.nsbm.uni_cricket_360.controller;

import com.nsbm.uni_cricket_360.dto.BowlingAverageDTO;
import com.nsbm.uni_cricket_360.service.PerformanceService;
import com.nsbm.uni_cricket_360.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/performance")
public class PerformanceController {

    @Autowired
    private PerformanceService performanceService;

    // -------- Batting --------
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/batting-average/{playerId}")
    public ResponseUtil getBattingAverage(@PathVariable Long playerId) {
        return new ResponseUtil(
                200,
                "Batting average calculated successfully!",
                performanceService.getBattingAverage(playerId) != null ? performanceService.getBattingAverage(playerId) : "No dismissals recorded for this player."
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/strike-rate/{playerId}")
    public ResponseUtil getStrikeRate(@PathVariable Long playerId) {
        return new ResponseUtil(
                200,
                "Strike rate calculated successfully!",
                performanceService.getStrikeRate(playerId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/boundary-percentage/{playerId}")
    public ResponseUtil getBoundaryPercentage(@PathVariable Long playerId) {
        return new ResponseUtil(
                200,
                "Boundary percentage calculated successfully!",
                performanceService.getBoundaryPercentage(playerId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/dismissal-analysis/{playerId}")
    public ResponseUtil getDismissalAnalysis(@PathVariable Long playerId) {
        return new ResponseUtil(
                200,
                "Dismissal analysed successfully!",
                performanceService.getDismissalAnalysis(playerId));
    }

    // -------- Bowling --------

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/bowling-average/{playerId}")
    public ResponseUtil getBowlingAverage(@PathVariable Long playerId) {
        BowlingAverageDTO dto = performanceService.getBowlingAverage(playerId);

        if (dto.getTotalWickets() == 0) {
            return new ResponseUtil(200, "No wickets taken", "N/A");
        }
        return new ResponseUtil(200, "Bowling average calculated successfully!", dto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/economy-rate/{playerId}")
    public ResponseUtil getEconomyRate(@PathVariable Long playerId) {
        return new ResponseUtil(
                200,
                "Economy rate calculated successfully!",
                performanceService.getEconomyRate(playerId));
    }

    // -------- Fielding --------

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/fielding-stats/{playerId}")
    public ResponseUtil getFieldingStats(@PathVariable Long playerId) {
        return new ResponseUtil(
                200,
                "Fielding stats calculated successfully!",
                performanceService.getFieldingStats(playerId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/fielding-stats/{playerId}/{matchId}")
    public ResponseUtil getFieldingStatsPerMatch(@PathVariable Long playerId, @PathVariable Long matchId) {
        return new ResponseUtil(
                200,
                "Fielding stats per match calculated successfully!",
                performanceService.getFieldingStatsPerMatch(playerId, matchId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/run-out-stats/{playerId}")
    public ResponseUtil getRunOutStats(@PathVariable Long playerId) {
        return new ResponseUtil(
                200,
                "Run out stats calculated successfully!",
                performanceService.getRunOutStats(playerId)
        );
    }

    @GetMapping("/run-out-stats/{playerId}/{matchId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseUtil getRunOutStatsPerMatch(@PathVariable Long playerId, @PathVariable Long matchId) {
        return new ResponseUtil(
                200,
                "Run outs per match calculated successfully!",
                performanceService.getRunOutStatsPerMatch(playerId, matchId)
        );
    }

   // -------- Fitness --------
    @GetMapping("/fitness/sprint-time/{playerId}")
    public ResponseUtil getAverageSprintTime(@PathVariable Long playerId) {
        return new ResponseUtil(
                200,
                "Average sprint time calculated successfully!",
                performanceService.getAverageSprintTime(playerId)
        );
    }

    @GetMapping("/fitness/beep-level/{playerId}")
    public ResponseUtil getAverageBeepLevel(@PathVariable Long playerId) {
        return new ResponseUtil(
                200,
                "Average beep level calculated successfully!",
                performanceService.getAverageBeepLevel(playerId)
        );
    }

    // ---------------- Injury Metrics ----------------

    @GetMapping("/fitness/injuries/{playerId}")
    public ResponseUtil getInjuryImpact(@PathVariable Long playerId) {
        return new ResponseUtil(
                200,
                "Injury impact calculated successfully!",
                performanceService.getInjuryImpact(playerId)
        );
    }

    // -------- Team --------
    @GetMapping("/team/win-loss/{teamId}")
    public ResponseUtil getWinLossRatio(@PathVariable Long teamId) {
        return new ResponseUtil(
                200,
                "Win loss ratio calculated successfully!",
                performanceService.getWinLossRatio(teamId)
        );
    }

    @GetMapping("/team/net-run-rate/{teamId}")
    public ResponseUtil getNetRunRate(@PathVariable Long teamId) {
        return new ResponseUtil(
                200,
                "Net run rate calculated successfully!",
                performanceService.getNetRunRate(teamId)
        );
    }

    @GetMapping("/player/attendance/{playerId}")
    public ResponseUtil getTrainingAttendance(@PathVariable Long playerId) {
        return new ResponseUtil(
                200,
                "OK",
                performanceService.getTrainingAttendance(playerId)
        );
    }
}

