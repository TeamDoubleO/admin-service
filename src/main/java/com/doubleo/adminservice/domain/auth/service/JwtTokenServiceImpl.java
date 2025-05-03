package com.doubleo.adminservice.domain.auth.service;

import com.doubleo.adminservice.domain.auth.domain.BlackListToken;
import com.doubleo.adminservice.domain.auth.domain.RefreshToken;
import com.doubleo.adminservice.domain.auth.dto.AccessTokenDto;
import com.doubleo.adminservice.domain.auth.dto.RefreshTokenDto;
import com.doubleo.adminservice.domain.auth.repository.BlackListTokenRepository;
import com.doubleo.adminservice.domain.auth.repository.RefreshTokenRepository;
import com.doubleo.adminservice.global.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final BlackListTokenRepository blackListTokenRepository;

    public AccessTokenDto createAccessTokenDto(Long adminId) {
        return jwtUtil.generateAccessTokenDto(adminId);
    }

    public String createAccessToken(Long adminId) {
        return jwtUtil.generateAccessToken(adminId);
    }

    public String createRefreshToken(Long adminId) {
        String token = jwtUtil.generateRefreshToken(adminId);
        RefreshToken refreshToken =
                RefreshToken.builder()
                        .adminId(adminId)
                        .token(token)
                        .ttl(jwtUtil.getRefreshTokenExpirationTime())
                        .build();
        refreshTokenRepository.save(refreshToken);

        return token;
    }

    public RefreshTokenDto retrieveRefreshToken(String refreshTokenValue) {
        RefreshTokenDto refreshTokenDto = parseRefreshToken(refreshTokenValue);

        if (refreshTokenDto == null) {
            return null;
        }

        Optional<RefreshToken> refreshToken = getRefreshToken(refreshTokenDto.adminId());

        if (refreshToken.isPresent()
                && refreshTokenDto.refreshTokenValue().equals(refreshToken.get().getToken())) {
            return refreshTokenDto;
        }

        return null;
    }

    public AccessTokenDto reissueAccessTokenIfExpired(String accessTokenValue) {
        try {
            jwtUtil.parseAccessToken(accessTokenValue);
            return null;
        } catch (ExpiredJwtException e) {
            Long adminId = Long.parseLong(e.getClaims().getSubject());

            return createAccessTokenDto(adminId);
        }
    }

    public void putAccessTokenOnBlackList(String accessTokenValue) {

        String accessToken = jwtUtil.resolveToken(accessTokenValue);
        if (accessToken == null) {
            return;
        }

        long remainingMs = jwtUtil.getRemainingExpirationMillis(accessToken);
        long ttlSeconds = remainingMs > 0 ? remainingMs / 1000 : 0;

        BlackListToken black = BlackListToken.createBlackListToken(accessToken, ttlSeconds);
        blackListTokenRepository.save(black);
    }

    private RefreshTokenDto parseRefreshToken(String refreshTokenValue) {
        try {
            return jwtUtil.parseRefreshToken(refreshTokenValue);
        } catch (Exception e) {
            return null;
        }
    }

    private Optional<RefreshToken> getRefreshToken(Long adminId) {
        return refreshTokenRepository.findById(adminId);
    }
}
