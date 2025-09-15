package com.nsbm.uni_cricket_360.dto;

import com.nsbm.uni_cricket_360.enums.SessionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class TrainingSessionBasicDTO {
    private Long id;
    private String description;
    private LocalDateTime date_time;
    private String title;
    private String venue;
    private SessionStatus status;


    public TrainingSessionBasicDTO(Long id) {
        this.id = id;
    }

    public TrainingSessionBasicDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
