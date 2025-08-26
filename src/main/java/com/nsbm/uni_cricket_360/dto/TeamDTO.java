package com.nsbm.uni_cricket_360.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class TeamDTO {
    private Long id;
    private String team_name;
    private String venue;

    public TeamDTO(Long id) {
        this.id = id;
    }

    public TeamDTO(String team_name, String venue) {
        this.team_name = team_name;
        this.venue = venue;
    }
}
