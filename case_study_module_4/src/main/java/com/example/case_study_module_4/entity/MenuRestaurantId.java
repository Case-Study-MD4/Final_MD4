package com.example.case_study_module_4.entity;

// MenuRestaurantId.java
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuRestaurantId that = (MenuRestaurantId) o;
        return Objects.equals(resId, that.resId) &&
                Objects.equals(foodId, that.foodId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resId, foodId);
    }
}
