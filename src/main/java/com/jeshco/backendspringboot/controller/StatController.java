package com.jeshco.backendspringboot.controller;

import com.jeshco.backendspringboot.entity.Stat;
import com.jeshco.backendspringboot.repository.StatRepository;
import com.jeshco.backendspringboot.util.MyLogger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stat/")
public class StatController {

    private final StatRepository statRepository;
    private final Long defaultId = 1l;

    public StatController(StatRepository statRepository) {
        this.statRepository = statRepository;
    }

    @GetMapping
    public ResponseEntity<Stat> findById() {

        MyLogger.showMethodName("StatController", "search");

        return ResponseEntity.ok(statRepository.findById(defaultId).get());
    }
}
