package com.nsbm.uni_cricket_360.dto;

import com.nsbm.uni_cricket_360.enums.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class TrainingAttendanceDTO {
    private Long player_id;
    private String name;
//    private String first_name;
//    private String last_name;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;
}
