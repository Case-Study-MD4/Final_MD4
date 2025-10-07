package com.example.case_study_module_4.repository;

import com.example.case_study_module_4.entity.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IAccountRepository extends JpaRepository<Account,Long> {
    @EntityGraph(attributePaths = {"role"})
    Optional<Account> findByUserName(String userName);
}
