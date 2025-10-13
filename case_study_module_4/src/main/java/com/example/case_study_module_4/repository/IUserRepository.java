package com.example.case_study_module_4.repository;


import com.example.case_study_module_4.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByAccount_Id(Long accountId);

    // Nếu muốn tìm trực tiếp bằng username:
    Optional<User> findByAccount_UserName(String userName);

    @Query("SELECT u FROM User u WHERE u.account.userName = :username")
    Optional<User> findByUsername(@Param("username") String username);
    // 🧮 Thống kê số lượng user theo vai trò
    @Query("SELECT COUNT(u) FROM User u WHERE u.account.role.roleName = 'ROLE_CUSTOMER'")
    Long countAllCustomers();

    @Query("SELECT COUNT(u) FROM User u WHERE u.account.role.roleName = 'ROLE_RESTAURANT_OWNER'")
    Long countAllOwners();

}
