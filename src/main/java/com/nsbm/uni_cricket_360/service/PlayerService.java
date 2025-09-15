package com.nsbm.uni_cricket_360.service;

import com.nsbm.uni_cricket_360.dto.PlayerDTO;
import com.nsbm.uni_cricket_360.dto.PlayerUpdateDTO;
import com.nsbm.uni_cricket_360.entity.Player;
import com.nsbm.uni_cricket_360.enums.PlayerRole;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PlayerService {
    List<PlayerDTO> getAllPlayers();

    PlayerDTO getPlayerById(Long id);

    PlayerDTO savePlayer(PlayerDTO dto, MultipartFile imageFile);

    PlayerDTO updatePlayer(Long id, PlayerUpdateDTO dto, MultipartFile imageFile);

    PlayerDTO updatePlayerImage(Long id, MultipartFile imageFile);

    PlayerDTO updatePlayerRole(Long id, PlayerRole newRole);

    void deletePlayer(Long id);

    int getPlayerCount();

//    PlayerDTO savePlayer(PlayerDTO dto);

//    String savePlayerImage(MultipartFile imageFile);
}
