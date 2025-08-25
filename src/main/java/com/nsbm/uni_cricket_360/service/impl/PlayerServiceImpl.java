package com.nsbm.uni_cricket_360.service.impl;

import com.nsbm.uni_cricket_360.dto.PlayerDTO;
import com.nsbm.uni_cricket_360.entity.Player;
import com.nsbm.uni_cricket_360.entity.Team;
import com.nsbm.uni_cricket_360.exception.ImageFileException;
import com.nsbm.uni_cricket_360.exception.NotFoundException;
import com.nsbm.uni_cricket_360.repository.PlayerRepo;
import com.nsbm.uni_cricket_360.repository.TeamRepo;
import com.nsbm.uni_cricket_360.service.PlayerService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

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

    @Value("${player.upload-dir}")
    private String uploadDir;

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

        // Fetch actual team from DB to avoid null fields
        if (dto.getTeam() != null && dto.getTeam().getId() != null) {
            Team team = teamRepo.findById(dto.getTeam().getId()).orElseThrow(() -> new NotFoundException("Team not found with id: " + dto.getTeam().getId()));
            player.setTeam(team);
        }

        //Hash password before saving
        player.setPassword(passwordEncoder.encode(dto.getPassword()));

        Player saved = playerRepo.save(player);
        return mapper.map(saved, PlayerDTO.class);

    }

    @Override
    public String savePlayerImage(MultipartFile imageFile) {
        try {
            Path uploadPath = Paths.get(uploadDir);

            // Create directories if they don't exist
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate a unique filename
            String fileName = UUID.randomUUID() + "_" + StringUtils.cleanPath(imageFile.getOriginalFilename());
            Path filePath = uploadPath.resolve(fileName);

            // Save the file
            imageFile.transferTo(filePath.toFile());

            // Return path (can also return relative URL)
            return filePath.toString();

        } catch (IOException e) {
            throw new ImageFileException("Failed to store image file: " + e.getMessage());
        }
    }
}
