package org.example.bewerbungs_buddy;

import org.example.bewerbungs_buddy.model.Application;
import org.example.bewerbungs_buddy.model.Notification;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {

    @Test
    void testNotificationGettersAndSetters() {
        Notification notification = new Notification();

        Long id = 1L;
        LocalDate sendDate = LocalDate.of(2024, 1, 1);
        int notificationTime = 7;
        String status = "Pending";
        Application application = new Application();
        application.setId(100L);

        // Test setters
        notification.setId(id);
        notification.setSendDate(sendDate);
        notification.setNotificationTime(notificationTime);
        notification.setStatus(status);
        notification.setApplication(application);

        // Test getters
        assertEquals(id, notification.getId());
        assertEquals(sendDate, notification.getSendDate());
        assertEquals(notificationTime, notification.getNotificationTime());
        assertEquals(status, notification.getStatus());
        assertEquals(application, notification.getApplication());
    }

    @Test
    void testDefaultValues() {
        Notification notification = new Notification();

        // Assert that the default values are null or zero as expected
        assertNull(notification.getId());
        assertNull(notification.getSendDate());
        assertEquals(0, notification.getNotificationTime());
        assertNull(notification.getStatus());
        assertNull(notification.getApplication());
    }
}
