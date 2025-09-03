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
    public ResponseUtil battingAverage(@PathVariable Long playerId) {
        return new ResponseUtil(
                200,
                "OK",
                performanceService.getBattingAverage(playerId) != null ? performanceService.getBattingAverage(playerId) : "No dismissals recorded for this player."
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/strike-rate/{playerId}")
    public ResponseUtil strikeRate(@PathVariable Long playerId) {
        return new ResponseUtil(
                200,
                "OK",
                performanceService.getStrikeRate(playerId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/boundary-percentage/{playerId}")
    public ResponseUtil boundaryPercentage(@PathVariable Long playerId) {
        return new ResponseUtil(
                200,
                "OK",
                performanceService.getBoundaryPercentage(playerId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/dismissal-analysis/{playerId}")
    public ResponseUtil dismissalAnalysis(@PathVariable Long playerId) {
        return new ResponseUtil(
                200,
                "OK",
                performanceService.getDismissalAnalysis(playerId));
    }

    // -------- Bowling --------

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/bowling-average/{playerId}")
    public ResponseUtil bowlingAverage(@PathVariable Long playerId) {
        BowlingAverageDTO dto = performanceService.getBowlingAverage(playerId);

        if (dto.getTotalWickets() == 0) {
            return new ResponseUtil(200, "OK", "No wickets taken - N/A");
        }
        return new ResponseUtil(200, "OK", dto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/economy-rate/{playerId}")
    public ResponseUtil economyRate(@PathVariable Long playerId) {
        return new ResponseUtil(
                200,
                "OK",
                performanceService.getEconomyRate(playerId));
    }

    // -------- Fielding --------

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/fielding-stats/{playerId}")
    public ResponseUtil fieldingStats(@PathVariable Long playerId) {
        return new ResponseUtil(
                200,
                "OK",
                performanceService.getFieldingStats(playerId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/fielding-stats/{playerId}/{matchId}")
    public ResponseUtil fieldingStatsPerMatch(@PathVariable Long playerId, @PathVariable Long matchId) {
        return new ResponseUtil(
                200,
                "OK",
                performanceService.fieldingStatsPerMatch(playerId, matchId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/run-out-stats/{playerId}")
    public ResponseUtil runOutStats(@PathVariable Long playerId) {
        return new ResponseUtil(
                200,
                "OK",
                performanceService.getRunOutStats(playerId)
        );
    }

    @GetMapping("/run-out-stats/{playerId}/{matchId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseUtil runOutStatsPerMatch(@PathVariable Long playerId, @PathVariable Long matchId) {
        return new ResponseUtil(
                200,
                "OK",
                performanceService.getRunOutStatsPerMatch(playerId, matchId)
        );
    }

    /*// -------- Fitness --------
    @GetMapping("/fitness/sprint-time/{playerId}")
    public ResponseEntity<ResponseUtil> avgSprintTime(@PathVariable Long playerId) {
        return ResponseEntity.ok(new ResponseUtil(200, "OK", performanceService.getAverageSprintTime(playerId)));
    }

    @GetMapping("/fitness/beep-level/{playerId}")
    public ResponseEntity<ResponseUtil> avgBeepLevel(@PathVariable Long playerId) {
        return ResponseEntity.ok(new ResponseUtil(200, "OK", performanceService.getAverageBeepLevel(playerId)));
    }

    @GetMapping("/fitness/injuries/{playerId}")
    public ResponseEntity<ResponseUtil> injuryImpact(@PathVariable Long playerId) {
        return ResponseEntity.ok(new ResponseUtil(200, "OK", performanceService.getInjuryImpact(playerId)));
    }

    // -------- Team --------
    @GetMapping("/team/win-loss/{teamId}")
    public ResponseEntity<ResponseUtil> winLossRatio(@PathVariable Long teamId) {
        return ResponseEntity.ok(new ResponseUtil(200, "OK", performanceService.getWinLossRatio(teamId)));
    }

    @GetMapping("/team/net-run-rate/{teamId}")
    public ResponseEntity<ResponseUtil> netRunRate(@PathVariable Long teamId) {
        return ResponseEntity.ok(new ResponseUtil(200, "OK", performanceService.getNetRunRate(teamId)));
    }

    @GetMapping("/team/attendance/{teamId}")
    public ResponseEntity<ResponseUtil> trainingAttendance(@PathVariable Long teamId) {
        return ResponseEntity.ok(new ResponseUtil(200, "OK", performanceService.getTrainingAttendance(teamId)));
    }*/
}

