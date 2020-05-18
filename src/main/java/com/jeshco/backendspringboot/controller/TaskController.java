package com.jeshco.backendspringboot.controller;

import com.jeshco.backendspringboot.entity.Task;
import com.jeshco.backendspringboot.repository.TaskRepository;
import com.jeshco.backendspringboot.search.TaskSearchValues;
import com.jeshco.backendspringboot.util.MyLogger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/task/")
public class TaskController {

    private TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("all")
    public List<Task> findAll() {

        MyLogger.showMethodName("TaskController", "findAll");

        return taskRepository.findAllByOrderByIdAsc();
    }

    @PostMapping("add")
    public ResponseEntity<Task> add(@RequestBody Task task) {

        MyLogger.showMethodName("TaskController", "add");

        if (task.getId() != null && task.getId() != 0) {
            return new ResponseEntity("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (task.getTitle() == null || task.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(taskRepository.save(task));
    }

    @PutMapping("update")
    public ResponseEntity update(@RequestBody Task task) {

        MyLogger.showMethodName("TaskController", "update");

        if (task.getId() == null || task.getId() == 0) {
            return new ResponseEntity("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }

        if (task.getTitle() == null || task.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        taskRepository.save(task);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id) {

        MyLogger.showMethodName("TaskController", "findById");

        Task task = null;
        try {
            task = taskRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {

        MyLogger.showMethodName("TaskController", "delete");

        try {
            taskRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("search")
    public ResponseEntity<List<Task>> search(@RequestBody TaskSearchValues taskSearchValues) {

        MyLogger.showMethodName("TaskController", "search");

        String title = taskSearchValues.getTitle() != null ? taskSearchValues.getTitle() : null;
        Integer completed = taskSearchValues.getCompleted() != null ? taskSearchValues.getCompleted() : null;
        Long priorityId = taskSearchValues.getPriorityId() != null ? taskSearchValues.getPriorityId() : null;
        Long categoryId = taskSearchValues.getCategoryId() != null ? taskSearchValues.getCategoryId() : null;

        return ResponseEntity.ok(taskRepository.findByParams(title, completed, priorityId, categoryId));
    }

}
