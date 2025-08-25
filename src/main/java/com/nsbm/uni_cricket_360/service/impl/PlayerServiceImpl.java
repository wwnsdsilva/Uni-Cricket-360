package com.nsbm.uni_cricket_360.service.impl;

import com.nsbm.uni_cricket_360.dto.PlayerDTO;
import com.nsbm.uni_cricket_360.dto.UserDTO;
import com.nsbm.uni_cricket_360.entity.Player;
import com.nsbm.uni_cricket_360.entity.Team;
import com.nsbm.uni_cricket_360.repository.PlayerRepo;
import com.nsbm.uni_cricket_360.repository.TeamRepo;
import com.nsbm.uni_cricket_360.service.PlayerService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepo playerRepo;

    @Autowired
    private TeamRepo teamRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<PlayerDTO> getAllPlayers() {
        return mapper.map(playerRepo.findAll(), new TypeToken<List<PlayerDTO>>() {
        }.getType());
    }

    @Override
    public PlayerDTO savePlayer(PlayerDTO dto) {
        System.out.println("------------------- Inside PlayerServiceImpl: savePlayer -------------------");
        System.out.println(dto);

        Player player = mapper.map(dto, Player.class);

        // fetch actual team from DB to avoid null fields
        if (dto.getTeam() != null && dto.getTeam().getId() != null) {
            Team team = teamRepo.findById(dto.getTeam().getId()).orElseThrow(() -> new RuntimeException("Team not found with id " + dto.getTeam().getId()));
            player.setTeam(team);
        }

        //Hash password before saving
        player.setPassword(passwordEncoder.encode(dto.getPassword()));

        Player saved = playerRepo.save(player);
        return mapper.map(saved, PlayerDTO.class);

    }
}
