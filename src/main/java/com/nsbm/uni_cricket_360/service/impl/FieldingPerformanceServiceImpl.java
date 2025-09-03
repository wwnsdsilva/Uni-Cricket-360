package com.nsbm.uni_cricket_360.service.impl;

import com.nsbm.uni_cricket_360.dto.FieldingPerformanceDTO;
import com.nsbm.uni_cricket_360.dto.FieldingPerformanceDTO;
import com.nsbm.uni_cricket_360.entity.*;
import com.nsbm.uni_cricket_360.exception.NotFoundException;
import com.nsbm.uni_cricket_360.repository.FieldingPerformanceRepo;
import com.nsbm.uni_cricket_360.repository.MatchRepo;
import com.nsbm.uni_cricket_360.repository.PlayerRepo;
import com.nsbm.uni_cricket_360.service.FieldingPerformanceService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FieldingPerformanceServiceImpl implements FieldingPerformanceService {

    @Autowired
    private FieldingPerformanceRepo fieldingPerformanceRepo;

    @Autowired
    private PlayerRepo playerRepo;

    @Autowired
    private MatchRepo matchRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<FieldingPerformanceDTO> getAllFieldingPerformance() {
        return mapper.map(fieldingPerformanceRepo.findAll(), new TypeToken<List<FieldingPerformanceDTO>>() {
        }.getType());
    }

    @Override
    public FieldingPerformanceDTO searchFieldingPerformanceById(Long id) {
        FieldingPerformance fieldingPerformance = fieldingPerformanceRepo.findById(id).orElseThrow(() -> new NotFoundException("Fielding performance details not found with id " + id));
        return mapper.map(fieldingPerformance, FieldingPerformanceDTO.class);
    }

    @Override
    public FieldingPerformanceDTO saveFieldingPerformance(FieldingPerformanceDTO dto) {
        FieldingPerformance fieldingPerformance = mapper.map(dto, FieldingPerformance.class);

        // Set player from DB
        if (dto.getPlayer() != null && dto.getPlayer().getId() != null) {
            Player player = playerRepo.findById(dto.getPlayer().getId())
                    .orElseThrow(() -> new NotFoundException("Player not found with id: " + dto.getPlayer().getId()));
            fieldingPerformance.setPlayer(player);
        }

        // Set match from DB
        if (dto.getMatch() != null && dto.getMatch().getId() != null) {
            Match match = matchRepo.findById(dto.getMatch().getId()).orElseThrow(() -> new NotFoundException("Match details not found with id: " + dto.getMatch().getId()));
            fieldingPerformance.setMatch(match);
        }

        FieldingPerformance saved = fieldingPerformanceRepo.save(fieldingPerformance);
        return mapper.map(saved, FieldingPerformanceDTO.class);
    }

    @Override
    public FieldingPerformanceDTO updateFieldingPerformance(Long id, FieldingPerformanceDTO dto) {
        FieldingPerformance existingFieldingPerformance = fieldingPerformanceRepo.findById(id).orElseThrow(() -> new NotFoundException("Fielding performance details not found with id " + id));

        // Set player from DB
        if (dto.getPlayer() != null && dto.getPlayer().getId() != null) {
            Player player = playerRepo.findById(dto.getPlayer().getId())
                    .orElseThrow(() -> new NotFoundException("Player not found with id: " + dto.getPlayer().getId()));
            existingFieldingPerformance.setPlayer(player);
        }

        // Set match from DB
        if (dto.getMatch() != null && dto.getMatch().getId() != null) {
            Match match = matchRepo.findById(dto.getMatch().getId()).orElseThrow(() -> new NotFoundException("Match details not found with id: " + dto.getMatch().getId()));
            existingFieldingPerformance.setMatch(match);
        }

        existingFieldingPerformance.setCatches(dto.getCatches());
        existingFieldingPerformance.setDirect_run_outs(dto.getDirect_run_outs());
        existingFieldingPerformance.setAssisted_run_outs(dto.getAssisted_run_outs());
        existingFieldingPerformance.setStumpings(dto.getStumpings());

        return mapper.map(fieldingPerformanceRepo.save(existingFieldingPerformance), FieldingPerformanceDTO.class);
    }

    @Override
    public void deleteFieldingPerformance(Long id) {
        FieldingPerformance fieldingPerformance = fieldingPerformanceRepo.findById(id).orElseThrow(() -> new NotFoundException("Fielding performance details not found with id " + id));
        fieldingPerformanceRepo.delete(fieldingPerformance);
    }
}
