package com.jeshco.backendspringboot.service;


import com.jeshco.backendspringboot.entity.Category;
import com.jeshco.backendspringboot.repository.CategoryRepository;
import com.jeshco.backendspringboot.search.CategorySearchValues;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService {

    private CategoryRepository repository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.repository = categoryRepository;
    }

    public List<Category> findAll() {
        return repository.findAllByOrderByTitleAsc();
    }

    public Category add(Category category) {
        return repository.save(category);
    }

    public Category update(Category category) {
        return repository.save(category);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Category findById(Long id) {
        return repository.findById(id).get();
    }

    public List<Category> findAllByOrderByTitleAsc() {
        return repository.findAllByOrderByTitleAsc();
    }

    public List<Category> search(CategorySearchValues categorySearchValues) {
        return repository.findByTitle(categorySearchValues.getText());
    }
}
