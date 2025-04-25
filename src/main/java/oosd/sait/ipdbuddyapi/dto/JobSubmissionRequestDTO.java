package oosd.sait.ipdbuddyapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobSubmissionRequestDTO {

    private long contractorId;
    private LocalDate date;
    private String address;

    private List<BillableItemsRequest> itemsRequests;

//    public long getContractorId() {
//        return contractorId;
//    }
//
//    public void setContractorId(long contractorId) {
//        this.contractorId = contractorId;
//    }
//
//    public LocalDate getDate() {
//        return date;
//    }
//
//    public void setDate(LocalDate date) {
//        this.date = date;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public List<BillableItemsRequest> getItemsRequests() {
//        return itemsRequests;
//    }
//
//    public void setItemsRequests(List<BillableItemsRequest> itemsRequests) {
//        this.itemsRequests = itemsRequests;
//    }
}
