package org.example.bewerbungs_buddy;


import org.example.bewerbungs_buddy.model.Application;
import org.example.bewerbungs_buddy.model.Notification;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationTest {

    @Test
    void testApplicationGettersAndSetters() {
        Application application = new Application();

        Long id = 1L;
        String companyName = "Test Company";
        String contactInfo = "test@example.com";
        String phoneNumber = "+123456789";
        LocalDate sendDate = LocalDate.of(2024, 1, 1);
        String kanton = "Zurich";
        String postalCode = "1234";
        String additionalNotes = "These are additional notes.";
        int notificationTime = 5;
        String status = "Pending";
        HashSet<Notification> notifications = new HashSet<>();

        // Test setters
        application.setId(id);
        application.setCompanyName(companyName);
        application.setContactInfo(contactInfo);
        application.setPhoneNumber(phoneNumber);
        application.setSendDate(sendDate);
        application.setKanton(kanton);
        application.setPostalCode(postalCode);
        application.setAdditionalNotes(additionalNotes);
        application.setNotificationTime(notificationTime);
        application.setStatus(status);
        application.setNotifications(notifications);

        // Test getters
        assertEquals(id, application.getId());
        assertEquals(companyName, application.getCompanyName());
        assertEquals(contactInfo, application.getContactInfo());
        assertEquals(phoneNumber, application.getPhoneNumber());
        assertEquals(sendDate, application.getSendDate());
        assertEquals(kanton, application.getKanton());
        assertEquals(postalCode, application.getPostalCode());
        assertEquals(additionalNotes, application.getAdditionalNotes());
        assertEquals(notificationTime, application.getNotificationTime());
        assertEquals(status, application.getStatus());
        assertEquals(notifications, application.getNotifications());
    }

    @Test
    void testDefaultValues() {
        Application application = new Application();

        // Assert that the default values are null or zero as expected
        assertNull(application.getId());
        assertNull(application.getCompanyName());
        assertNull(application.getContactInfo());
        assertNull(application.getPhoneNumber());
        assertNull(application.getSendDate());
        assertNull(application.getKanton());
        assertNull(application.getPostalCode());
        assertNull(application.getAdditionalNotes());
        assertEquals(0, application.getNotificationTime());
        assertNull(application.getStatus());
        assertNull(application.getNotifications());
    }
}

