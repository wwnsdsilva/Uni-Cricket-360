package com.nsbm.uni_cricket_360.service;

import com.nsbm.uni_cricket_360.dto.FieldingPerformanceDTO;

import java.util.List;

public interface FieldingPerformanceService {

    List<FieldingPerformanceDTO> getAllFieldingPerformance();

    FieldingPerformanceDTO searchFieldingPerformanceById(Long id);

    FieldingPerformanceDTO saveFieldingPerformance(FieldingPerformanceDTO dto);

    FieldingPerformanceDTO updateFieldingPerformance(Long id, FieldingPerformanceDTO dto);

    void deleteFieldingPerformance(Long id);
}

