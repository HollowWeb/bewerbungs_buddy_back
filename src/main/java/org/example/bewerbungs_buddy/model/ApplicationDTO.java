package org.example.bewerbungs_buddy.model;

import java.time.LocalDate;

public class ApplicationDTO {
    private Long id;
    private String companyName;
    private String contactInfo;
    private String phoneNumber;
    private LocalDate sendDate;
    private String kanton;
    private String postalCode;
    private String additionalNotes;
    private int notificationTime;
    private String status;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public LocalDate getSendDate() { return sendDate; }
    public void setSendDate(LocalDate sendDate) { this.sendDate = sendDate; }

    public String getKanton() { return kanton; }
    public void setKanton(String kanton) { this.kanton = kanton; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public String getAdditionalNotes() { return additionalNotes; }
    public void setAdditionalNotes(String additionalNotes) { this.additionalNotes = additionalNotes; }

    public int getNotificationTime() { return notificationTime; }
    public void setNotificationTime(int notificationTime) { this.notificationTime = notificationTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
