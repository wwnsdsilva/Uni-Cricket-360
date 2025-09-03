package com.nsbm.uni_cricket_360.service;

import com.nsbm.uni_cricket_360.dto.BowlingPerformanceDTO;

import java.util.List;

public interface BowlingPerformanceService {
    
    List<BowlingPerformanceDTO> getAllBowlingPerformance();

    BowlingPerformanceDTO searchBowlingPerformanceById(Long id);

    BowlingPerformanceDTO saveBowlingPerformance(BowlingPerformanceDTO dto);

    BowlingPerformanceDTO updateBowlingPerformance(Long id, BowlingPerformanceDTO dto);

    void deleteBowlingPerformance(Long id);
}
