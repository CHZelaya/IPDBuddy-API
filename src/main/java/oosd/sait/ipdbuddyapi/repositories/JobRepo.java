package oosd.sait.ipdbuddyapi.repositories;

import oosd.sait.ipdbuddyapi.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface JobRepo extends JpaRepository<Job, Integer> {
}
