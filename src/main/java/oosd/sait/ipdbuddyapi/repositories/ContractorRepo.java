package oosd.sait.ipdbuddyapi.repositories;

import oosd.sait.ipdbuddyapi.entities.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ContractorRepo extends JpaRepository<Contractor, Integer > {
}
