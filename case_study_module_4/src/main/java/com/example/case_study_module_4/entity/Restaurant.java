package com.example.case_study_module_4.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String subtitle;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String image;

    @Column(name = "is_freeship")
    private Boolean isFreeship = false;

    private String address;

    @Column(name = "open_date")
    private LocalDateTime openDate;

    @ManyToMany
    @JoinTable(
            name = "menu_restaurant",
            joinColumns = @JoinColumn(name = "res_id"),
            inverseJoinColumns = @JoinColumn(name = "food_id")
    )
    private List<Food> foods;

    // 🟢 Thêm liên kết đến Account (chủ nhà hàng)
    @ManyToOne
    @JoinColumn(name = "owner_account_id", referencedColumnName = "id")
    private Account account;
}
