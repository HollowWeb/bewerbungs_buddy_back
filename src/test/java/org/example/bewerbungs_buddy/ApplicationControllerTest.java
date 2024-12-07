package org.example.bewerbungs_buddy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


import java.time.LocalDate;
import java.util.Optional;


import org.example.bewerbungs_buddy.controller.ApplicationController;

import org.example.bewerbungs_buddy.model.Application;
import org.example.bewerbungs_buddy.model.ApplicationRepository;
import org.example.bewerbungs_buddy.model.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ApplicationController.class)
@AutoConfigureMockMvc
class ApplicationControllerTest {

    @MockBean
    private ApplicationRepository applicationRepository;

    @MockBean
    private NotificationRepository notificationRepository;

    @Autowired
    private ApplicationController applicationController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void applicationController_isPresent() {
        assertThat(applicationController).isNotNull();
    }

    @Test
    void whenPostApplication_thenApplicationCreated() throws Exception {
        Application application = new Application();
        application.setContactInfo("valid.email@example.com");
        application.setPhoneNumber("+1234567890");
        application.setPostalCode("1234");
        application.setNotificationTime(5);
        application.setSendDate(LocalDate.now());

        when(applicationRepository.save(any(Application.class))).thenReturn(application);

        mockMvc.perform(MockMvcRequestBuilders.post("/applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"contactInfo\": \"valid.email@example.com\", \"phoneNumber\": \"+1234567890\", \"postalCode\": \"1234\", \"notificationTime\": 5, \"sendDate\": \"" + LocalDate.now() + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.contactInfo").value("valid.email@example.com"));
    }

    @Test
    void whenPostApplicationWithInvalidContactInfo_thenThrowsException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"contactInfo\": \"invalid-email\", \"phoneNumber\": \"+1234567890\", \"postalCode\": \"1234\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void whenGetApplicationById_thenApplicationReturned() throws Exception {
        Application application = new Application();
        application.setId(1L);
        application.setContactInfo("valid.email@example.com");

        when(applicationRepository.findById(1L)).thenReturn(java.util.Optional.of(application));

        mockMvc.perform(MockMvcRequestBuilders.get("/applications/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.contactInfo").value("valid.email@example.com"));
    }

    @Test
    void whenGetApplicationByInvalidId_thenThrowsException() throws Exception {
        when(applicationRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/applications/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void whenDeleteApplication_thenApplicationDeleted() throws Exception {
        Application application = new Application();
        application.setId(1L);
        when(applicationRepository.findById(1L)).thenReturn(Optional.of(application));

        mockMvc.perform(MockMvcRequestBuilders.delete("/applications/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void whenDeleteApplicationWithInvalidId_thenThrowsException() throws Exception {
        when(applicationRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.delete("/applications/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void whenUpdateApplication_thenApplicationUpdated() throws Exception {
        Application application = new Application();
        application.setId(1L);
        application.setContactInfo("valid.email@example.com");
        application.setPhoneNumber("+1234567890");
        application.setPostalCode("1234");

        when(applicationRepository.findById(1L)).thenReturn(java.util.Optional.of(application));
        when(applicationRepository.save(any(Application.class))).thenReturn(application);

        mockMvc.perform(MockMvcRequestBuilders.put("/applications/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"contactInfo\": \"valid.email@example.com\", \"phoneNumber\": \"+1234567890\", \"postalCode\": \"1234\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.contactInfo").value("valid.email@example.com"));
    }

    @Test
    void whenUpdateApplicationWithInvalidId_thenThrowsException() throws Exception {
        when(applicationRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.put("/applications/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"contactInfo\": \"valid.email@example.com\", \"phoneNumber\": \"+1234567890\", \"postalCode\": \"1234\"}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void whenPatchApplicationStatus_thenApplicationStatusUpdated() throws Exception {
        Application application = new Application();
        application.setId(1L);
        application.setStatus("Pending");

        when(applicationRepository.findById(1L)).thenReturn(java.util.Optional.of(application));
        when(applicationRepository.save(any(Application.class))).thenReturn(application);

        mockMvc.perform(MockMvcRequestBuilders.patch("/applications/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\": \"Approved\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Approved"));
    }

    @Test
    void whenPatchApplicationStatusWithInvalidId_thenThrowsException() throws Exception {
        when(applicationRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.patch("/applications/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\": \"Approved\"}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
