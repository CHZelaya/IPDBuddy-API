package oosd.sait.ipdbuddyapi.controllers;

import jakarta.validation.Valid;
import oosd.sait.ipdbuddyapi.dto.contractor.ContractorProfileUpdateDTO;
import oosd.sait.ipdbuddyapi.entities.Contractor;
import oosd.sait.ipdbuddyapi.repositories.ContractorRepo;
import oosd.sait.ipdbuddyapi.services.ContractorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Contractor> getMyProfile(@AuthenticationPrincipal OidcUser user) {
      String email = user.getEmail();
      return contractorRepo.findByEmail(email).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    };

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
