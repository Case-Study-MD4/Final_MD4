package com.example.case_study_module_4.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "foods")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String image;
    private String timeShip;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "cate_id", referencedColumnName = "id")
    private Categories categories;

}

