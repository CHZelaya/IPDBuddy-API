package oosd.sait.ipdbuddyapi.services;

import jakarta.validation.Valid;
import oosd.sait.ipdbuddyapi.dto.contractor.ContractorProfileResponseDTO;
import oosd.sait.ipdbuddyapi.dto.contractor.ContractorProfileUpdateDTO;
import oosd.sait.ipdbuddyapi.entities.Contractor;
import oosd.sait.ipdbuddyapi.repositories.ContractorRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContractorService {

    private final ContractorRepo contractorRepo;

    public ContractorService(ContractorRepo contractorRepo){
        this.contractorRepo = contractorRepo;
    }

    public Optional<ContractorProfileResponseDTO> getContractorByEmail(String email) {
        return contractorRepo.findByEmail(email).map(contractor -> new ContractorProfileResponseDTO(
                contractor.getFirstName(),
                contractor.getLastName(),
                contractor.getPhoneNumber(),
                contractor.getTaxRate(),
                contractor.getSavingsRate()
                )
        );
    };



    public Optional<ContractorProfileResponseDTO> updateContracterByEmail(String email, @Valid ContractorProfileUpdateDTO dto) {
        return contractorRepo.findByEmail(email).map(contractor -> {
            contractor.setFirstName(dto.getFirstName());
            contractor.setLastName(dto.getLastName());
            contractor.setPhoneNumber(dto.getPhoneNumber());
            contractor.setTaxRate(dto.getTaxRate());
            contractor.setSavingsRate(dto.getSavingsRate());

            Contractor updated = contractorRepo.save(contractor);
            return new ContractorProfileResponseDTO(
                    updated.getFirstName(),
                    updated.getLastName(),
                    updated.getPhoneNumber(),
                    updated.getTaxRate(),
                    updated.getSavingsRate()
            );

        });
    }


}//! class
