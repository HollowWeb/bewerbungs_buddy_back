package org.example.bewerbungs_buddy.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification, Long> {

    /**
     * Used to find Notifications with the Status of the Application
     * @param status
     * @return List<Notification>
     */
    @Query("SELECT n FROM Notification n JOIN FETCH n.application WHERE n.status = :status")
    List<Notification> findByStatus(String status);

    /**
     * Used to find Notification by its applicationId
     * @param applicationId
     * @return List<Notification>
     */
    List<Notification> findByApplicationId(Long applicationId)
    ;

}
