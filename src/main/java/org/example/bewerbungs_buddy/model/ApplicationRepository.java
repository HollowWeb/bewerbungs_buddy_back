package org.example.bewerbungs_buddy.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ApplicationRepository extends CrudRepository<Application, Long> {

    List<Application> findByStatus(String status);

}
