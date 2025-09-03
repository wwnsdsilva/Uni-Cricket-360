package com.nsbm.uni_cricket_360.service;

import com.nsbm.uni_cricket_360.dto.FitnessTestDTO;

import java.util.List;

public interface FitnessTestService {

    List<FitnessTestDTO> getAllFitnessTestResults();

    FitnessTestDTO searchFitnessTestResultsByPlayer(Long id);

    FitnessTestDTO saveFitnessTestResults(FitnessTestDTO dto);

    FitnessTestDTO updateFitnessTestResults(Long id, FitnessTestDTO dto);

    void deleteFitnessTestResults(Long id);
}
