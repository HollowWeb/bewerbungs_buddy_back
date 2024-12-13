package org.example.bewerbungs_buddy.controller;


import org.example.bewerbungs_buddy.exceptions.*;
import org.example.bewerbungs_buddy.model.Application;
import org.example.bewerbungs_buddy.model.Notification;
import org.example.bewerbungs_buddy.model.NotificationDTO;
import org.example.bewerbungs_buddy.model.NotificationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationRepository repository;

    public NotificationController(NotificationRepository repository) {
        this.repository = repository;
    }

    /**
     * Gets all Notifications
     * @return Iterable<NotificationDTO>
     */
    @GetMapping("")
    public ResponseEntity<List<NotificationDTO>> getAllNotifications() {
        List<Notification> notifications = (List<Notification>) repository.findAll();
        List<NotificationDTO> dtos = notifications.stream()
                .map(this::mapToNotificationDTO)
                .toList();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dtos);
    }

    /**
     * Gets all Notifications where the status matches the param status
     * @param status
     * @return List<NotificationDTO>
     */
    @GetMapping("/status")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByStatus(@RequestParam String status) {
        List<Notification> notifications = repository.findByStatus(status);
        if (notifications.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<NotificationDTO> dtos = notifications.stream()
                .map(this::mapToNotificationDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }


    /**
     * Gets 1 Notification by its ID
     * @param id
     * @return ResponseEntity<NotificationDTO>
     */
    @GetMapping("/{id}")
    public ResponseEntity<NotificationDTO> getNotificationById(@PathVariable Long id) {
        Notification notification = repository.findById(id)
                .orElseThrow(() -> new NotificationCouldNotBeFoundByIdException(id));
        return ResponseEntity.ok(mapToNotificationDTO(notification));
    }

    /**
     * Adds a new Notification to the database
     * @param notificationDTO
     * @return ResponseEntity<NotificationDTO>
     */
    @PostMapping("")
    public ResponseEntity<NotificationDTO> addNotification(@RequestBody NotificationDTO notificationDTO) {
        Notification notification = mapToNotificationEntity(notificationDTO);

        // Validate the associated Application
        if (notification.getApplication() == null || notification.getApplication().getId() == null) {
            throw new IllegalArgumentException("Application ID must be provided for the Notification.");
        }

        Notification savedNotification = repository.save(notification);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToNotificationDTO(savedNotification));
    }


    /**
     * Deletes 1 Notification by its ID
     * @param id
     * @return ResponseEntity<Void>
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new NotificationCouldNotBeFoundByIdException(id);
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Deletes ALL notifications by their Application_ID
     * @param id
     * @return ResponseEntity<Void>
     */
    @DeleteMapping("/del/{id}")
    public ResponseEntity<Void> deleteNotificationByApplication(@PathVariable Long id) {
        List<Notification> allNotifications = repository.findByApplicationId(id);

        if (!allNotifications.isEmpty()) {
            repository.deleteAll(allNotifications);
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Updates 1 Notification by its ID
     * @param id
     * @param notificationDTO
     * @return ResponseEntity<NotificationDTO>
     */
    @PutMapping("/{id}")
    public ResponseEntity<NotificationDTO> updateNotification(@PathVariable Long id, @RequestBody NotificationDTO notificationDTO) {
        if (!repository.existsById(id)) {
            throw new NotificationCouldNotBeFoundByIdException(id);
        }
        Notification notification = mapToNotificationEntity(notificationDTO);
        notification.setId(id); // Ensure the ID remains consistent
        Notification updatedNotification = repository.save(notification);
        return ResponseEntity.ok(mapToNotificationDTO(updatedNotification));
    }

    /**
     * Updates the status of a Notification that is identified by its ID
     * @param id
     * @param updates
     * @return ResponseEntity<NotificationDTO>
     */
    @PatchMapping("/{id}")
    public ResponseEntity<NotificationDTO> updateNotificationStatus(@PathVariable Long id, @RequestBody Map<String, String> updates) {
        Notification existingNotification = repository.findById(id)
                .orElseThrow(() -> new NotificationCouldNotBeFoundByIdException(id));
        existingNotification.setStatus(updates.get("status"));

        Notification updatedNotification = repository.save(existingNotification);
        return ResponseEntity.ok(mapToNotificationDTO(updatedNotification));
    }

    // Mapping methods
    public NotificationDTO mapToNotificationDTO(Notification notification) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getId());
        dto.setStatus(notification.getStatus());
        dto.setApplicationId(notification.getApplication() != null ? notification.getApplication().getId() : null);
        return dto;
    }



    private Notification mapToNotificationEntity(NotificationDTO dto) {
        Notification notification = new Notification();
        notification.setId(dto.getId());
        notification.setSendDate(dto.getSendDate());
        notification.setNotificationTime(dto.getNotificationTime());
        notification.setStatus(dto.getStatus());
        Application application = new Application();
        application.setId(dto.getApplicationId());
        notification.setApplication(application);
        return notification;
    }
}

