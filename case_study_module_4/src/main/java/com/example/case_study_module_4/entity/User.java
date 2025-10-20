package com.example.case_study_module_4.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@Getter @Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Cột users.account_id (UNIQUE) đã có trong DB → đây là "owner" của quan hệ
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false, unique = true)
    @ToString.Exclude
    private Account account;

    @Column(nullable = false, length = 100)
    private String fullname;

    private String avatar;
    private String phone;
    private String address;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "active_email")
    private Boolean activeEmail = true;
    // tiện ích: đồng bộ 2 chiều (nếu bạn set từ User)
    public void setAccount(Account account) {
        this.account = account;
        if (account != null && account.getUser() != this) {
            account.setUser(this);
        }
    }
}
