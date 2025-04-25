package oosd.sait.ipdbuddyapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobSubmissionResponseDTO {

    private Long jobId;
    private BigDecimal taxAmount;
    private BigDecimal savingsAmount;
    private BigDecimal grandTotalAmount;
    private String message;

    private List<BillableItemsSummary> billableItemsSummary;

}
