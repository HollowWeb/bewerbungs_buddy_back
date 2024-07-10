package org.example.bewerbungs_buddy.model;

import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.awt.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "applications")
public class Application {

    @javax.persistence.Id
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
}
