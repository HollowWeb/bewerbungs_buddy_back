package org.example.bewerbungs_buddy.controller;

import org.example.bewerbungs_buddy.exceptions.*;
import org.example.bewerbungs_buddy.model.Application;
import org.example.bewerbungs_buddy.model.ApplicationRepository;
import org.example.bewerbungs_buddy.model.Notification;
import org.example.bewerbungs_buddy.model.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping("")
    public @ResponseBody Iterable<Application> getAllApplications() {
        Iterable<Application> applications;
        try {
            applications = applicationRepository.findAll();
        } catch (Exception ex){
            throw new ApplicationCouldNotBeFoundException();
        }
        return applications;
    }

    @GetMapping("/status")
    public @ResponseBody List<Application> getApplicationsByStatus(@RequestParam String status) {
        List<Application> applications;
        try {
            applications = applicationRepository.findByStatus(status);
        } catch (Exception ex){
            throw new ApplicationCouldNotBeFoundException(status);
        }
        return applications;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplicationById(@PathVariable Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(()-> new ApplicationDoesNotExistException(id));
        return ResponseEntity.ok(application);
    }

    @PostMapping("")
    public ResponseEntity<Application> addApplication(@RequestBody Application application) {
        if (!validateContactInformation(application.getContactInfo())) {
            throw new ApplicationHasInvalideContactInformationException(application.getContactInfo());
        }
        if (!validatePhoneNumber(application.getPhoneNumber())) {
            throw new ApplicationHasInvalidePhoneNumberException(application.getPhoneNumber());
        }
        if (!validatePostalCode(application.getPostalCode())) {
            throw new ApplicationHasInvalidePostalCodeException(application.getPostalCode());
        }

        Application savedApplication;
        try {
            savedApplication = applicationRepository.save(application);
        } catch (Exception ex){
            throw new ApplicationCouldNotBeAddedException();
        }

        if (application.getNotificationTime() > 0) {
            createNotificationForApplication(savedApplication);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(savedApplication);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        return applicationRepository.findById(id).map(application -> {
            applicationRepository.delete(application);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }).orElseThrow(() -> new ApplicationDoesNotExistException(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Application> updateApplication(@PathVariable Long id, @RequestBody Application applicationDetails) {
        return applicationRepository.findById(id).map(application -> {
            application.setContactInfo(applicationDetails.getContactInfo());
            application.setPhoneNumber(applicationDetails.getPhoneNumber());
            application.setPostalCode(applicationDetails.getPostalCode());
            applicationRepository.save(application);
            return new ResponseEntity<Application>(application, HttpStatus.OK);
        }).orElseThrow(() -> new ApplicationDoesNotExistException(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Application> updateApplicationStatus(@PathVariable Long id, @RequestBody Map<String, String> updates) {
        Application existingApplication = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationDoesNotExistException(id));

        String status = updates.get("status");
        if (status != null) {
            existingApplication.setStatus(status);
        }
        Application updatedApplication;
        try {
            updatedApplication = applicationRepository.save(existingApplication);
        } catch (Exception ex) {
            throw new ApplicationCouldNotBePatchedException(id);
        }

        return ResponseEntity.ok(updatedApplication);
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

    private void createNotificationForApplication(Application application) {
        if (application.getSendDate() == null) {
            throw new ApplicationSendDateNotProvidedException(application.getId());
        }
        Notification notification = new Notification();
        notification.setApplication(application);
        notification.setSendDate(application.getSendDate().plusDays(application.getNotificationTime()));
        notification.setNotificationTime(application.getNotificationTime());
        notification.setStatus("Pending");
        try {
            notificationRepository.save(notification);
        } catch (Exception ex) {
            throw new CouldNotCreateNotificationForApplicationException(application.getId());
        }
    }

}
