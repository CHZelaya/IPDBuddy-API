package oosd.sait.ipdbuddyapi.dto.contractor;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ContractorProfileResponseDTO {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private BigDecimal taxRate;
    private BigDecimal savingsRate;
}
