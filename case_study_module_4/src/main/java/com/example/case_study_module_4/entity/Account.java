package com.example.case_study_module_4.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "accounts")
@Getter @Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id") // join sang roles.id
    private Role role;


    // Quan hệ ngược One-to-One (KHÔNG tạo cột mới; dùng mappedBy trỏ sang field ở User)
    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private User user;

    // tiện ích: đồng bộ 2 chiều (nếu bạn set từ Account)
    public void setUser(User user) {
        this.user = user;
        if (user != null && user.getAccount() != this) {
            user.setAccount(this);
        }
    }

    // Spring Security hay gọi getUsername()
    public String getUsername() { return userName; }
}
