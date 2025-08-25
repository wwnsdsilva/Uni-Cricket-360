package com.nsbm.uni_cricket_360.service;

import com.nsbm.uni_cricket_360.entity.RefreshToken;

public interface RefreshTokenService {
    RefreshToken createForUser(Long userId);

    RefreshToken verifyUsable(String token);

    RefreshToken rotate(RefreshToken oldRefreshToken);

    void revokeToken(String token);

    void revokeAllForUser(Long userId);
}
