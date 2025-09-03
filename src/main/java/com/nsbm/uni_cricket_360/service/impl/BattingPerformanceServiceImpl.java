package com.nsbm.uni_cricket_360.service.impl;

import com.nsbm.uni_cricket_360.dto.BattingPerformanceDTO;
import com.nsbm.uni_cricket_360.entity.BattingPerformance;
import com.nsbm.uni_cricket_360.entity.Inning;
import com.nsbm.uni_cricket_360.entity.Player;
import com.nsbm.uni_cricket_360.exception.NotFoundException;
import com.nsbm.uni_cricket_360.repository.BattingPerformanceRepo;
import com.nsbm.uni_cricket_360.repository.InningRepo;
import com.nsbm.uni_cricket_360.repository.PlayerRepo;
import com.nsbm.uni_cricket_360.service.BattingPerformanceService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BattingPerformanceServiceImpl implements BattingPerformanceService {

    @Autowired
    BattingPerformanceRepo battingPerformanceRepo;

    @Autowired
    PlayerRepo playerRepo;

    @Autowired
    InningRepo inningRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<BattingPerformanceDTO> getAllBattingPerformance() {
        return mapper.map(battingPerformanceRepo.findAll(), new TypeToken<List<BattingPerformanceDTO>>() {
        }.getType());
    }

    @Override
    public BattingPerformanceDTO searchBattingPerformanceById(Long id) {
        BattingPerformance battingPerformance = battingPerformanceRepo.findById(id).orElseThrow(() -> new NotFoundException("Batting performance details not found with id " + id));
        return mapper.map(battingPerformance, BattingPerformanceDTO.class);
    }

    @Override
    public BattingPerformanceDTO saveBattingPerformance(BattingPerformanceDTO dto) {
        BattingPerformance battingPerformance = mapper.map(dto, BattingPerformance.class);

        // Set player from DB
        if (dto.getPlayer() != null && dto.getPlayer().getId() != null) {
            Player player = playerRepo.findById(dto.getPlayer().getId())
                    .orElseThrow(() -> new NotFoundException("Player not found with id: " + dto.getPlayer().getId()));
            battingPerformance.setPlayer(player);
        }

        // Set inning from DB
        if (dto.getInning() != null && dto.getInning().getId() != null) {
            Inning inning = inningRepo.findById(dto.getInning().getId()).orElseThrow(() -> new NotFoundException("Inning details not found with id: " + dto.getInning().getId()));
            battingPerformance.setInning(inning);
        }

        BattingPerformance saved = battingPerformanceRepo.save(battingPerformance);
        return mapper.map(saved, BattingPerformanceDTO.class);
    }

    @Override
    public BattingPerformanceDTO updateBattingPerformance(Long id, BattingPerformanceDTO dto) {
        BattingPerformance existingBattingPerformance = battingPerformanceRepo.findById(id).orElseThrow(() -> new NotFoundException("Batting performance details not found with id " + id));

        // Set player from DB
        if (dto.getPlayer() != null && dto.getPlayer().getId() != null) {
            Player player = playerRepo.findById(dto.getPlayer().getId())
                    .orElseThrow(() -> new NotFoundException("Player not found with id: " + dto.getPlayer().getId()));
            existingBattingPerformance.setPlayer(player);
        }

        // Set inning from DB
        if (dto.getInning() != null && dto.getInning().getId() != null) {
            Inning inning = inningRepo.findById(dto.getInning().getId()).orElseThrow(() -> new NotFoundException("Inning details not found with id: " + dto.getInning().getId()));
            existingBattingPerformance.setInning(inning);
        }

        existingBattingPerformance.setRuns(dto.getRuns());
        existingBattingPerformance.setBalls_faced(dto.getBalls_faced());
        existingBattingPerformance.setFours(dto.getFours());
        existingBattingPerformance.setSixes(dto.getSixes());
        existingBattingPerformance.setDismissal_type(dto.getDismissal_type());

        return mapper.map(battingPerformanceRepo.save(existingBattingPerformance), BattingPerformanceDTO.class);
    }

    @Override
    public void deleteBattingPerformance(Long id) {
        BattingPerformance battingPerformance = battingPerformanceRepo.findById(id).orElseThrow(() -> new NotFoundException("Batting performance deatils not found with id " + id));
        battingPerformanceRepo.delete(battingPerformance);
    }
}
