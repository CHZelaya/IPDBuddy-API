package oosd.sait.ipdbuddyapi.dto.contractor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ContractorProfileDTO {

    //Properties and validation

    @NotNull
    @Pattern(regexp = "^[A-Za-zÀ-ÿ]+(-[A-Za-zÀ-ÿ]+)*$\n")
    private String firstName;

    @NotNull
    @Pattern(regexp = "^[A-Za-zÀ-ÿ]+(-[A-Za-zÀ-ÿ]+)*$\n")
    private String lastName;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Pattern(regexp = "^(\\+1[-.\\s]?)?(\\(?\\d{3}\\)?[-.\\s]?)\\d{3}[-.\\s]?\\d{4}$\n" )
    private String phoneNumber;

    private BigDecimal taxRate;

    private BigDecimal savingsRate;

}
