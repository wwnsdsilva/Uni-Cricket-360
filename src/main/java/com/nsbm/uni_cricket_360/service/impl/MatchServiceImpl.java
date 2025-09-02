package com.nsbm.uni_cricket_360.service.impl;

import com.nsbm.uni_cricket_360.dto.EventDTO;
import com.nsbm.uni_cricket_360.dto.MatchDTO;
import com.nsbm.uni_cricket_360.entity.Admin;
import com.nsbm.uni_cricket_360.entity.Event;
import com.nsbm.uni_cricket_360.entity.Match;
import com.nsbm.uni_cricket_360.entity.Team;
import com.nsbm.uni_cricket_360.exception.ImageFileException;
import com.nsbm.uni_cricket_360.exception.NotFoundException;
import com.nsbm.uni_cricket_360.repository.EventRepo;
import com.nsbm.uni_cricket_360.repository.MatchRepo;
import com.nsbm.uni_cricket_360.repository.TeamRepo;
import com.nsbm.uni_cricket_360.repository.UserRepo;
import com.nsbm.uni_cricket_360.service.MatchService;
import com.nsbm.uni_cricket_360.util.UploadImageUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
@Transactional
public class MatchServiceImpl implements MatchService {

    @Autowired
    MatchRepo matchRepo;

    @Autowired
    private TeamRepo teamRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UploadImageUtil uploadImageUtil;

    @Value("${match.upload-dir}")
    private String uploadDir;

    @Override
    public List<MatchDTO> getAllMatches() {
        return mapper.map(matchRepo.findAll(), new TypeToken<List<MatchDTO>>() {}.getType());
    }

    @Override
    public MatchDTO getMatchById(Long id) {
        Match match = matchRepo.findById(id).orElseThrow(() -> new NotFoundException("Match not found with id:" + id));
        return mapper.map(match, MatchDTO.class);
    }

    @Override
    public MatchDTO saveMatch(MatchDTO dto, MultipartFile imageFile) {
        try {
            Match match = mapper.map(dto, Match.class);

            // Fetch actual team(home) from DB to avoid null fields
            if (dto.getHome_team() != null && dto.getHome_team().getId() != null) {
                Team home_team = teamRepo.findById(dto.getHome_team().getId()).orElseThrow(() -> new NotFoundException("Home Team not found with id: " + dto.getHome_team().getId()));
                match.setHome_team(home_team);
            }

            // Fetch actual team(away) from DB to avoid null fields
            if (dto.getAway_team() != null && dto.getAway_team().getId() != null) {
                Team away_team = teamRepo.findById(dto.getAway_team().getId()).orElseThrow(() -> new NotFoundException("Away Team not found with id: " + dto.getHome_team().getId()));
                match.setAway_team(away_team);
            }

            // Set scheduled_by (Admin)
            if (dto.getScheduled_by() != null && dto.getScheduled_by().getId() != null) {
                Admin admin = (Admin) userRepo.findById(dto.getScheduled_by().getId())
                        .orElseThrow(() -> new NotFoundException("Admin not found with id: " + dto.getScheduled_by().getId()));
                match.setScheduled_by(admin);
            }

            // Save image if provided
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageUrl = saveMatchImage(imageFile);
                match.setImage_url(imageUrl);
            }

            Match saved = matchRepo.save(match);
            return mapper.map(saved, MatchDTO.class);

        } catch (Exception ex) {
            throw new RuntimeException("Failed to save match: " + ex.getMessage(), ex);
        }
    }

    @Override
    public MatchDTO updateMatch(Long id, MatchDTO dto, MultipartFile imageFile) {
        Match existingMatch = matchRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Match not found with id: " + id));

        // Fetch actual team(home) from DB to avoid null fields
        if (dto.getHome_team() != null && dto.getHome_team().getId() != null) {
            Team home_team = teamRepo.findById(dto.getHome_team().getId()).orElseThrow(() -> new NotFoundException("Home Team not found with id: " + dto.getHome_team().getId()));
            existingMatch.setHome_team(home_team);
        }

        // Fetch actual team(away) from DB to avoid null fields
        if (dto.getAway_team() != null && dto.getAway_team().getId() != null) {
            Team away_team = teamRepo.findById(dto.getAway_team().getId()).orElseThrow(() -> new NotFoundException("Away Team not found with id: " + dto.getHome_team().getId()));
            existingMatch.setAway_team(away_team);
        }

        // Set scheduled_by (Admin)
        if (dto.getScheduled_by() != null && dto.getScheduled_by().getId() != null) {
            Admin admin = (Admin) userRepo.findById(dto.getScheduled_by().getId())
                    .orElseThrow(() -> new NotFoundException("Admin not found with id: " + dto.getScheduled_by().getId()));
            existingMatch.setScheduled_by(admin);
        }

        // Update fields (keep existing values if null in dto)
        if (dto.getDescription() != null) existingMatch.setDescription(dto.getDescription());
        if (dto.getDate_time() != null) existingMatch.setDateTime(dto.getDate_time());
        if (dto.getVenue() != null) existingMatch.setVenue(dto.getVenue());
        if (dto.getOvers_per_inning() != 0) existingMatch.setOvers_per_inning(dto.getOvers_per_inning());
        if (dto.getStatus() != null) existingMatch.setStatus(dto.getStatus());
        if (dto.getMatch_type() != null) existingMatch.setMatch_type(dto.getMatch_type());
        if (dto.getResult() != null) existingMatch.setResult(dto.getResult());

        String oldImage = null;
        String newImageUrl;

        // Handle image replacement if new one provided
        if (imageFile != null && !imageFile.isEmpty()) {
            oldImage = existingMatch.getImage_url();
            newImageUrl = saveMatchImage(imageFile);
            existingMatch.setImage_url(newImageUrl);
        }

        Match updated = matchRepo.save(existingMatch);

        // Delete old image file
        deleteOldImageFile(oldImage);

        return mapper.map(updated, MatchDTO.class);
    }

    @Override
    public MatchDTO updateMatchImage(Long id, MultipartFile imageFile) {
        Match existingMatch = matchRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Match not found with id: " + id));

        String oldImage = existingMatch.getImage_url();
        String newImageUrl = saveMatchImage(imageFile);
        existingMatch.setImage_url(newImageUrl);

        Match updated = matchRepo.save(existingMatch);

        // Delete old image file
        deleteOldImageFile(oldImage);

        return mapper.map(updated, MatchDTO.class);
    }

    @Override
    public void deleteMatch(Long id) {
        Match existingMatch = matchRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Match not found with id: " + id));

        // Delete image file if exists
        deleteOldImageFile(existingMatch.getImage_url());

        // Delete event from DB
        matchRepo.delete(existingMatch);

    }

    private String saveMatchImage(MultipartFile imageFile) {
        return uploadImageUtil.saveImage(uploadDir, imageFile);
    }

    private void deleteOldImageFile(String oldImageUrl){
        if (oldImageUrl != null) {
            try {
                Files.deleteIfExists(Paths.get(oldImageUrl));
            } catch (IOException e) {
                throw new ImageFileException("Failed to delete match image: " + e.getMessage());
            }
        }
    }
}
