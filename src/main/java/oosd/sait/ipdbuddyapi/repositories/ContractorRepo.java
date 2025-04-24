package oosd.sait.ipdbuddyapi.repositories;

import oosd.sait.ipdbuddyapi.entities.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractorRepo extends JpaRepository<Contractor, Integer > {
}
