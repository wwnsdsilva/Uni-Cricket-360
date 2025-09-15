package com.nsbm.uni_cricket_360.service.impl;

import com.nsbm.uni_cricket_360.dto.*;
import com.nsbm.uni_cricket_360.entity.*;
import com.nsbm.uni_cricket_360.exception.ImageFileException;
import com.nsbm.uni_cricket_360.exception.NotFoundException;
import com.nsbm.uni_cricket_360.repository.*;
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
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchRepo matchRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TeamRepo teamRepo;

    @Autowired
    private InningRepo inningRepo;

    @Autowired
    private PlayerRepo playerRepo;

    @Autowired
    private BattingPerformanceRepo battingPerformanceRepo;

    @Autowired
    private BowlingPerformanceRepo bowlingPerformanceRepo;

    @Autowired
    private FieldingPerformanceRepo fieldingPerformanceRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UploadImageUtil uploadImageUtil;

    @Value("${match.upload-dir}")
    private String uploadDir;

    @Override
    public List<MatchDTO> getAllMatches() {
        return mapper.map(matchRepo.findAll(), new TypeToken<List<MatchDTO>>() {
        }.getType());
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

            /*// Fetch actual team(home) from DB to avoid null fields
            if (dto.getHome_team() != null && dto.getHome_team().getId() != null) {
                Team home_team = teamRepo.findById(dto.getHome_team().getId()).orElseThrow(() -> new NotFoundException("Home Team not found with id: " + dto.getHome_team().getId()));
                match.setHome_team(home_team);
            }

            // Fetch actual team(away) from DB to avoid null fields
            if (dto.getAway_team() != null && dto.getAway_team().getId() != null) {
                Team away_team = teamRepo.findById(dto.getAway_team().getId()).orElseThrow(() -> new NotFoundException("Away Team not found with id: " + dto.getHome_team().getId()));
                match.setAway_team(away_team);
            }*/

            // Fetch actual opponent team from DB to avoid null fields
            if (dto.getOpponent() != null && dto.getOpponent().getId() != null) {
                Team away_team = teamRepo.findById(dto.getOpponent().getId()).orElseThrow(() -> new NotFoundException("Opponent Team not found with id: " + dto.getOpponent().getId()));
                match.setOpponent(away_team);
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

        /*// Fetch actual team(home) from DB to avoid null fields
        if (dto.getHome_team() != null && dto.getHome_team().getId() != null) {
            Team home_team = teamRepo.findById(dto.getHome_team().getId()).orElseThrow(() -> new NotFoundException("Home Team not found with id: " + dto.getHome_team().getId()));
            existingMatch.setHome_team(home_team);
        }

        // Fetch actual team(away) from DB to avoid null fields
        if (dto.getAway_team() != null && dto.getAway_team().getId() != null) {
            Team away_team = teamRepo.findById(dto.getAway_team().getId()).orElseThrow(() -> new NotFoundException("Away Team not found with id: " + dto.getHome_team().getId()));
            existingMatch.setAway_team(away_team);
        }*/

        // Fetch actual opponent team from DB to avoid null fields
        if (dto.getOpponent() != null && dto.getOpponent().getId() != null) {
            Team away_team = teamRepo.findById(dto.getOpponent().getId()).orElseThrow(() -> new NotFoundException("Opponent Team not found with id: " + dto.getOpponent().getId()));
            existingMatch.setOpponent(away_team);
        }

        // Set scheduled_by (Admin)
        if (dto.getScheduled_by() != null && dto.getScheduled_by().getId() != null) {
            Admin admin = (Admin) userRepo.findById(dto.getScheduled_by().getId())
                    .orElseThrow(() -> new NotFoundException("Admin not found with id: " + dto.getScheduled_by().getId()));
            existingMatch.setScheduled_by(admin);
        }

        // Update fields (keep existing values if null in dto)
        if (dto.getDescription() != null) existingMatch.setDescription(dto.getDescription());
        if (dto.getDate_time() != null) existingMatch.setDate_time(dto.getDate_time());
        if (dto.getVenue() != null) existingMatch.setVenue(dto.getVenue());
        if (dto.getOvers_per_inning() != 0) existingMatch.setOvers_per_inning(dto.getOvers_per_inning());
        if (dto.getStatus() != null) existingMatch.setStatus(dto.getStatus());
        if (dto.getMatch_type() != null) existingMatch.setMatch_type(dto.getMatch_type());
        if (dto.getResult() != null) existingMatch.setResult(dto.getResult());
        if (dto.getScore() != null) existingMatch.setScore(dto.getScore());
        if (dto.getOpponent_score() != null) existingMatch.setOpponent_score(dto.getOpponent_score());
//        if (dto.getToss() != null) existingMatch.setToss(dto.getToss());

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
    @Transactional
    public void deleteMatch(Long id) {
        /*Match existingMatch = matchRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Match not found with id: " + id));

        // Delete image file if exists
        deleteOldImageFile(existingMatch.getImage_url());

        // Delete event from DB
        matchRepo.delete(existingMatch);*/

        try {
            Match match = matchRepo.findById(id)
                    .orElseThrow(() -> new NotFoundException("Match not found with id: " + id));

            // Delete fielding stats
            fieldingPerformanceRepo.deleteByMatch(match);

            // Delete innings and their stats
            List<Inning> innings = inningRepo.findByMatch(match);
            for (Inning inning : innings) {
                battingPerformanceRepo.deleteByInning(inning);
                bowlingPerformanceRepo.deleteByInning(inning);
            }
            inningRepo.deleteAll(innings);

            // Delete image file if exists
            deleteOldImageFile(match.getImage_url());

            // Finally delete the match
            matchRepo.delete(match);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to delete match: " + ex.getMessage(), ex);
        }
    }

    @Transactional
    @Override
    public MatchStatsDTO saveMatchStatistics(MatchStatsDTO dto) {
        try {
            MatchDTO matchDTO = dto.getMatch();
            MatchStatsDTO saved = new MatchStatsDTO();

//            // --- Step 1: Save Match ---
//            Match matchEntity = mapper.map(dto.getMatch(), Match.class);

            // Step 1: Get existing match from DB
            Match existingMatchEntity = matchRepo.findById(dto.getMatch().getId())
                    .orElseThrow(() -> new NotFoundException("Match not found with id: " + dto.getMatch().getId()));

            // If opponent/team references exist, fetch them from DB
            if (dto.getMatch().getOpponent() != null && dto.getMatch().getOpponent().getId() != null) {
                Team opponent = teamRepo.findById(dto.getMatch().getOpponent().getId())
                        .orElseThrow(() -> new NotFoundException("Opponent team not found with id: " + dto.getMatch().getOpponent().getId()));
                existingMatchEntity.setOpponent(opponent);
            }

            // Update fields (keep existing values if null in matchDTO)
            if (matchDTO.getDescription() != null) existingMatchEntity.setDescription(matchDTO.getDescription());
            if (matchDTO.getDate_time() != null) existingMatchEntity.setDate_time(matchDTO.getDate_time());
            if (matchDTO.getVenue() != null) existingMatchEntity.setVenue(matchDTO.getVenue());
            if (matchDTO.getOvers_per_inning() != 0)
                existingMatchEntity.setOvers_per_inning(matchDTO.getOvers_per_inning());
            if (matchDTO.getStatus() != null) existingMatchEntity.setStatus(matchDTO.getStatus());
            if (matchDTO.getMatch_type() != null) existingMatchEntity.setMatch_type(matchDTO.getMatch_type());
            if (matchDTO.getResult() != null) existingMatchEntity.setResult(matchDTO.getResult());
            if (matchDTO.getScore() != null) existingMatchEntity.setScore(matchDTO.getScore());
            if (matchDTO.getOpponent_score() != null)
                existingMatchEntity.setOpponent_score(matchDTO.getOpponent_score());

            Match savedMatch = matchRepo.save(existingMatchEntity);
            saved.setMatch(mapper.map(savedMatch, MatchDTO.class));

            // --- Step 2: Save Inning ---
            Inning inningEntity = mapper.map(dto.getInning(), Inning.class);
            inningEntity.setMatch(savedMatch);

            // Fetch batting team from DB
            if (dto.getInning().getBatting_team() != null && dto.getInning().getBatting_team().getId() != null) {
                Team battingTeam = teamRepo.findById(dto.getInning().getBatting_team().getId())
                        .orElseThrow(() -> new NotFoundException("Batting team not found with id: " + dto.getInning().getBatting_team().getId()));
                inningEntity.setBatting_team(battingTeam);
            }

            Inning savedInning = inningRepo.save(inningEntity);
            saved.setInning(mapper.map(savedInning, InningDTO.class));

            // --- Step 3: Save Batting Stats ---
            List<BattingPerformanceDTO> saved_battingStats = new ArrayList<>();
            if (dto.getBatting_stats() != null) {
                for (BattingPerformanceDTO bpDto : dto.getBatting_stats()) {
                    BattingPerformance bpEntity = mapper.map(bpDto, BattingPerformance.class);

                    // Fetch actual player from DB
                    Player player = playerRepo.findById(bpDto.getPlayer_id())
                            .orElseThrow(() -> new NotFoundException("Player not found with id: " + bpDto.getPlayer_id()));

                    bpEntity.setPlayer(player);
                    bpEntity.setInning(savedInning);

                    battingPerformanceRepo.save(bpEntity);
                    saved_battingStats.add(mapper.map(bpEntity, BattingPerformanceDTO.class));
                }
                saved.setBatting_stats(saved_battingStats);
            }

            // --- Step 4: Save Bowling Stats ---
            List<BowlingPerformanceDTO> saved_bowlingStats = new ArrayList<>();
            if (dto.getBowling_stats() != null) {
                for (BowlingPerformanceDTO bowlDto : dto.getBowling_stats()) {
                    BowlingPerformance bowlEntity = mapper.map(bowlDto, BowlingPerformance.class);

                    Player player = playerRepo.findById(bowlDto.getPlayer_id())
                            .orElseThrow(() -> new NotFoundException("Player not found with id: " + bowlDto.getPlayer_id()));

                    bowlEntity.setPlayer(player);
                    bowlEntity.setInning(savedInning);

                    bowlingPerformanceRepo.save(bowlEntity);
                    saved_bowlingStats.add(mapper.map(bowlEntity, BowlingPerformanceDTO.class));
                }
                saved.setBowling_stats(saved_bowlingStats);
            }

            // --- Step 5: Save Fielding Stats ---
            List<FieldingPerformanceDTO> saved_fieldingStats = new ArrayList<>();
            if (dto.getFielding_stats() != null) {
                for (FieldingPerformanceDTO fieldDto : dto.getFielding_stats()) {
                    FieldingPerformance fieldEntity = mapper.map(fieldDto, FieldingPerformance.class);

                    Player player = playerRepo.findById(fieldDto.getPlayer_id())
                            .orElseThrow(() -> new NotFoundException("Player not found with id: " + fieldDto.getPlayer_id()));

                    fieldEntity.setPlayer(player);
                    fieldEntity.setMatch(savedMatch);

                    fieldingPerformanceRepo.save(fieldEntity);
                    saved_fieldingStats.add(mapper.map(fieldEntity, FieldingPerformanceDTO.class));
                }
                saved.setFielding_stats(saved_fieldingStats);
            }

            // --- Step 6: Return DTO (optional: map saved entities back) ---
            return mapper.map(saved, MatchStatsDTO.class);

        } catch (Exception ex) {
            // Rollback transaction
            throw new RuntimeException("Failed to save match stats: " + ex.getMessage(), ex); // Spring @Transactional will rollback automatically
        }
    }

    @Override
    public MatchDTO updateScheduledMatch(Long id, MatchDTO dto) {
        Match existingMatch = matchRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Match not found with id: " + id));

        // Fetch actual opponent team from DB to avoid null fields
        if (dto.getOpponent() != null && dto.getOpponent().getId() != null) {
            Team away_team = teamRepo.findById(dto.getOpponent().getId()).orElseThrow(() -> new NotFoundException("Opponent Team not found with id: " + dto.getOpponent().getId()));
            existingMatch.setOpponent(away_team);
        }

        // Set scheduled_by (Admin)
        if (dto.getScheduled_by() != null && dto.getScheduled_by().getId() != null) {
            Admin admin = (Admin) userRepo.findById(dto.getScheduled_by().getId())
                    .orElseThrow(() -> new NotFoundException("Admin not found with id: " + dto.getScheduled_by().getId()));
            existingMatch.setScheduled_by(admin);
        }

        // Update fields (keep existing values if null in dto)
        if (dto.getDescription() != null) existingMatch.setDescription(dto.getDescription());
        if (dto.getDate_time() != null) existingMatch.setDate_time(dto.getDate_time());
        if (dto.getVenue() != null) existingMatch.setVenue(dto.getVenue());
        if (dto.getStatus() != null) existingMatch.setStatus(dto.getStatus());
        if (dto.getMatch_type() != null) existingMatch.setMatch_type(dto.getMatch_type());

        Match updated = matchRepo.save(existingMatch);
        return mapper.map(updated, MatchDTO.class);
    }

    private String saveMatchImage(MultipartFile imageFile) {
        return uploadImageUtil.saveImage(uploadDir, "matches", imageFile);
    }

    private void deleteOldImageFile(String oldImageUrl) {
        if (oldImageUrl != null) {
            try {
                Files.deleteIfExists(Paths.get(oldImageUrl));
            } catch (IOException e) {
                throw new ImageFileException("Failed to delete match image: " + e.getMessage());
            }
        }
    }
}
