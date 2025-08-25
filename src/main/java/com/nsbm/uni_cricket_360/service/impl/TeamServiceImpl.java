package com.nsbm.uni_cricket_360.service.impl;

import com.nsbm.uni_cricket_360.dto.TeamDTO;
import com.nsbm.uni_cricket_360.dto.UserDTO;
import com.nsbm.uni_cricket_360.entity.Team;
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
    public TeamDTO saveTeam(TeamDTO dto) {
        System.out.println("------------------- Inside TeamServiceImpl: saveTeam -------------------");
        System.out.println(dto);
        return mapper.map(teamRepo.save(mapper.map(dto, Team.class)), TeamDTO.class);
    }
}
