package com.nsbm.uni_cricket_360.service.impl;

import com.nsbm.uni_cricket_360.dto.TeamDTO;
import com.nsbm.uni_cricket_360.dto.UserDTO;
import com.nsbm.uni_cricket_360.entity.Team;
import com.nsbm.uni_cricket_360.exception.NotFoundException;
import com.nsbm.uni_cricket_360.repository.TeamRepo;
import com.nsbm.uni_cricket_360.service.TeamService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepo teamRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<TeamDTO> getAllTeams() {
        return mapper.map(teamRepo.findAll(), new TypeToken<List<TeamDTO>>() {
        }.getType());
    }

    @Override
    public TeamDTO getTeamById(Long id) {
        Team team = teamRepo.findById(id).orElseThrow(() -> new NotFoundException("Team not found with id " + id));
        return mapper.map(team, TeamDTO.class);
    }

    @Override
    public TeamDTO saveTeam(TeamDTO dto) {
        return mapper.map(teamRepo.save(mapper.map(dto, Team.class)), TeamDTO.class);
    }

    @Override
    public TeamDTO updateTeam(Long id, TeamDTO dto) {
        Team team = teamRepo.findById(id).orElseThrow(() -> new NotFoundException("Team not found with id " + id));

        team.setTeam_name(dto.getTeam_name());
        team.setVenue(dto.getVenue());
        return mapper.map(teamRepo.save(team), TeamDTO.class);
    }

    @Override
    public void deleteTeam(Long id) {
        Team team = teamRepo.findById(id).orElseThrow(() -> new NotFoundException("Team not found with id " + id));
        teamRepo.delete(team);
    }
}
