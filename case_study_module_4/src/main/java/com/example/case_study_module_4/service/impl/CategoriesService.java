package com.example.case_study_module_4.service.impl;

import com.example.case_study_module_4.entity.Categories;
import com.example.case_study_module_4.repository.ICategoriesRepository;
import com.example.case_study_module_4.service.ICategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriesService implements ICategoriesService {

    private final ICategoriesRepository categoriesRepository;

    @Override
    public List<Categories> findAll() {
        return categoriesRepository.findAll();
    }

    @Override
    public Categories findById(Integer id) {
        return categoriesRepository.findById(id).orElse(null);
    }
}
