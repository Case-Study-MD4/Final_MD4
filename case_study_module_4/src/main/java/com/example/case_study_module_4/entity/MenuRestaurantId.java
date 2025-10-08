package com.example.case_study_module_4.entity;

// MenuRestaurantId.java
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class MenuRestaurantId implements Serializable {
    private Long resId;
    private Long foodId;

    // constructor, equals & hashCode
    public MenuRestaurantId() {}
    public MenuRestaurantId(Long resId, Long foodId) {
        this.resId = resId;
        this.foodId = foodId;
    }
}
