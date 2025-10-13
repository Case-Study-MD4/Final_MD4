package com.example.case_study_module_4.service;

import com.example.case_study_module_4.entity.Categories;

import java.util.List;

public interface ICategoriesService {
    List<Categories> findAll();

    Categories findById(Integer id);
}
