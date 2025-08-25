package com.nsbm.uni_cricket_360.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class EventDTO {
    private Long id;
    private String event_title;
    private LocalDateTime date_time;
    private String venue;
    private String description;
    private String image_url;
    private AdminDTO created_by;
}
