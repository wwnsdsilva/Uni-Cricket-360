package com.nsbm.uni_cricket_360.controller;

import com.nsbm.uni_cricket_360.service.PerformanceService;
import com.nsbm.uni_cricket_360.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    /*// -------- Bowling --------
    @GetMapping("/bowling-average/{playerId}")
    public ResponseEntity<ResponseUtil> bowlingAverage(@PathVariable Long playerId) {
        return ResponseEntity.ok(new ResponseUtil(200, "OK", performanceService.getBowlingAverage(playerId)));
    }

    @GetMapping("/economy-rate/{playerId}")
    public ResponseEntity<ResponseUtil> economyRate(@PathVariable Long playerId) {
        return ResponseEntity.ok(new ResponseUtil(200, "OK", performanceService.getEconomyRate(playerId)));
    }

    // -------- Fielding --------
    @GetMapping("/fielding-contribution/{playerId}")
    public ResponseEntity<ResponseUtil> fieldingContribution(@PathVariable Long playerId) {
        return ResponseEntity.ok(new ResponseUtil(200, "OK", performanceService.getFieldingContribution(playerId)));
    }

    // -------- Fitness --------
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

