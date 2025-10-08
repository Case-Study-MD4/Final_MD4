package com.example.case_study_module_4.repository;



import com.example.case_study_module_4.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long > {

    Optional<User> findByAccount_Id(Long accountId);

    // Nếu muốn tìm trực tiếp bằng username:
    Optional<User> findByAccount_UserName(String userName);
}
