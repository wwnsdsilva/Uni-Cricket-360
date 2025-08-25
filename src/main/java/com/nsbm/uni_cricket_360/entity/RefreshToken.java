package com.nsbm.uni_cricket_360.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length=200)
    private String token;          // store as random string; optionally hash

    @Column(nullable=false)
    private Long userId;

    @Column(nullable=false)
    private Instant expires_at;

    @Column(nullable=false)
    private boolean revoked = false;

    private Instant created_at = Instant.now();
    private Instant revoked_at;
    private String replaced_by;     // token string of the new rotated token

}
