package oosd.sait.ipdbuddyapi.services;

import oosd.sait.ipdbuddyapi.dto.BillableItemsRequest;
import oosd.sait.ipdbuddyapi.dto.BillableItemsSummary;
import oosd.sait.ipdbuddyapi.dto.JobSubmissionRequestDTO;
import oosd.sait.ipdbuddyapi.dto.JobSubmissionResponseDTO;
import oosd.sait.ipdbuddyapi.entities.BillableItemSubmission;
import oosd.sait.ipdbuddyapi.entities.Contractor;
import oosd.sait.ipdbuddyapi.entities.Job;
import oosd.sait.ipdbuddyapi.enums.Billables;
import oosd.sait.ipdbuddyapi.repositories.BillableItemSubmissionRepo;
import oosd.sait.ipdbuddyapi.repositories.ContractorRepo;
import oosd.sait.ipdbuddyapi.repositories.JobRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {

    private JobRepo jobRepo;

    private ContractorRepo contractorRepo;

    private BillableItemSubmissionRepo billableItemSubmissionRepo;

    public JobSubmissionResponseDTO handleJobSubmission(JobSubmissionRequestDTO requestDTO) {
        //Fetching the Contractor ID from the request
        Contractor contractor = contractorRepo.findById((int) requestDTO.getContractorId()).orElseThrow(() -> new IllegalArgumentException("Contractor not found"));

        //Creating and saving a new Job
        Job job = new Job();
        job.setContractor(contractor);
        job.setAddress(requestDTO.getAddress());
        job.setDate(requestDTO.getDate() != null ? requestDTO.getDate() : LocalDate.now());

        jobRepo.save(job);

        //Preparing the summary
        List<BillableItemsSummary> itemsSummaryList = new ArrayList<>();
        BigDecimal grandTotal = BigDecimal.ZERO;

        for(BillableItemsRequest itemsRequest : requestDTO.getItemsRequests()) {
            Billables billablesType = Billables.valueOf(itemsRequest.getBillableType());

            //* Calculate quantity * rate for each task done
            BigDecimal rate = billablesType.getRate();
            BigDecimal quantity = BigDecimal.valueOf(itemsRequest.getQuantity());
            BigDecimal total = rate.multiply(quantity);

            BillableItemSubmission submission = new BillableItemSubmission();
            submission.setJob(job);
            submission.setBillableType(billablesType);
            submission.setQuantity(itemsRequest.getQuantity());
            submission.setNotes(itemsRequest.getNotes());
            submission.setTotalPrice(total);

            billableItemSubmissionRepo.save(submission);

            //* Summary will be sent to the front-end to be printed on the PDF
            itemsSummaryList.add(new BillableItemsSummary(
                    billablesType.name(),
                    billablesType.getDescription(),
                    itemsRequest.getQuantity(),
                    rate,
                    total,
                    job.getAddress(),
                    job.getDate()
            ));

            grandTotal = grandTotal.add(total);

            JobSubmissionResponseDTO responseDTO = new JobSubmissionResponseDTO();
            responseDTO.setBillableItemsSummary(itemsSummaryList);
            responseDTO.setGrandTotalAmount(grandTotal);


        }







        return null;
    }

}//!Class
