package org.example.bewerbungs_buddy.controller;

import org.example.bewerbungs_buddy.model.Application;
import org.example.bewerbungs_buddy.model.ApplicationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationRepository repository;

    @GetMapping("")
    public @ResponseBody Iterable<Application> getAllApplications() {
        return repository.findAll();
    }

    @PostMapping("")
    public @ResponseBody Application addApplication(@RequestBody Application application) {
        return repository.save(application);
    }
}
