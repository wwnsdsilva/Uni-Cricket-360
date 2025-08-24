package com.nsbm.uni_cricket_360.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventTitle;
    private LocalDateTime dateTime;
    private String venue;
    private String description;
    private String imageUrl;

    @ManyToOne
    @JoinColumn (name = "created_by", referencedColumnName = "id", nullable = false)
    private Admin createdBy;
}
