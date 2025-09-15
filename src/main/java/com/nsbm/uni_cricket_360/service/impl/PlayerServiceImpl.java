package com.nsbm.uni_cricket_360.service.impl;

import com.nsbm.uni_cricket_360.dto.PlayerDTO;
import com.nsbm.uni_cricket_360.dto.PlayerUpdateDTO;
import com.nsbm.uni_cricket_360.entity.Player;
import com.nsbm.uni_cricket_360.entity.Team;
import com.nsbm.uni_cricket_360.enums.PlayerRole;
import com.nsbm.uni_cricket_360.exception.ImageFileException;
import com.nsbm.uni_cricket_360.exception.NotFoundException;
import com.nsbm.uni_cricket_360.repository.PlayerRepo;
import com.nsbm.uni_cricket_360.repository.TeamRepo;
import com.nsbm.uni_cricket_360.service.PlayerService;
import com.nsbm.uni_cricket_360.util.UploadImageUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    @Autowired
    private UploadImageUtil uploadImageUtil;

    @Value("${player.upload-dir}")
    private String uploadDir;

    @Override
    public List<PlayerDTO> getAllPlayers() {
        return mapper.map(playerRepo.findAll(), new TypeToken<List<PlayerDTO>>() {
        }.getType());
    }

    @Override
    public PlayerDTO getPlayerById(Long id) {
        Player player = playerRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Player not found with id: " + id));
        return mapper.map(player, PlayerDTO.class);
    }

    @Override
    public PlayerDTO savePlayer(PlayerDTO dto, MultipartFile imageFile) {
        try {
            Player player = mapper.map(dto, Player.class);

            // Fetch actual team from DB to avoid null fields
            if (dto.getTeam() != null && dto.getTeam().getId() != null) {
                Team team = teamRepo.findById(dto.getTeam().getId()).orElseThrow(() -> new NotFoundException("Team not found with id: " + dto.getTeam().getId()));
                player.setTeam(team);
            }

            // Save image if provided
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageUrl = savePlayerImage(imageFile);
                player.setImage_url(imageUrl);
            }

            //Hash password before saving
            player.setPassword(passwordEncoder.encode(dto.getPassword()));

            Player saved = playerRepo.save(player);
            return mapper.map(saved, PlayerDTO.class);

        } catch (Exception ex) {
            throw new RuntimeException("Failed to save existingPlayer: " + ex.getMessage(), ex);
        }
    }

    @Override
    public PlayerDTO updatePlayer(Long id, PlayerUpdateDTO dto, MultipartFile imageFile) {
        Player existingPlayer = playerRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Player not found with id: " + id));

        // Fetch actual team from DB to avoid null fields
        if (dto.getTeam() != null && dto.getTeam().getId() != null) {
            Team team = teamRepo.findById(dto.getTeam().getId()).orElseThrow(() -> new NotFoundException("Team not found with id: " + dto.getTeam().getId()));
            existingPlayer.setTeam(team);
        }

        // Update fields (keep existingPlayer values if null in dto)
        if (dto.getFirst_name() != null) existingPlayer.setFirst_name(dto.getFirst_name());
        if (dto.getLast_name() != null) existingPlayer.setLast_name(dto.getLast_name());
        if (dto.getName() != null) existingPlayer.setName(dto.getName());
        if (dto.getDob() != null) existingPlayer.setDob(dto.getDob());
        if (dto.getAge() != 0) existingPlayer.setAge(dto.getAge());
        if (dto.getContact() != null) existingPlayer.setContact(dto.getContact());
        if (dto.getPlayer_role() != null) existingPlayer.setPlayer_role(dto.getPlayer_role());

        if (dto.getUniversity_id() != null) existingPlayer.setUniversity_id(dto.getUniversity_id());
        if (dto.getBatting_style() != null) existingPlayer.setBatting_style(dto.getBatting_style());
        if (dto.getBowling_style() != null) existingPlayer.setBowling_style(dto.getBowling_style());
        if (dto.getJersey_no() != 0) existingPlayer.setJersey_no(dto.getJersey_no());
        if (dto.getJoined_date() != null) existingPlayer.setJoined_date(dto.getJoined_date());

        String oldImage = null;
        String newImageUrl;

        // Handle image replacement if new image is provided
        if (imageFile != null && !imageFile.isEmpty()) {
            oldImage = existingPlayer.getImage_url();
            newImageUrl = savePlayerImage(imageFile);
            existingPlayer.setImage_url(newImageUrl);
        }

        Player updated = playerRepo.save(existingPlayer);

        // Delete old image file
        deleteOldImageFile(existingPlayer.getImage_url());

        return mapper.map(updated, PlayerDTO.class);
    }

    @Override
    public PlayerDTO updatePlayerImage(Long id, MultipartFile imageFile) {
        Player existingPlayer = playerRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Player not found with id: " + id));

        String oldImage = existingPlayer.getImage_url();
        String newImageUrl = savePlayerImage(imageFile);
        existingPlayer.setImage_url(newImageUrl);

        Player updated = playerRepo.save(existingPlayer);

        // Delete old image
        deleteOldImageFile(existingPlayer.getImage_url());

        return mapper.map(updated, PlayerDTO.class);
    }

    @Override
    public PlayerDTO updatePlayerRole(Long id, PlayerRole newRole) {
        Player player = playerRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Player not found with id: " + id));
        player.setPlayer_role(newRole);
//        if (newRole != null)  player.setPlayer_role(newRole);
        return mapper.map(playerRepo.save(player), PlayerDTO.class);
    }

    @Override
    public void deletePlayer(Long id) {
        Player player = playerRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Player not found with id: " + id));

        // Delete image file if exists
        deleteOldImageFile(player.getImage_url());

        playerRepo.delete(player);
    }

    @Override
    public int getPlayerCount() {
        return playerRepo.findAll().size();
    }

    private String savePlayerImage(MultipartFile imageFile) {
        return uploadImageUtil.saveImage(uploadDir, "players", imageFile);
    }

    private void deleteOldImageFile(String oldImageUrl){
        if (oldImageUrl != null) {
            try {
                Files.deleteIfExists(Paths.get(oldImageUrl));
            } catch (IOException e) {
                throw new ImageFileException("Failed to delete event image: " + e.getMessage());
            }
        }
    }
}
