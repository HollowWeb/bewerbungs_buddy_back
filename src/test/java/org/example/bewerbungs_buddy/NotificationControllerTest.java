package org.example.bewerbungs_buddy;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.example.bewerbungs_buddy.controller.NotificationController;
import org.example.bewerbungs_buddy.model.Application;
import org.example.bewerbungs_buddy.model.Notification;
import org.example.bewerbungs_buddy.model.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(NotificationController.class)
@AutoConfigureMockMvc
class NotificationControllerTest {

    @MockBean
    private NotificationRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenGetAllNotifications_thenReturnJsonArray() throws Exception {
        Notification notification = new Notification();
        notification.setId(1L);
        notification.setStatus("Pending");
        Application application = new Application();
        application.setId(1L);
        notification.setApplication(application);

        when(repository.findAll()).thenReturn(List.of(notification));

        mockMvc.perform(get("/notifications")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].status").value("Pending"))
                .andExpect(jsonPath("$[0].applicationId").value(1));
    }

    @Test
    void whenGetNotificationById_thenReturnJson() throws Exception {
        Notification notification = new Notification();
        notification.setId(1L);
        notification.setStatus("Pending");

        Application application = new Application();
        application.setId(1L);
        notification.setApplication(application);

        when(repository.findById(1L)).thenReturn(Optional.of(notification));

        mockMvc.perform(get("/notifications/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("Pending"))
                .andExpect(jsonPath("$.applicationId").value(1));
    }


    @Test
    void whenGetNotificationByInvalidId_thenReturnNotFound() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/notifications/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenPostNotification_thenCreateNotification() throws Exception {
        // Arrange: Create Notification and Application objects
        Notification notification = new Notification();
        notification.setId(1L);
        notification.setStatus("Pending");
        Application application = new Application();
        application.setId(1L);
        notification.setApplication(application);

        // Mock behavior of repository
        when(repository.save(ArgumentMatchers.any(Notification.class))).thenReturn(notification);

        // Act and Assert: Perform the POST request and validate the response
        mockMvc.perform(post("/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\": \"Pending\", \"applicationId\": 1}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("Pending"))
                .andExpect(jsonPath("$.applicationId").value(1));
    }



    @Test
    void whenDeleteNotification_thenReturnNoContent() throws Exception {
        when(repository.existsById(1L)).thenReturn(true);

        mockMvc.perform(delete("/notifications/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenDeleteNotification_thenReturnNotFound() throws Exception {
        when(repository.existsById(1L)).thenReturn(false);

        mockMvc.perform(delete("/notifications/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenUpdateNotification_thenReturnNotFound() throws Exception {
        when(repository.existsById(1L)).thenReturn(false);

        mockMvc.perform(put("/notifications/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\": \"Done\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenPatchNotificationStatus_thenReturnUpdatedNotification() throws Exception {
        Notification notification = new Notification();
        notification.setId(1L);
        notification.setStatus("Pending");

        when(repository.findById(1L)).thenReturn(Optional.of(notification));
        when(repository.save(notification)).thenReturn(notification);

        mockMvc.perform(patch("/notifications/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\": \"Updated\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("Updated"));
    }

    @Test
    void whenPatchNotificationStatus_thenReturnNotFound() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(patch("/notifications/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\": \"Updated\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGetNotificationsByStatus_thenReturnJsonArray() throws Exception {
        Notification notification = new Notification();
        notification.setId(1L);
        notification.setStatus("Pending");
        Application application = new Application();
        application.setId(1L);
        notification.setApplication(application);

        when(repository.findByStatus("Pending")).thenReturn(List.of(notification));

        mockMvc.perform(get("/notifications/status")
                        .param("status", "Pending")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].status").value("Pending"))
                .andExpect(jsonPath("$[0].applicationId").value(1));
    }


    @Test
    void whenGetNotificationsByStatus_thenReturnNoContent() throws Exception {
        when(repository.findByStatus("Pending")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/notifications/status")
                        .param("status", "Pending"))
                .andExpect(status().isNoContent()); // Expect 204 status
    }



}