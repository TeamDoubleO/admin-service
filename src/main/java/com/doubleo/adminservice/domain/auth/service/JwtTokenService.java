package com.doubleo.adminservice.domain.auth.service;

import com.doubleo.adminservice.domain.auth.dto.AccessTokenDto;
import com.doubleo.adminservice.domain.auth.dto.RefreshTokenDto;

public interface JwtTokenService {

    // AccessToken DTO 생성
    AccessTokenDto createAccessTokenDto(Long adminId);

    // AccessToken 생성
    String createAccessToken(Long adminId);

    // RefreshToken 생성
    String createRefreshToken(Long adminId);

    // DB 저장된 RefreshToken 조회 및 검증
    RefreshTokenDto retrieveRefreshToken(String refreshTokenValue);

    // AccessToken 만료 여부 검증 후 재발급
    AccessTokenDto reissueAccessTokenIfExpired(String accessTokenValue);

    // 사용하지 않는 AccessToken BlackList 등록
    void putAccessTokenOnBlackList(String accessTokenValue);
}
