package oosd.sait.ipdbuddyapi.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillableItemsSummary {

    // This class will send the response data to the front end, which will be used to populate the PDF using a front end PDF library to allow the user to download it

    private String type;
    private String description;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal total;
    private String jobAddress;
    private LocalDate jobDate;

}
