package org.example.bewerbungs_buddy.controller;

import org.example.bewerbungs_buddy.model.Notification;
import org.example.bewerbungs_buddy.model.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository repository;

    @GetMapping("")
    public @ResponseBody Iterable<Notification> getAllNotifications() {
        return repository.findAll();
    }
    @GetMapping("/status")
    public @ResponseBody List<Notification> getNotificationsByStatus(@RequestParam String status) {
        return repository.findByStatus(status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("")
    public ResponseEntity<Notification> addNotification(@RequestBody Notification notification) {
        Notification savedNotification = repository.save(notification);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedNotification);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable Long id, @RequestBody Notification notification) {
        return repository.findById(id)
                .map(existingNotification -> {
                    notification.setId(id);
                    Notification updatedNotification = repository.save(notification);
                    return ResponseEntity.ok(updatedNotification);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Notification> updateNotificationStatus(@PathVariable Long id, @RequestBody Map<String, String> updates) {
        return repository.findById(id)
                .map(existingNotification -> {
                    String status = updates.get("status");
                    if (status != null) {
                        existingNotification.setStatus(status);
                    }
                    Notification updatedNotification = repository.save(existingNotification);
                    return ResponseEntity.ok(updatedNotification);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
