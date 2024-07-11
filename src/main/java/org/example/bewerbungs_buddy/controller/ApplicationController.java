package org.example.bewerbungs_buddy.controller;

import org.example.bewerbungs_buddy.model.Application;
import org.example.bewerbungs_buddy.model.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationRepository repository;

    @GetMapping("")
    public @ResponseBody Iterable<Application> getAllApplications() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplicationById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("")
    public ResponseEntity<Application> addApplication(@RequestBody Application application) {
        if (!validateContactInformation(application.getContactInfo())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        if (!validatePhoneNumber(application.getPhoneNumber())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        if (!validatePostalCode(application.getPostalCode())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Application savedApplication = repository.save(application);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedApplication);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Application> updateApplication(@PathVariable Long id, @RequestBody Application application) {
        if (!validateContactInformation(application.getContactInfo()) ||
                !validatePhoneNumber(application.getPhoneNumber()) ||
                !validatePostalCode(application.getPostalCode())){

            return ResponseEntity.badRequest().build();
        }
        return repository.findById(id)
                .map(existingApplication -> {
                    application.setId(id);
                    Application updatedApplication = repository.save(application);
                    return ResponseEntity.ok(updatedApplication);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private boolean validateContactInformation(String contactInfo) {
        if (contactInfo == null || contactInfo.isEmpty()) {
            return false;
        }
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$|^https?:\\/\\/(www\\.)?[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(contactInfo);

        return matcher.matches();
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return true;
        }
        String regex = "^(\\+\\d{1,3}[- ]?)?(\\(\\d{1,4}\\)[- ]?)?\\d{1,4}[- ]?\\d{1,4}[- ]?\\d{1,9}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);

        return matcher.matches();
    }

    private boolean validatePostalCode(String postalCode) {
        if (postalCode == null || postalCode.isEmpty()) {
            return false;
        }
        String regex = "^[0-9]{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(postalCode);

        return matcher.matches();
    }




}

