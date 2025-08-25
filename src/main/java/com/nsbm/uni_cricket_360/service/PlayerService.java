package com.nsbm.uni_cricket_360.service;

import com.nsbm.uni_cricket_360.dto.PlayerDTO;

import java.util.List;

public interface PlayerService {
    List<PlayerDTO> getAllPlayers();

    PlayerDTO savePlayer(PlayerDTO dto);
}
