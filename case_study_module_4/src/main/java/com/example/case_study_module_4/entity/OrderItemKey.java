package com.example.case_study_module_4.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemKey implements Serializable {
    private Long orderId;
    private Long foodId;

    // override equals và hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItemKey)) return false;
        OrderItemKey that = (OrderItemKey) o;
        return orderId.equals(that.orderId) && foodId.equals(that.foodId);
    }

    @Override
    public int hashCode() {
        return orderId.hashCode() + foodId.hashCode();
    }
}

