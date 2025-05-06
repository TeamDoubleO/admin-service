package com.doubleo.adminservice.global.util;

import com.doubleo.adminservice.domain.model.Tenant;
import com.doubleo.adminservice.global.exception.CommonException;
import com.doubleo.adminservice.global.exception.errorcode.TenantErrorCode;
import com.doubleo.tenantcontext.TenantContextHolder;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class TenantValidator<T extends Tenant> {

    public String getTenantId() {
        return Optional.ofNullable(TenantContextHolder.getTenantId())
                .orElseThrow(() -> new CommonException(TenantErrorCode.TENANT_ID_NOT_FOUND));
    }

    public T validateTenant(T entity) {
        String currentTenantId = getTenantId();
        if (!entity.getTenantId().equals(currentTenantId)) {
            throw new CommonException(TenantErrorCode.INVALID_TENANT_ID);
        }
        return entity;
    }
}
