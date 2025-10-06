package com.example.case_study_module_4.repository;

import com.example.case_study_module_4.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRestaurantRepository extends JpaRepository<Restaurant, Long> {
}
