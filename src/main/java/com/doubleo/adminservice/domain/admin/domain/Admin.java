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
    @Column(name = "admin_id", nullable = false)
    private Long id;

    @Column(name = "admin_username", nullable = false, unique = true)
    private String username;

    @Column(name = "admin_password", nullable = false, length = 100)
    private String password;

    @Column(name = "admin_name", nullable = false)
    private String name;

    @Column(name = "admin_contact", nullable = false)
    private String contact;

    @Builder(access = AccessLevel.PRIVATE)
    private Admin(String username, String password, String name, String contact) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.contact = contact;
    }

    public static Admin createAdmin(String username, String password, String name, String contact) {
        return Admin.builder()
                .username(username)
                .password(password)
                .name(name)
                .contact(contact)
                .build();
    }

    public void updateAdminPassword(String passwordNew) {
        this.password = passwordNew;
    }
}
