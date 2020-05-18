package com.jeshco.backendspringboot.controller;

import com.jeshco.backendspringboot.entity.Priority;
import com.jeshco.backendspringboot.repository.PriorityRepository;
import com.jeshco.backendspringboot.search.PrioritySearchValues;
import com.jeshco.backendspringboot.service.PriorityService;
import com.jeshco.backendspringboot.util.MyLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/priority/")
public class PriorityController {

    private PriorityService priorityService;

    public PriorityController(PriorityService priorityService) {
        this.priorityService = priorityService;
    }

    @GetMapping("all")
    public List<Priority> findAll() {
        MyLogger.showMethodName("PriorityController", "findAll");
        return priorityService.findAll();
    }

    @PostMapping("add")
    public ResponseEntity<Priority> add(@RequestBody Priority priority) {
        MyLogger.showMethodName("PriorityController", "add");
        if (priority.getId() != null && priority.getId() != 0) {
            return new ResponseEntity("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }
        if (priority.getTitle() == null || priority.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        if (priority.getColor() == null || priority.getColor().trim().length() == 0) {
            return new ResponseEntity("missed param: color", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(priorityService.add(priority));
    }

    @PutMapping("update")
    public ResponseEntity update(@RequestBody Priority priority) {
        MyLogger.showMethodName("PriorityController", "update");
        if (priority.getId() == null || priority.getId() == 0) {
            return new ResponseEntity("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }
        if (priority.getTitle() == null || priority.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        if (priority.getColor() == null || priority.getColor().trim().length() == 0) {
            return new ResponseEntity("missed param: color", HttpStatus.NOT_ACCEPTABLE);
        }
        priorityService.update(priority);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<Priority> findById(@PathVariable Long id) {
        MyLogger.showMethodName("PriorityController", "findById");
        Priority priority = null;
        try {
            priority = priorityService.findById(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(priority);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        MyLogger.showMethodName("PriorityController", "delete");
        try {
            priorityService.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("search")
    public ResponseEntity<List<Priority>> search(@RequestBody PrioritySearchValues prioritySearchValues) {
        MyLogger.showMethodName("PriorityController", "search");
        return ResponseEntity.ok(priorityService.search(prioritySearchValues));
    }

}
