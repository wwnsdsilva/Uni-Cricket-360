package com.nsbm.uni_cricket_360.service.impl;

import com.nsbm.uni_cricket_360.dto.FieldingPerformanceDTO;
import com.nsbm.uni_cricket_360.dto.FitnessTestDTO;
import com.nsbm.uni_cricket_360.entity.FieldingPerformance;
import com.nsbm.uni_cricket_360.entity.FitnessTest;
import com.nsbm.uni_cricket_360.entity.Player;
import com.nsbm.uni_cricket_360.exception.NotFoundException;
import com.nsbm.uni_cricket_360.repository.FitnessTestRepo;
import com.nsbm.uni_cricket_360.repository.PlayerRepo;
import com.nsbm.uni_cricket_360.service.FitnessTestService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FitnessTestServiceImpl implements FitnessTestService {

    @Autowired
    private FitnessTestRepo fitnessTestRepo;

    @Autowired
    private PlayerRepo playerRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<FitnessTestDTO> getAllFitnessTestResults() {
        return mapper.map(fitnessTestRepo.findAll(), new TypeToken<List<FitnessTestDTO>>() {
        }.getType());
    }

    @Override
    public FitnessTestDTO searchFitnessTestResultsByPlayer(Long id) {
        FitnessTest fitnessTest = fitnessTestRepo.findById(id).orElseThrow(() -> new NotFoundException("Fitness test results not found with id " + id));
        return mapper.map(fitnessTest, FitnessTestDTO.class);
    }

    @Override
    public FitnessTestDTO saveFitnessTestResults(FitnessTestDTO dto) {
        FitnessTest fitnessTest = mapper.map(dto, FitnessTest.class);

        // Set player from DB
        if (dto.getPlayer() != null && dto.getPlayer().getId() != null) {
            Player player = playerRepo.findById(dto.getPlayer().getId())
                    .orElseThrow(() -> new NotFoundException("Player not found with id: " + dto.getPlayer().getId()));
            fitnessTest.setPlayer(player);
        }

        FitnessTest saved = fitnessTestRepo.save(fitnessTest);
        return mapper.map(saved, FitnessTestDTO.class);
    }

    @Override
    public FitnessTestDTO updateFitnessTestResults(Long id, FitnessTestDTO dto) {
        FitnessTest existingFitnessTest = fitnessTestRepo.findById(id).orElseThrow(() -> new NotFoundException("Fitness test results not found with id " + id));

        // Set player from DB
        if (dto.getPlayer() != null && dto.getPlayer().getId() != null) {
            Player player = playerRepo.findById(dto.getPlayer().getId())
                    .orElseThrow(() -> new NotFoundException("Player not found with id: " + dto.getPlayer().getId()));
            existingFitnessTest.setPlayer(player);
        }

        existingFitnessTest.setDate(dto.getDate());
        existingFitnessTest.setSprint_time(dto.getSprint_time());
        existingFitnessTest.setBeep_level(dto.getBeep_level());

        FitnessTest saved = fitnessTestRepo.save(existingFitnessTest);
        return mapper.map(saved, FitnessTestDTO.class);
    }

    @Override
    public void deleteFitnessTestResults(Long id) {
        FitnessTest fitnessTest = fitnessTestRepo.findById(id).orElseThrow(() -> new NotFoundException("Fitness test results not found with id " + id));
        fitnessTestRepo.delete(fitnessTest);
    }
}
