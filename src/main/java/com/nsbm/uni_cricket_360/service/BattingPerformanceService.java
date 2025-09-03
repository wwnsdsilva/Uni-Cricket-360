package com.nsbm.uni_cricket_360.service;

import com.nsbm.uni_cricket_360.dto.BattingPerformanceDTO;

import java.util.List;

public interface BattingPerformanceService {

    List<BattingPerformanceDTO> getAllBattingPerformance();

    BattingPerformanceDTO searchBattingPerformanceById(Long id);

    BattingPerformanceDTO saveBattingPerformance(BattingPerformanceDTO dto);

    BattingPerformanceDTO updateBattingPerformance(Long id, BattingPerformanceDTO dto);

    void deleteBattingPerformance(Long id);
}
