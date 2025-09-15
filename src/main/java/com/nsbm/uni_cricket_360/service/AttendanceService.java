package com.nsbm.uni_cricket_360.service;

import com.nsbm.uni_cricket_360.dto.AttendanceDTO;

import java.util.List;

public interface AttendanceService {

    List<AttendanceDTO> getAllAttendance();

    AttendanceDTO searchAttendanceById(Long id);

    AttendanceDTO saveAttendance(AttendanceDTO dto);

    AttendanceDTO updateAttendance(Long id, AttendanceDTO dto);

    void deleteAttendance(Long id);

    void markAttendance(AttendanceDTO dto);
}
