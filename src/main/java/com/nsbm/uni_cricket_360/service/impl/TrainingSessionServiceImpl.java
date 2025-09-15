package com.nsbm.uni_cricket_360.service.impl;

import com.nsbm.uni_cricket_360.dto.TrainingAttendanceDTO;
import com.nsbm.uni_cricket_360.dto.TrainingSessionBasicDTO;
import com.nsbm.uni_cricket_360.dto.TrainingSessionDTO;
import com.nsbm.uni_cricket_360.entity.Attendance;
import com.nsbm.uni_cricket_360.entity.Player;
import com.nsbm.uni_cricket_360.entity.TrainingSession;
import com.nsbm.uni_cricket_360.enums.AttendanceStatus;
import com.nsbm.uni_cricket_360.enums.SessionStatus;
import com.nsbm.uni_cricket_360.exception.NotFoundException;
import com.nsbm.uni_cricket_360.repository.AttendanceRepo;
import com.nsbm.uni_cricket_360.repository.PlayerRepo;
import com.nsbm.uni_cricket_360.repository.TrainingSessionRepo;
import com.nsbm.uni_cricket_360.service.TrainingSessionService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TrainingSessionServiceImpl implements TrainingSessionService {

    @Autowired
    private TrainingSessionRepo trainingSessionRepo;

    @Autowired
    private PlayerRepo playerRepo;

    @Autowired
    private AttendanceRepo attendanceRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<TrainingSessionBasicDTO> getAllTrainingSessions() {
        return mapper.map(trainingSessionRepo.findAll(), new TypeToken<List<TrainingSessionBasicDTO>>() {
        }.getType());
    }

    @Override
    public List<TrainingSessionDTO> getAllSessionsWithAttendance() {
        List<TrainingSession> sessions = trainingSessionRepo.findAllWithAttendance();

        return sessions.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private TrainingSessionDTO mapToDTO(TrainingSession session) {
        List<TrainingAttendanceDTO> trainingAttendanceDTOs = session.getAttendance().stream()
                .map(att -> new TrainingAttendanceDTO(
                        att.getPlayer().getId(),
                        att.getPlayer().getFirst_name() + " " + att.getPlayer().getLast_name(),
                        att.getStatus()
                ))
                .collect(Collectors.toList());

        return new TrainingSessionDTO(
                session.getId(),
                session.getDescription(),
                session.getDate_time(),
                session.getTitle(),
                session.getVenue(),
                session.getStatus(),
                trainingAttendanceDTOs
        );
    }

    @Override
    public TrainingSessionDTO searchSessionById(Long id) {
        TrainingSession trainingSession = trainingSessionRepo.findById(id).orElseThrow(() -> new NotFoundException("Training session not found with id " + id));
        return mapper.map(trainingSession, TrainingSessionDTO.class);
    }

    @Override
    @Transactional
    public TrainingSessionDTO saveSession(TrainingSessionDTO dto) {
//        return mapper.map(trainingSessionRepo.save(mapper.map(dto, TrainingSession.class)), TrainingSessionDTO.class);

        try {
            // 1. Map DTO → Entity
            TrainingSession session = mapper.map(dto, TrainingSession.class);

            // 2. Save the training session first
            TrainingSession savedSession = trainingSessionRepo.save(session);

            // 3. Fetch all players from DB
            List<Player> players = playerRepo.findAll();

            // 4. Create attendance records for each player
            List<Attendance> attendanceList = players.stream()
                    .map(player -> {
                        Attendance att = new Attendance();
                        att.setPlayer(player);
                        att.setSession(savedSession);
                        att.setStatus(AttendanceStatus.ABSENT); // default
                        return att;
                    })
                    .collect(Collectors.toList());

            // 5. Save all attendance records
            attendanceRepo.saveAll(attendanceList);

            // 6. Attach attendances to session (so DTO includes them if needed)
            savedSession.setAttendance(attendanceList);

            // 7. Return DTO
            return mapToDTO(savedSession);

        } catch (Exception ex) {
            throw new RuntimeException("Failed to save training session: " + ex.getMessage(), ex);
        }
    }

    @Override
    public TrainingSessionBasicDTO updateSession(Long id, TrainingSessionBasicDTO dto) {
        TrainingSession existingTrainingSession = trainingSessionRepo.findById(id).orElseThrow(() -> new NotFoundException("Training session not found with id " + id));

        existingTrainingSession.setDescription(dto.getDescription());
        existingTrainingSession.setDate_time(dto.getDate_time());
        existingTrainingSession.setTitle(dto.getTitle());
        existingTrainingSession.setVenue(dto.getVenue());
        existingTrainingSession.setStatus(dto.getStatus());
        return mapper.map(trainingSessionRepo.save(existingTrainingSession), TrainingSessionBasicDTO.class);
    }

    @Override
    public void deleteSession(Long id) {
        TrainingSession trainingSession = trainingSessionRepo.findById(id).orElseThrow(() -> new NotFoundException("Training session not found with id " + id));
        trainingSessionRepo.delete(trainingSession);
    }

    @Override
    public int getTrainingSessionCount(SessionStatus status) {
        try {
            return trainingSessionRepo.findAllByStatus(status).size();

        } catch (Exception ex) {
            throw new RuntimeException("Failed to fetch the session count: " + ex.getMessage(), ex);
        }
    }
}
