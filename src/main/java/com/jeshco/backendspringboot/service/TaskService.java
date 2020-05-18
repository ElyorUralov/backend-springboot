package com.jeshco.backendspringboot.service;

import com.jeshco.backendspringboot.entity.Task;
import com.jeshco.backendspringboot.repository.TaskRepository;
import com.jeshco.backendspringboot.search.TaskSearchValues;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaskService {

    private TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> findAll() {
        return repository.findAllByOrderByIdAsc();
    }

    public Task add(Task task) {
        return repository.save(task);
    }

    public Task update(Task task) {
        return repository.save(task);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Task findById(Long id) {
        return repository.findById(id).get();
    }

    public List<Task> findAllByOrderByTitleAsc() {
        return repository.findAllByOrderByIdAsc();
    }

    public Page search(TaskSearchValues taskSearchValues) {
        String text = taskSearchValues.getTitle() != null ? taskSearchValues.getTitle() : null;
        Integer completed = taskSearchValues.getCompleted() != null ? taskSearchValues.getCompleted() : null;
        Long priorityId = taskSearchValues.getPriorityId() != null ? taskSearchValues.getPriorityId() : null;
        Long categoryId = taskSearchValues.getCategoryId() != null ? taskSearchValues.getCategoryId() : null;

        String sortColumn = taskSearchValues.getSortColumn() != null ? taskSearchValues.getSortColumn() : null;
        String sortDirection = taskSearchValues.getSortDirection() != null ? taskSearchValues.getSortDirection() : null;

        Integer pageNumber = taskSearchValues.getPageNumber() != null ? taskSearchValues.getPageNumber() : null;
        Integer pageSize = taskSearchValues.getPageSize() != null ? taskSearchValues.getPageSize() : null;

        Sort.Direction direction = sortDirection == null || sortDirection.trim().length() == 0 || sortDirection.trim().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        // подставляем все значения
        // объект сортировки
        Sort sort = Sort.by(direction, sortColumn);
        // объект постраничности
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        // результат запроса с постраничным выводом
        Page result = repository.findByParams(text, completed, priorityId, categoryId, pageRequest);

        return result;
    }
}
