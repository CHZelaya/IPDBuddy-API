package oosd.sait.ipdbuddyapi.repositories;

import oosd.sait.ipdbuddyapi.entities.BillableItemSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface BillableItemSubmissionRepo extends JpaRepository<BillableItemSubmission, Integer> {
}
