package com.doubleo.adminservice.domain.auth.dto;

public record AccessTokenDto(Long adminId, String accessTokenValue) {
    public static AccessTokenDto of(Long adminId, String accessTokenValue) {
        return new AccessTokenDto(adminId, accessTokenValue);
    }
}
