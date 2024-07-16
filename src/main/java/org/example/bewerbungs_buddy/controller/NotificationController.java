package org.example.bewerbungs_buddy.controller;

import org.example.bewerbungs_buddy.exceptions.*;
import org.example.bewerbungs_buddy.model.Application;
import org.example.bewerbungs_buddy.model.Notification;
import org.example.bewerbungs_buddy.model.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository repository;

    /**
     * Gets all Notifications
     * @return Iterable<Notification>
     */
    @GetMapping("")
    public @ResponseBody Iterable<Notification> getAllNotifications() {
        Iterable<Notification> notifications;
        try {
            notifications = repository.findAll();
        } catch (Exception e) {
            throw new NotificationsCouldNotBeFoundException();
        }
        return notifications;
    }

    /**
     * Gets all Notification where the status matches the param status
     * @param status
     * @return List<Notification>
     */
    @GetMapping("/status")
    public @ResponseBody List<Notification> getNotificationsByStatus(@RequestParam String status) {
        List<Notification> notifications;
        try {
            notifications = repository.findByStatus(status);
        } catch (Exception e) {
            throw new NotificationsCouldNotBeFoundByStatusException(status);
        }
        return notifications;

    }

    /**
     * Gets 1 Notification by there ID
     * @param id
     * @return ResponseEntity<Notification>
     */
    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        Optional<Notification> notification;
        try {
            notification = repository.findById(id);
        } catch (Exception e) {
            throw new NotificationCouldNotBeFoundByIdException(id);
        }
        return notification.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    /**
     * Adds a new Notification to the database
     * @param notification
     * @return ResponseEntity<Notification>
     */
    @PostMapping("")
    public ResponseEntity<Notification> addNotification(@RequestBody Notification notification) {

        Notification savedNotification;
        try {
            savedNotification = repository.save(notification);
        } catch (Exception e) {
            throw new NotificationCouldNotBeSavedException();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(savedNotification);
    }

    /**
     * Deletes 1 Notification by there ID
     * @param id
     * @return ResponseEntity<Void>
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new NotificationCouldNotBeFoundByIdException(id);
        }

        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new NotificationCouldNotBeDeletedException(id);
        }

        return ResponseEntity.noContent().build();
    }

    /**
     * Deletes ALL notifications by there Application_ID
     * @param id
     * @return ResponseEntity<Void>
     */
    @DeleteMapping("/del/{id}")
    public ResponseEntity<Void> deleteNotificationByApplication(@PathVariable Long id) {
        List<Notification> allNotifications = repository.findByApplicationId(id);

        if (!allNotifications.isEmpty()) {
            try {
                repository.deleteAll(allNotifications);
            } catch (Exception e) {
                throw new NotificationCouldNotBeDeletedException(id);
            }

            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Updates 1 Notification by there ID
     * @param id
     * @param notification
     * @return ResponseEntity<Notification>
     */
    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable Long id, @RequestBody Notification notification) {
        if (!repository.existsById(id)) {
            throw new NotificationCouldNotBeFoundByIdException(id);
        }
        try {
            notification.setId(id);
        } catch (Exception e) {
            throw new NotificationCouldNotBeUpdatetException(id);
        }
        Notification updatedNotification = repository.save(notification);

        return ResponseEntity.ok(updatedNotification);
    }

    /**
     * Updates the status of a Notification that is identified by there ID
     * @param id
     * @param updates
     * @return ResponseEntity<Notification>
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Notification> updateNotificationStatus(@PathVariable Long id, @RequestBody Map<String, String> updates) {
        Notification existingNotification = repository.findById(id).orElseThrow( () -> new NotificationCouldNotBeFoundByIdException(id));
        existingNotification.setStatus(updates.get("status"));

        Notification updatedNotification;
        try {
            updatedNotification = repository.save(existingNotification);
        } catch (Exception e) {
            throw new NotificationCouldNotBeUpdatetException(id);
        }

        return ResponseEntity.ok(updatedNotification);
    }


}
