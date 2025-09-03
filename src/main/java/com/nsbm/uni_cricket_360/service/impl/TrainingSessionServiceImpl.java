package com.nsbm.uni_cricket_360.service.impl;

import com.nsbm.uni_cricket_360.dto.TrainingSessionDTO;
import com.nsbm.uni_cricket_360.entity.TrainingSession;
import com.nsbm.uni_cricket_360.exception.NotFoundException;
import com.nsbm.uni_cricket_360.repository.TrainingSessionRepo;
import com.nsbm.uni_cricket_360.service.TrainingSessionService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TrainingSessionServiceImpl implements TrainingSessionService {

    @Autowired
    private TrainingSessionRepo trainingSessionRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<TrainingSessionDTO> getAllTrainingSessions() {
        return mapper.map(trainingSessionRepo.findAll(), new TypeToken<List<TrainingSessionDTO>>() {
        }.getType());
    }

    @Override
    public TrainingSessionDTO searchSessionById(Long id) {
        TrainingSession trainingSession = trainingSessionRepo.findById(id).orElseThrow(() -> new NotFoundException("Training session not found with id " + id));
        return mapper.map(trainingSession, TrainingSessionDTO.class);
    }

    @Override
    public TrainingSessionDTO saveSession(TrainingSessionDTO dto) {
        return mapper.map(trainingSessionRepo.save(mapper.map(dto, TrainingSession.class)), TrainingSessionDTO.class);
    }

    @Override
    public TrainingSessionDTO updateSession(Long id, TrainingSessionDTO dto) {
        TrainingSession existingTrainingSession = trainingSessionRepo.findById(id).orElseThrow(() -> new NotFoundException("Training session not found with id " + id));

        existingTrainingSession.setDescription(dto.getDescription());
        existingTrainingSession.setDate(dto.getDate());
        return mapper.map(trainingSessionRepo.save(existingTrainingSession), TrainingSessionDTO.class);
    }

    @Override
    public void deleteSession(Long id) {
        TrainingSession trainingSession = trainingSessionRepo.findById(id).orElseThrow(() -> new NotFoundException("Training session not found with id " + id));
        trainingSessionRepo.delete(trainingSession);
    }
}
