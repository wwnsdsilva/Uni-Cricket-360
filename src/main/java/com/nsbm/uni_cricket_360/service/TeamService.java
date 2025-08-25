package com.nsbm.uni_cricket_360.service;

import com.nsbm.uni_cricket_360.dto.TeamDTO;

import java.util.List;

public interface TeamService {
    List<TeamDTO> getAllTeams();

    TeamDTO saveTeam(TeamDTO dto);
}
