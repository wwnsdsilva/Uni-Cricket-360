package com.nsbm.uni_cricket_360.service.impl;

import com.nsbm.uni_cricket_360.dto.AttendanceDTO;
import com.nsbm.uni_cricket_360.dto.InjuryDTO;
import com.nsbm.uni_cricket_360.entity.Attendance;
import com.nsbm.uni_cricket_360.entity.Injury;
import com.nsbm.uni_cricket_360.entity.Player;
import com.nsbm.uni_cricket_360.entity.TrainingSession;
import com.nsbm.uni_cricket_360.exception.NotFoundException;
import com.nsbm.uni_cricket_360.repository.AttendanceRepo;
import com.nsbm.uni_cricket_360.repository.InjuryRepo;
import com.nsbm.uni_cricket_360.repository.PlayerRepo;
import com.nsbm.uni_cricket_360.repository.TrainingSessionRepo;
import com.nsbm.uni_cricket_360.service.AttendanceService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepo attendanceRepo;

    @Autowired
    private TrainingSessionRepo trainingSessionRepo;

    @Autowired
    private PlayerRepo playerRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<AttendanceDTO> getAllAttendance() {
        return mapper.map(attendanceRepo.findAll(), new TypeToken<List<AttendanceDTO>>() {
        }.getType());
    }

    @Override
    public AttendanceDTO searchAttendanceById(Long id) {
        Attendance attendance = attendanceRepo.findById(id).orElseThrow(() -> new NotFoundException("Attendance record not found with id " + id));
        return mapper.map(attendance, AttendanceDTO.class);
    }

    @Override
    public AttendanceDTO saveAttendance(AttendanceDTO dto) {
        Attendance attendance = mapper.map(dto, Attendance.class);

        // Set player from DB
        if (dto.getPlayer() != null && dto.getPlayer().getId() != null) {
            Player player = playerRepo.findById(dto.getPlayer().getId())
                    .orElseThrow(() -> new NotFoundException("Player not found with id: " + dto.getPlayer().getId()));
            attendance.setPlayer(player);
        }

        // Set training session from DB
        if (dto.getSession() != null && dto.getSession().getId() != null) {
            TrainingSession trainingSession = trainingSessionRepo.findById(dto.getSession().getId())
                    .orElseThrow(() -> new NotFoundException("Training session not found with id: " + dto.getSession().getId()));
            attendance.setSession(trainingSession);
        }

        Attendance saved = attendanceRepo.save(attendance);
        return mapper.map(saved, AttendanceDTO.class);
    }

    @Override
    public AttendanceDTO updateAttendance(Long id, AttendanceDTO dto) {
        Attendance existingAttendance = attendanceRepo.findById(id).orElseThrow(() -> new NotFoundException("Attendance records not found with id " + id));

        // Set player from DB
        if (dto.getPlayer() != null && dto.getPlayer().getId() != null) {
            Player player = playerRepo.findById(dto.getPlayer().getId())
                    .orElseThrow(() -> new NotFoundException("Player not found with id: " + dto.getPlayer().getId()));
            existingAttendance.setPlayer(player);
        }

        // Set training session from DB
        if (dto.getSession() != null && dto.getSession().getId() != null) {
            TrainingSession trainingSession = trainingSessionRepo.findById(dto.getSession().getId())
                    .orElseThrow(() -> new NotFoundException("Training session not found with id: " + dto.getSession().getId()));
            existingAttendance.setSession(trainingSession);
        }

        existingAttendance.setStatus(dto.getStatus());

        Attendance saved = attendanceRepo.save(existingAttendance);
        return mapper.map(saved, AttendanceDTO.class);
    }

    @Override
    public void deleteAttendance(Long id) {
        Attendance attendance = attendanceRepo.findById(id).orElseThrow(() -> new NotFoundException("Attendance record not found with id " + id));
        attendanceRepo.delete(attendance);
    }
}
