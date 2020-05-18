package com.jeshco.backendspringboot.service;

import com.jeshco.backendspringboot.entity.Stat;
import com.jeshco.backendspringboot.repository.StatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StatService {

    private StatRepository repository;

    public StatService(StatRepository repository) {
        this.repository = repository;
    }

    public Stat findById(Long id) {
        return repository.findById(id).get();
    }
}
