package oosd.sait.ipdbuddyapi.repositories;

import oosd.sait.ipdbuddyapi.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.UUID;


@RepositoryRestResource
public interface JobRepo extends JpaRepository<Job, Integer> {
    List<Job> findAllByContractor_Id(Long contractorId);

    Job findById(long id);
}
