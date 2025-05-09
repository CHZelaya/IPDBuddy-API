package oosd.sait.ipdbuddyapi.repositories;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import oosd.sait.ipdbuddyapi.entities.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource
public interface ContractorRepo extends JpaRepository<Contractor, Integer> {
    Optional<Contractor> findByEmail(@NotNull @Email String email);

    Long getContractorByEmailAndId(@NotNull @Email String email, Long id);

    Long getContractorByEmail(@NotNull @Email String email);
}
