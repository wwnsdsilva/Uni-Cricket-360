package com.nsbm.uni_cricket_360.controller;

import com.nsbm.uni_cricket_360.dto.AttendanceDTO;
import com.nsbm.uni_cricket_360.service.AttendanceService;
import com.nsbm.uni_cricket_360.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1/attendance")
public class AttendanceController {

    @Autowired
    AttendanceService attendanceService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getAllAttendance() {
        return new ResponseUtil(
                200,
                "OK",
                attendanceService.getAllAttendance()
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil searchAttendanceById(@PathVariable Long id) {
        return new ResponseUtil(
                200,
                "Attendance details fetched successfully!",
                attendanceService.searchAttendanceById(id)
        );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil saveAttendance(@RequestBody AttendanceDTO dto) {
        return new ResponseUtil(
                201,
                "Attendance saved successfully..!",
                attendanceService.saveAttendance(dto)
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil updateAttendance(@PathVariable Long id, @RequestBody AttendanceDTO dto) {
        return new ResponseUtil(
                200,
                "Attendance updated successfully!",
                attendanceService.updateAttendance(id, dto)
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = "/mark", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil markAttendance(@RequestBody AttendanceDTO dto) {
        attendanceService.markAttendance(dto);
        return new ResponseUtil(
                200,
                "Attendance marked successfully!",
                null
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil deleteAttendance(@PathVariable Long id) {
        attendanceService.deleteAttendance(id);
        return new ResponseUtil(
                200,
                "Attendance deleted successfully!",
                null
        );
    }
}
