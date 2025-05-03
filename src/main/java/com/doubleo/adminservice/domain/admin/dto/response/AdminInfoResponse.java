package com.doubleo.adminservice.domain.admin.dto.response;

import com.doubleo.adminservice.domain.admin.domain.Admin;
import io.swagger.v3.oas.annotations.media.Schema;

public record AdminInfoResponse(
        @Schema(description = "관리자 ID", example = "1") Long adminId,
        @Schema(description = "관리자 username", example = "example@gmail.com") String username,
        @Schema(description = "관리자 이름", example = "정선우") String name,
        @Schema(description = "관리자 연락처", example = "010-1234-5678") String contact) {
    public static AdminInfoResponse of(Admin admin) {
        return new AdminInfoResponse(
                admin.getId(), admin.getUsername(), admin.getName(), admin.getContact());
    }
}
