package org.example.bewerbungs_buddy.model;

import java.time.LocalDate;

public class NotificationDTO {
    private Long id;
    private LocalDate sendDate;
    private int notificationTime;
    private String status;
    private Long applicationId;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getSendDate() { return sendDate; }
    public void setSendDate(LocalDate sendDate) { this.sendDate = sendDate; }

    public int getNotificationTime() { return notificationTime; }
    public void setNotificationTime(int notificationTime) { this.notificationTime = notificationTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }
}
