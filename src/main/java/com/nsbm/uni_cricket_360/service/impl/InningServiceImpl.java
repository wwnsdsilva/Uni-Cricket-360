package com.nsbm.uni_cricket_360.service.impl;

import com.nsbm.uni_cricket_360.dto.InningDTO;
import com.nsbm.uni_cricket_360.entity.Inning;
import com.nsbm.uni_cricket_360.entity.Match;
import com.nsbm.uni_cricket_360.entity.Team;
import com.nsbm.uni_cricket_360.exception.NotFoundException;
import com.nsbm.uni_cricket_360.repository.InningRepo;
import com.nsbm.uni_cricket_360.repository.MatchRepo;
import com.nsbm.uni_cricket_360.repository.TeamRepo;
import com.nsbm.uni_cricket_360.service.InningService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InningServiceImpl implements InningService {

    @Autowired
    private InningRepo inningRepo;

    @Autowired
    private TeamRepo teamRepo;

    @Autowired
    private MatchRepo matchRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<InningDTO> getAllInnings() {
        return mapper.map(inningRepo.findAll(), new TypeToken<List<InningDTO>>() {
        }.getType());
    }

    @Override
    public InningDTO searchInningById(Long id) {
        Inning inning = inningRepo.findById(id).orElseThrow(() -> new NotFoundException("Inning not found with id " + id));
        return mapper.map(inning, InningDTO.class);
    }

    @Override
    public InningDTO saveInning(InningDTO dto) {
        Inning inning = mapper.map(dto, Inning.class);

        // Set match from DB
        if (dto.getMatch() != null && dto.getMatch().getId() != null) {
            Match match = matchRepo.findById(dto.getMatch().getId())
                    .orElseThrow(() -> new NotFoundException("Match not found with id: " + dto.getMatch().getId()));
            inning.setMatch(match);
        }

        // Fetch actual team(batting) from DB to avoid null fields
        if (dto.getBatting_team() != null && dto.getBatting_team().getId() != null) {
            Team batting_team = teamRepo.findById(dto.getBatting_team().getId()).orElseThrow(() -> new NotFoundException("Batting Team not found with id: " + dto.getBatting_team().getId()));
            inning.setBatting_team(batting_team);
        }

        Inning saved = inningRepo.save(inning);
        return mapper.map(saved, InningDTO.class);
    }

    @Override
    public InningDTO updateInning(Long id, InningDTO dto) {
        Inning existingInning = inningRepo.findById(id).orElseThrow(() -> new NotFoundException("Inning not found with id " + id));

        // Set match from DB
        if (dto.getMatch() != null && dto.getMatch().getId() != null) {
            Match match = matchRepo.findById(dto.getMatch().getId())
                    .orElseThrow(() -> new NotFoundException("Match not found with id: " + dto.getMatch().getId()));
            existingInning.setMatch(match);
        }

        // Fetch actual team(batting) from DB to avoid null fields
        if (dto.getBatting_team() != null && dto.getBatting_team().getId() != null) {
            Team batting_team = teamRepo.findById(dto.getBatting_team().getId()).orElseThrow(() -> new NotFoundException("Batting Team not found with id: " + dto.getBatting_team().getId()));
            existingInning.setBatting_team(batting_team);
        }

        existingInning.setRuns(dto.getRuns());
        existingInning.setWickets(dto.getWickets());
        existingInning.setBalls(dto.getBalls());

        return mapper.map(inningRepo.save(existingInning), InningDTO.class);
    }

    @Override
    public void deleteInning(Long id) {
        Inning inning = inningRepo.findById(id).orElseThrow(() -> new NotFoundException("Inning not found with id " + id));
        inningRepo.delete(inning);
    }


}
