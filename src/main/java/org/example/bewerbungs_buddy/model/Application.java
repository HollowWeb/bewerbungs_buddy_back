package org.example.bewerbungs_buddy.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
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

    @OneToMany(mappedBy = "application")
    @JsonIgnore
    private Set<Notification> notifications;


    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getCompanyName() {
        return companyName;
    }

    public void setContactInfo(String contactInfo) {

        this.contactInfo = contactInfo;
    }
    public String getContactInfo() {
        return contactInfo;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setSendDate(LocalDate sendDate) {
        this.sendDate = sendDate;
    }
    public LocalDate getSendDate() {
        return sendDate;
    }

    public void setKanton(String kanton) {
        this.kanton = kanton;
    }
    public String getKanton() {
        return kanton;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public String getPostalCode() {
        return postalCode;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }
    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setNotificationTime(int notificationTime) {
        this.notificationTime = notificationTime;
    }
    public int getNotificationTime() {
        return notificationTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }
}
