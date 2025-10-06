package com.example.case_study_module_4.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class  User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fullname;
    private String phone;
    private String address;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;
}

