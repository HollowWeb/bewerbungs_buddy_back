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

    /**
     * Gets all Application from the Database
     * @return Iterable<Application>
     */
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

    /**
     * Gets all Application where status == status
     * @param status
     * @return List<Application>
     */
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

    /**
     * Gets 1 Application by there ID
     * @param id
     * @return ResponseEntity<Application>
     */
    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplicationById(@PathVariable Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(()-> new ApplicationDoesNotExistException(id));
        return ResponseEntity.ok(application);
    }

    /**
     * Inserts a New Application in to the database if the Email/Website, Phone Number and Postal Code are Valid
     * If the Notification Time is > 0 then it will also automatically Create a Notification.
     * @param application
     * @return ResponseEntity<Application>
     */
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

    /**
     * Deletes 1 Application by there ID
     * @param id
     * @return ResponseEntity<Void>
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        return applicationRepository.findById(id).map(application -> {
            applicationRepository.delete(application);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }).orElseThrow(() -> new ApplicationDoesNotExistException(id));
    }

    /**
     * Updates the Application with the correct ID with new Information. Used when Application have changes that are not Status changes.
     * @param id
     * @param applicationDetails
     * @return ResponseEntity<Application>
     */
    @PutMapping("/{id}")
    public ResponseEntity<Application> updateApplication(@PathVariable Long id, @RequestBody Application applicationDetails) {
        return applicationRepository.findById(id).map(application -> {
            application.setContactInfo(applicationDetails.getContactInfo());
            application.setPhoneNumber(applicationDetails.getPhoneNumber());
            application.setPostalCode(applicationDetails.getPostalCode());
            application.setStatus(applicationDetails.getStatus());
            application.setKanton(applicationDetails.getKanton());
            applicationRepository.save(application);
            return new ResponseEntity<Application>(application, HttpStatus.OK);
        }).orElseThrow(() -> new ApplicationDoesNotExistException(id));
    }

    /**
     * Updates ONLY the Status of an Application that is identified by there ID
     * @param id
     * @param updates
     * @return ResponseEntity<Application>
     */
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

    /**
     * Validates ContactInfo if it is a Website or an Email address and matches with the Regex
     * @param contactInfo
     * @return boolean
     */
    private boolean validateContactInformation(String contactInfo) {
        if (contactInfo == null || contactInfo.isEmpty()) {
            return false;
        }
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$|^https?:\\/\\/(www\\.)?[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(contactInfo);

        return matcher.matches();
    }

    /**
     * Validates a PhoneNumber if it is Correct and matches with the Regex
     * @param phoneNumber
     * @return boolean
     */
    private boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return true;
        }
        String regex = "^(\\+\\d{1,3}[- ]?)?(\\(\\d{1,4}\\)[- ]?)?\\d{1,4}[- ]?\\d{1,4}[- ]?\\d{1,9}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);

        return matcher.matches();
    }

    /**
     * Validates a PostalCode if it is made up of 4 Number
     * @param postalCode
     * @return boolean
     */
    private boolean validatePostalCode(String postalCode) {
        if (postalCode == null || postalCode.isEmpty()) {
            return false;
        }
        String regex = "^[0-9]{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(postalCode);

        return matcher.matches();
    }

    /**
     * Create a Notification from the information of an Application
     * @param application
     */
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
