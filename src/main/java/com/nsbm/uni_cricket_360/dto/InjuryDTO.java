package com.nsbm.uni_cricket_360.dto;

import com.nsbm.uni_cricket_360.enums.InjuryStatus;
import com.nsbm.uni_cricket_360.enums.InjuryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class InjuryDTO {
    private Long id;
    private PlayerDTO player;

    @Enumerated(EnumType.STRING)
    private InjuryType injury_type;

    private LocalDate date_reported;
    private int recovery_days;

    @Enumerated(EnumType.STRING)
    private InjuryStatus status;
}
