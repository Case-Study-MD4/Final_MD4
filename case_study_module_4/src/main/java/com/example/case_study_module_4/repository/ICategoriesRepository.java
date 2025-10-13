package com.example.case_study_module_4.repository;

import com.example.case_study_module_4.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoriesRepository extends JpaRepository<Categories,Integer> {

}
