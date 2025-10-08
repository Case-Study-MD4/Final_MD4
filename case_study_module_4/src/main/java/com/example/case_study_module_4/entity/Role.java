package com.example.case_study_module_4.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter @Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")            // hoặc bỏ hẳn @Column vì default đã là "id"
    private Long id;

    @Column(name = "role_name", nullable = false)
    private String roleName;


}

