package com.nsbm.uni_cricket_360.dto;

import com.nsbm.uni_cricket_360.enums.InjuryStatus;
import com.nsbm.uni_cricket_360.enums.InjuryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class InjuryDTO {
    private Long id;
    private PlayerDTO player;
    private InjuryType injuryType;
    private LocalDate dateReported;
    private int recoveryDays;
    private InjuryStatus status;
}
