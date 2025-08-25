package com.nsbm.uni_cricket_360.service.impl;

import com.nsbm.uni_cricket_360.entity.RefreshToken;
import com.nsbm.uni_cricket_360.repository.RefreshTokenRepo;
import com.nsbm.uni_cricket_360.repository.UserRepo;
import com.nsbm.uni_cricket_360.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Autowired
    RefreshTokenRepo refreshTokenRepo;

    @Autowired
    UserRepo userRepo;

    @Value("${refresh.token.ttl}")
    private Duration refreshTtl;

    /**
     * Creates a new refresh token for a given user.
     * Generates a long random token string and sets its expiration time based on configured TTL.
     *
     * @param userId the ID of the user to create the token for
     * @return the saved RefreshToken entity
     */
    @Override
    public RefreshToken createForUser(Long userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString() + UUID.randomUUID()); // long random
        refreshToken.setUserId(userId);
        refreshToken.setExpires_at(Instant.now().plus(Duration.ofDays(refreshTtl.toDays())));
        return refreshTokenRepo.save(refreshToken);
    }

    /**
     * Verifies that a given refresh token is valid and usable.
     * Throws RuntimeException if the token does not exist, is revoked, or has expired.
     *
     * @param token the refresh token string
     * @return the RefreshToken entity if valid
     * @throws RuntimeException if the token is invalid, revoked, or expired
     */
    @Override
    public RefreshToken verifyUsable(String token) {
        RefreshToken refreshToken = refreshTokenRepo.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
        if (refreshToken.isRevoked() || refreshToken.getExpires_at().isBefore(Instant.now())) {
            throw new RuntimeException("Refresh token has been expired or revoked. Try login again.");
        }
        return refreshToken;
    }

    /**
     * Rotates an old refresh token.
     * Marks the old token as revoked, creates a new token, and links the old token to the new one.
     *
     * @param oldRefreshToken the old RefreshToken entity to rotate
     * @return the newly created RefreshToken
     */
    @Override
    public RefreshToken rotate(RefreshToken oldRefreshToken) {
        // mark old as revoked, create new, link
        oldRefreshToken.setRevoked(true);
        oldRefreshToken.setRevoked_at(Instant.now());
        RefreshToken newRt = createForUser(oldRefreshToken.getUserId());
        oldRefreshToken.setReplaced_by(newRt.getToken());
        refreshTokenRepo.save(oldRefreshToken);
        return newRt;
    }

    /**
     * Revokes a single refresh token by marking it as revoked and updating revoked timestamp.
     *
     * @param token the refresh token string to revoke
     */
    @Override
    public void revokeToken(String token) {
        refreshTokenRepo.findByToken(token).ifPresent(refreshToken -> {
            refreshToken.setRevoked(true);
            refreshToken.setRevoked_at(Instant.now());
            refreshTokenRepo.save(refreshToken);
        });
    }

    /**
     * Revokes all refresh tokens for a given user that are not already revoked.
     * Marks each token as revoked and updates the revoked timestamp.
     *
     * @param userId the ID of the user whose tokens should be revoked
     */
    @Override
    public void revokeAllForUser(Long userId) {
        refreshTokenRepo.findAll().stream()
                .filter(refreshToken -> refreshToken.getUserId().equals(userId) && !refreshToken.isRevoked())
                .forEach(refreshToken -> {
                    refreshToken.setRevoked(true);
                    refreshToken.setRevoked_at(Instant.now());
                    refreshTokenRepo.save(refreshToken);
                });
    }
}
