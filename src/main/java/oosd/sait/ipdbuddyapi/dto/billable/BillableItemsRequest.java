package oosd.sait.ipdbuddyapi.dto.billable;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillableItemsRequest {

    private String billableType;
    private int quantity;
    private String notes;

}
