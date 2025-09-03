package com.nsbm.uni_cricket_360.service.impl;

import com.nsbm.uni_cricket_360.dto.BowlingPerformanceDTO;
import com.nsbm.uni_cricket_360.entity.BowlingPerformance;
import com.nsbm.uni_cricket_360.entity.Inning;
import com.nsbm.uni_cricket_360.entity.Player;
import com.nsbm.uni_cricket_360.exception.NotFoundException;
import com.nsbm.uni_cricket_360.repository.BowlingPerformanceRepo;
import com.nsbm.uni_cricket_360.repository.InningRepo;
import com.nsbm.uni_cricket_360.repository.PlayerRepo;
import com.nsbm.uni_cricket_360.service.BowlingPerformanceService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BowlingPerformanceServiceImpl implements BowlingPerformanceService {

    @Autowired
    private BowlingPerformanceRepo bowlingPerformanceRepo;

    @Autowired
    private PlayerRepo playerRepo;

    @Autowired
    private InningRepo inningRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<BowlingPerformanceDTO> getAllBowlingPerformance() {
        return mapper.map(bowlingPerformanceRepo.findAll(), new TypeToken<List<BowlingPerformanceDTO>>() {
        }.getType());
    }

    @Override
    public BowlingPerformanceDTO searchBowlingPerformanceById(Long id) {
        BowlingPerformance bowlingPerformance = bowlingPerformanceRepo.findById(id).orElseThrow(() -> new NotFoundException("Bowling performance details not found with id " + id));
        return mapper.map(bowlingPerformance, BowlingPerformanceDTO.class);
    }

    @Override
    public BowlingPerformanceDTO saveBowlingPerformance(BowlingPerformanceDTO dto) {
        BowlingPerformance bowlingPerformance = mapper.map(dto, BowlingPerformance.class);

        // Set player from DB
        if (dto.getPlayer() != null && dto.getPlayer().getId() != null) {
            Player player = playerRepo.findById(dto.getPlayer().getId())
                    .orElseThrow(() -> new NotFoundException("Player not found with id: " + dto.getPlayer().getId()));
            bowlingPerformance.setPlayer(player);
        }

        // Set inning from DB
        if (dto.getInning() != null && dto.getInning().getId() != null) {
            Inning inning = inningRepo.findById(dto.getInning().getId()).orElseThrow(() -> new NotFoundException("Inning details not found with id: " + dto.getInning().getId()));
            bowlingPerformance.setInning(inning);
        }

        // Calculate and save the over in decimal
        double oversInDecimal = bowlingPerformance.getOversInDecimal();
        bowlingPerformance.setOvers(oversInDecimal);

        BowlingPerformance saved = bowlingPerformanceRepo.save(bowlingPerformance);
        return mapper.map(saved, BowlingPerformanceDTO.class);
    }

    @Override
    public BowlingPerformanceDTO updateBowlingPerformance(Long id, BowlingPerformanceDTO dto) {
        BowlingPerformance existingBowlingPerformance = bowlingPerformanceRepo.findById(id).orElseThrow(() -> new NotFoundException("Bowling performance details not found with id " + id));

        // Set player from DB
        if (dto.getPlayer() != null && dto.getPlayer().getId() != null) {
            Player player = playerRepo.findById(dto.getPlayer().getId())
                    .orElseThrow(() -> new NotFoundException("Player not found with id: " + dto.getPlayer().getId()));
            existingBowlingPerformance.setPlayer(player);
        }

        // Set inning from DB
        if (dto.getInning() != null && dto.getInning().getId() != null) {
            Inning inning = inningRepo.findById(dto.getInning().getId()).orElseThrow(() -> new NotFoundException("Inning details not found with id: " + dto.getInning().getId()));
            existingBowlingPerformance.setInning(inning);
        }

        existingBowlingPerformance.setBalls_bowled(dto.getBalls_bowled());
        double oversInDecimal = existingBowlingPerformance.getOversInDecimal();
        existingBowlingPerformance.setOvers(oversInDecimal);
        existingBowlingPerformance.setRuns_conceded(dto.getRuns_conceded());
        existingBowlingPerformance.setWickets(dto.getWickets());

        return mapper.map(bowlingPerformanceRepo.save(existingBowlingPerformance), BowlingPerformanceDTO.class);
    }

    @Override
    public void deleteBowlingPerformance(Long id) {
        BowlingPerformance bowlingPerformance = bowlingPerformanceRepo.findById(id).orElseThrow(() -> new NotFoundException("Bowling performance details not found with id " + id));
        bowlingPerformanceRepo.delete(bowlingPerformance);
    }
}
