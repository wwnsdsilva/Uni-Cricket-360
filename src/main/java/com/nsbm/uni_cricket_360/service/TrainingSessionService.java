package com.nsbm.uni_cricket_360.service;

import com.nsbm.uni_cricket_360.dto.TrainingSessionDTO;

import java.util.List;

public interface TrainingSessionService {

    List<TrainingSessionDTO> getAllTrainingSessions();

    TrainingSessionDTO searchSessionById(Long id);

    TrainingSessionDTO saveSession(TrainingSessionDTO dto);

    TrainingSessionDTO updateSession(Long id, TrainingSessionDTO dto);

    void deleteSession(Long id);
}
