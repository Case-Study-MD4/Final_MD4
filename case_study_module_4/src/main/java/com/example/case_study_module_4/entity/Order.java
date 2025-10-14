package com.example.case_study_module_4.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "res_id")
    private Restaurant restaurant;

    private LocalDateTime createDate;

    private BigDecimal totalPrice;

    @Column(name = "status")
    private Integer status;

    @Transient
    public Set<Restaurant> getRestaurants() {
        if (items == null) return Collections.emptySet();
        return items.stream()
                .map(item -> item.getFood().getRestaurant())
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();
    public String getStatusText() {
        return switch (status) {
            case 0 -> "Mới";
            case 1 -> "Đang chờ";
            case 2 -> "Hoàn thành";
            case 3 -> "Đã hủy";
            default -> "Không xác định";
        };
    }

}

