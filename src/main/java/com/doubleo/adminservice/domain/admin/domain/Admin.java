package com.doubleo.adminservice.domain.admin.domain;

import com.doubleo.adminservice.domain.common.model.BaseTimeEntity;
import jakarta.persistence.*;

@Entity
public class Admin extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long id;
}
