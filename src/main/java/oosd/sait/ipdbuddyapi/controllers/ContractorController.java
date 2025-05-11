package oosd.sait.ipdbuddyapi.controllers;

import jakarta.validation.Valid;
import oosd.sait.ipdbuddyapi.dto.contractor.ContractorProfileDTO;
import oosd.sait.ipdbuddyapi.dto.contractor.ContractorProfileUpdateDTO;
import oosd.sait.ipdbuddyapi.entities.Contractor;
import oosd.sait.ipdbuddyapi.repositories.ContractorRepo;
import oosd.sait.ipdbuddyapi.services.ContractorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/contractor")
public class ContractorController {

    private final ContractorService contractorService;

    private final ContractorRepo contractorRepo;
    public ContractorController(ContractorRepo contractorRepo, ContractorService contractorService) {
        this.contractorRepo = contractorRepo;
        this.contractorService = contractorService;
    }

    @GetMapping("/me")
    public ResponseEntity<Optional<ContractorProfileDTO>> getMyProfile(@AuthenticationPrincipal OidcUser user) {
        String email = user.getEmail();
        Optional<Contractor> contractorOptional = contractorRepo.findByEmail(email);

        if (contractorOptional.isPresent()){
            Contractor contractor = contractorOptional.get();
            ContractorProfileDTO profileDTO = mapToDTO(contractor);
            return ResponseEntity.ok(Optional.ofNullable(profileDTO));
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    private ContractorProfileDTO mapToDTO(Contractor contractor) {
        ContractorProfileDTO dto = new ContractorProfileDTO();
        dto.setFirstName(contractor.getFirstName());
        dto.setLastName(contractor.getLastName());
        dto.setEmail(contractor.getEmail());
        dto.setPhoneNumber(contractor.getPhoneNumber());
        dto.setTaxRate(contractor.getTaxRate());
        dto.setSavingsRate(contractor.getSavingsRate());
        return dto;
    };

    ;

    @PutMapping("/me")
    public ResponseEntity<?> updateMyProfile(
            @AuthenticationPrincipal OidcUser user,
            @Valid @RequestBody ContractorProfileUpdateDTO dto) {

        String email = user.getEmail();

        return contractorService.updateContracterByEmail(email, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}//! Class
//@GetMapping("/me")
//public ResponseEntity<Contractor> getMyProfile(@AuthenticationPrincipal OidcUser user) {
//    String email = user.getEmail();
//    return contractorRepo.findByEmail(email).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
//};