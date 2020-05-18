package com.jeshco.backendspringboot.service;

import com.jeshco.backendspringboot.entity.Priority;
import com.jeshco.backendspringboot.repository.PriorityRepository;
import com.jeshco.backendspringboot.search.PrioritySearchValues;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PriorityService {

    private PriorityRepository repository;

    public PriorityService(PriorityRepository repository) {
        this.repository = repository;
    }

    public List<Priority> findAll() {
        return repository.findAllByOrderByIdAsc();
    }

    public Priority add(Priority category) {
        return repository.save(category);
    }

    public Priority update(Priority category) {
        return repository.save(category);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Priority findById(Long id) {
        return repository.findById(id).get();
    }

    public List<Priority> findAllByOrderByTitleAsc() {
        return repository.findAllByOrderByIdAsc();
    }

    public List<Priority> search(PrioritySearchValues prioritySearchValues) {
        return repository.findByTitle(prioritySearchValues.getText());
    }
}
