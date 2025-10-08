package com.example.case_study_module_4.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

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


//    @OneToOne
//    @JoinColumn(name = "owner_account_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_restaurant_owner"))
//    private Account ownerAccount;
}
