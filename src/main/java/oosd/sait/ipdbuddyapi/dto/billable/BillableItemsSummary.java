package oosd.sait.ipdbuddyapi.dto.billable;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillableItemsSummary {

    private String type;
    private String description;
    private int quantity;
    private BigDecimal rate;
    private BigDecimal total;
    private String jobAddress;
    private LocalDate jobDate;

}
