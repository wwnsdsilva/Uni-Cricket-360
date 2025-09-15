package com.nsbm.uni_cricket_360.service;

import com.nsbm.uni_cricket_360.dto.TrainingSessionBasicDTO;
import com.nsbm.uni_cricket_360.dto.TrainingSessionDTO;
import com.nsbm.uni_cricket_360.enums.SessionStatus;

import java.util.List;

public interface TrainingSessionService {

    List<TrainingSessionBasicDTO> getAllTrainingSessions();

    TrainingSessionDTO searchSessionById(Long id);

    TrainingSessionDTO saveSession(TrainingSessionDTO dto);

    TrainingSessionBasicDTO updateSession(Long id, TrainingSessionBasicDTO dto);

    void deleteSession(Long id);

    int getTrainingSessionCount(SessionStatus status);

    List<TrainingSessionDTO> getAllSessionsWithAttendance();
}
