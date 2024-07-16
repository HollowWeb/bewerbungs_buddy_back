package org.example.bewerbungs_buddy.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ApplicationRepository extends CrudRepository<Application, Long> {
    /**
     * Used to find Application with a certain Status
     * @param status
     * @return List<Application>
     */
    List<Application> findByStatus(String status);

}
