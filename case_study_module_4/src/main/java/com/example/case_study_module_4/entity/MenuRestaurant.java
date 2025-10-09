package com.example.case_study_module_4.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity(name = "menu_restaurant")
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
}