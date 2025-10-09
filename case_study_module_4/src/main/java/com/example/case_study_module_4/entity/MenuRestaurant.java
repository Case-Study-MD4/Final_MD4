/*
package com.example.case_study_module_4.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Table(name = "menu_restaurant")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuRestaurant {
    @EmbeddedId
    private MenuRestaurantId id;

    @ManyToOne
    @MapsId("resId")
    private Restaurant restaurant;

    @ManyToOne
    @MapsId("foodId")
    private Food food;

    private boolean isAvailable;
    private int position;
    private LocalDateTime createDate;
}*/



package com.example.case_study_module_4.entity;
import jakarta.persistence.*;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "menu_restaurant")
public class MenuRestaurant {
    @EmbeddedId
    private MenuRestaurantId id;
    @ManyToOne
    @MapsId("resId") // ánh xạ với resId trong EmbeddedId
    @JoinColumn(name = "res_id")
    private Restaurant restaurant;
    @ManyToOne
    @MapsId("foodId") // ánh xạ với foodId trong EmbeddedId
    @JoinColumn(name = "food_id")
    private Food food;
    private Boolean isAvailable = true;
    private Integer position;
}









