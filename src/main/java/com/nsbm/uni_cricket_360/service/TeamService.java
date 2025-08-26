package com.nsbm.uni_cricket_360.service;

import com.nsbm.uni_cricket_360.dto.TeamDTO;

import java.util.List;

public interface TeamService {
    List<TeamDTO> getAllTeams();

    TeamDTO getTeamById(Long id);

    TeamDTO saveTeam(TeamDTO dto);

    TeamDTO updateTeam(Long id, TeamDTO dto);

    void deleteTeam(Long id);
}
