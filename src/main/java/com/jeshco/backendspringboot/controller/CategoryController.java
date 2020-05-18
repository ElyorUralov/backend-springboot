package com.jeshco.backendspringboot.controller;

import com.jeshco.backendspringboot.entity.Category;
import com.jeshco.backendspringboot.repository.CategoryRepository;
import com.jeshco.backendspringboot.search.CategorySearchValues;
import com.jeshco.backendspringboot.service.CategoryService;
import com.jeshco.backendspringboot.util.MyLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/category/")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("all")
    public List<Category> findAll() {
        MyLogger.showMethodName("CategoryController", "findAll");
        return categoryService.findAll();
    }

    @PostMapping("add")
    public ResponseEntity<Category> add(@RequestBody Category category) {
        MyLogger.showMethodName("CategoryController", "add");
        if (category.getId() != null && category.getId() != 0) {
            return new ResponseEntity("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }
        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(categoryService.add(category));
    }

    @PutMapping("update")
    public ResponseEntity update(@RequestBody Category category) {
        MyLogger.showMethodName("CategoryController", "update");
        if (category.getId() == null || category.getId() == 0) {
            return new ResponseEntity("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }
        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        categoryService.update(category);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {

        MyLogger.showMethodName("CategoryController", "findById");

        Category category = null;
        try {
            category = categoryService.findById(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        MyLogger.showMethodName("CategoryController", "delete");
        try {
            categoryService.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("search")
    public ResponseEntity<List<Category>> search(@RequestBody CategorySearchValues categorySearchValues) {
        MyLogger.showMethodName("CategoryController", "search");
        return ResponseEntity.ok(categoryService.search(categorySearchValues));
    }

}
