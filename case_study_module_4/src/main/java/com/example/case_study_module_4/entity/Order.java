package com.example.case_study_module_4.entity;

import com.example.case_study_module_4.common.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
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

    @OneToMany(mappedBy = "order")
    private List<OrderItem> items;
}

