package oosd.sait.ipdbuddyapi.dto.job;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import oosd.sait.ipdbuddyapi.dto.billable.BillableItemsRequest;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobSubmissionRequestDTO {


    private LocalDate date;
    private String address;
    private List<BillableItemsRequest> itemsRequests;

}
