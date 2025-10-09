package com.example.case_study_module_4.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

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

    // Liên kết với MenuRestaurant
    @OneToMany(mappedBy = "food", fetch = FetchType.LAZY)
    private Set<MenuRestaurant> menuRestaurants;

    // Lấy Restaurant chính (ví dụ món ăn chỉ gắn 1 nhà hàng)
    public Restaurant getRestaurant() {
        if (menuRestaurants != null && !menuRestaurants.isEmpty()) {
            return menuRestaurants.iterator().next().getRestaurant();
        }
        return null;
    }
}
