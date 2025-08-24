package com.nsbm.uni_cricket_360.dto;

import com.nsbm.uni_cricket_360.enums.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class AttendanceDTO {
    private Long id;
    private PlayerDTO player;
    private TrainingSessionDTO session;
    private AttendanceStatus status;
}
