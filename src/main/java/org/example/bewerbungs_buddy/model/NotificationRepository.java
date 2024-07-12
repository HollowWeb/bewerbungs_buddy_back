package org.example.bewerbungs_buddy.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n JOIN FETCH n.application WHERE n.status = :status")
    List<Notification> findByStatus(String status);

}
