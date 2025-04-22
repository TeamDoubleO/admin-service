package com.doubleo.adminservice.domain.admin.domain;

import com.doubleo.adminservice.domain.common.model.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Admin extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long id;

    @Column(name = "admin_company")
    private String company;

    @Column(name = "admin_account_id")
    private String accountId; // 정규화 추가

    @Column(name = "admin_password")
    private String password;

    @Builder(access = AccessLevel.PRIVATE)
    private Admin(String company, String accountId, String password) {
        this.company = company;
        this.accountId = accountId;
        this.password = password;
    }

    public static Admin createAdmin(String company, String accountId, String password) {
        return Admin.builder().company(company).accountId(accountId).password(password).build();
    }
}
