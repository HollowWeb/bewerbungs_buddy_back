package org.example.bewerbungs_buddy.controller;

import org.example.bewerbungs_buddy.exceptions.*;
import org.example.bewerbungs_buddy.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationRepository applicationRepository;
    private final NotificationRepository notificationRepository;

    public ApplicationController(ApplicationRepository applicationRepository, NotificationRepository notificationRepository) {
        this.applicationRepository = applicationRepository;
        this.notificationRepository = notificationRepository;
    }

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern URL_PATTERN = Pattern.compile("^https?://(www\\.)?[\\w.-]+\\.[a-zA-Z]{2,}$");


    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(
            "^(\\+\\d{1,3}[- ]?)?(\\(\\d{1,4}\\)[- ]?)?\\d{1,4}([- ]?\\d{1,4}){1,3}$"
    );

    private static final Pattern POSTAL_CODE_PATTERN = Pattern.compile("^\\d{4}$");

    @GetMapping("")
    public List<ApplicationDTO> getAllApplications() {
        return StreamSupport.stream(applicationRepository.findAll().spliterator(), false)
                .map(this::mapToDTO)
                .toList();
    }

    @GetMapping("/status")
    public List<ApplicationDTO> getApplicationsByStatus(@RequestParam String status) {
        return applicationRepository.findByStatus(status)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDTO> getApplicationById(@PathVariable Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationDoesNotExistException(id));
        return ResponseEntity.ok(mapToDTO(application));
    }

    @PostMapping("")
    public ResponseEntity<ApplicationDTO> addApplication(@RequestBody ApplicationDTO applicationDTO) {
        validateContactInformation(applicationDTO.getContactInfo());
        validatePhoneNumber(applicationDTO.getPhoneNumber());
        validatePostalCode(applicationDTO.getPostalCode());

        Application application = mapToEntity(applicationDTO);
        Application savedApplication = applicationRepository.save(application);

        if (application.getNotificationTime() > 0) {
            createNotificationForApplication(savedApplication);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(mapToDTO(savedApplication));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationDoesNotExistException(id));
        applicationRepository.delete(application);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationDTO> updateApplication(@PathVariable Long id, @RequestBody ApplicationDTO applicationDTO) {
        Application existingApplication = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationDoesNotExistException(id));

        existingApplication.setContactInfo(applicationDTO.getContactInfo());
        existingApplication.setPhoneNumber(applicationDTO.getPhoneNumber());
        existingApplication.setPostalCode(applicationDTO.getPostalCode());
        existingApplication.setStatus(applicationDTO.getStatus());
        existingApplication.setKanton(applicationDTO.getKanton());

        Application updatedApplication = applicationRepository.save(existingApplication);
        return ResponseEntity.ok(mapToDTO(updatedApplication));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApplicationDTO> updateApplicationStatus(@PathVariable Long id, @RequestBody Map<String, String> updates) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationDoesNotExistException(id));

        Optional.ofNullable(updates.get("status")).ifPresent(application::setStatus);
        Application updatedApplication = applicationRepository.save(application);
        return ResponseEntity.ok(mapToDTO(updatedApplication));
    }

    private void validateContactInformation(String contactInfo) {
        if (contactInfo == null || contactInfo.isEmpty()) {
            throw new ApplicationHasInvalideContactInformationException(contactInfo);
        }
        if (!EMAIL_PATTERN.matcher(contactInfo).matches() && !URL_PATTERN.matcher(contactInfo).matches()) {
            throw new ApplicationHasInvalideContactInformationException(contactInfo);
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber != null && !PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches()) {
            throw new ApplicationHasInvalidePhoneNumberException(phoneNumber);
        }
    }

    private void validatePostalCode(String postalCode) {
        if (postalCode == null || !POSTAL_CODE_PATTERN.matcher(postalCode).matches()) {
            throw new ApplicationHasInvalidePostalCodeException(postalCode);
        }
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

        notificationRepository.save(notification);
    }

    private ApplicationDTO mapToDTO(Application application) {
        ApplicationDTO dto = new ApplicationDTO();
        dto.setId(application.getId());
        dto.setCompanyName(application.getCompanyName());
        dto.setContactInfo(application.getContactInfo());
        dto.setPhoneNumber(application.getPhoneNumber());
        dto.setSendDate(application.getSendDate());
        dto.setKanton(application.getKanton());
        dto.setPostalCode(application.getPostalCode());
        dto.setAdditionalNotes(application.getAdditionalNotes());
        dto.setNotificationTime(application.getNotificationTime());
        dto.setStatus(application.getStatus());
        return dto;
    }

    private Application mapToEntity(ApplicationDTO dto) {
        Application application = new Application();
        application.setId(dto.getId());
        application.setCompanyName(dto.getCompanyName());
        application.setContactInfo(dto.getContactInfo());
        application.setPhoneNumber(dto.getPhoneNumber());
        application.setSendDate(dto.getSendDate());
        application.setKanton(dto.getKanton());
        application.setPostalCode(dto.getPostalCode());
        application.setAdditionalNotes(dto.getAdditionalNotes());
        application.setNotificationTime(dto.getNotificationTime());
        application.setStatus(dto.getStatus());
        return application;
    }
}
