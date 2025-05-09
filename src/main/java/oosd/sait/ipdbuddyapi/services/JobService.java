package oosd.sait.ipdbuddyapi.services;

import oosd.sait.ipdbuddyapi.dto.billable.BillableItemsRequest;
import oosd.sait.ipdbuddyapi.dto.billable.BillableItemsSummary;
import oosd.sait.ipdbuddyapi.dto.job.JobResponseDTO;
import oosd.sait.ipdbuddyapi.dto.job.JobSubmissionRequestDTO;
import oosd.sait.ipdbuddyapi.dto.job.JobSubmissionResponseDTO;
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

    public JobSubmissionResponseDTO handleJobSubmission(String email,JobSubmissionRequestDTO requestDTO) {
        //Fetching the Contractor ID from the request
        Contractor contractor = contractorRepo.findByEmail( email).orElseThrow(() -> new IllegalArgumentException("Contractor not found"));

        //Creating and saving a new Job
        Job job = new Job();
        job.setContractor(contractor);
        job.setAddress(requestDTO.getAddress());
        job.setDate(requestDTO.getDate() != null ? requestDTO.getDate() : LocalDate.now());

        jobRepo.save(job);

        //Preparing the summary
        List<BillableItemsSummary> itemsSummaryList = new ArrayList<>();
        BigDecimal grandTotal = BigDecimal.ZERO;

        List<BillableItemsRequest> itemsRequests = requestDTO.getItemsRequests();

        if (itemsRequests != null && !itemsRequests.isEmpty()) {
            for (BillableItemsRequest itemsRequest : requestDTO.getItemsRequests()) {
                Billables billablesType = Billables.valueOf(itemsRequest.getBillableType());

                //* Calculate quantity * rate for each task done
                BigDecimal rate = billablesType.getRate();
                BigDecimal quantity = BigDecimal.valueOf(itemsRequest.getQuantity());
                BigDecimal total = rate.multiply(quantity);

                //* Helper method(s) to check for special cases and amend total based on special cases
                if (billablesType.equals(Billables.FIRE_CAULKING)) {
                    total = capFireCaulkingPay(rate, quantity);
                }

                BillableItemSubmission submission = new BillableItemSubmission();
                submission.setJob(job);
                submission.setBillableType(billablesType);
                submission.setQuantity(itemsRequest.getQuantity());
                submission.setNotes(itemsRequest.getNotes());
                submission.setTotalPrice(total);

                billableItemSubmissionRepo.save(submission);

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
            }
        }

        JobSubmissionResponseDTO response = new JobSubmissionResponseDTO(job.getId());
        response.setBillableItemsSummary(itemsSummaryList);
        response.setGrandTotalAmount(grandTotal);
        response.setTaxAmount(grandTotal.multiply(contractor.getTaxRate()));
        response.setSavingsAmount(grandTotal.multiply(contractor.getSavingsRate()));

        return response;
    }

    private BigDecimal capFireCaulkingPay(BigDecimal rate, BigDecimal quantity) {
        final BigDecimal maxPay = new BigDecimal("75.00");
        final BigDecimal total = rate.multiply(quantity);

        //* Capping pay at $75 for Fire Caulking.
        return total.compareTo(maxPay) > 0 ? maxPay : total;

    }


    public List<JobResponseDTO> getAllContractorJobs(String email) {

        Contractor contractor = contractorRepo.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("Contractor Not Found"));
        // Grabbing the Id from the email
        Long contractorId = contractor.getId();

        List<Job> jobs = jobRepo.findAllByContractor_Id(contractorId);

        List<JobResponseDTO> jobResponseDTOS = new ArrayList<>();

        for (Job job : jobs) {
            JobResponseDTO dto = new JobResponseDTO(
                    job.getId(),
                    job.getDate(),
                    job.getAddress()
            );
            jobResponseDTOS.add(dto);
        }

        return jobResponseDTOS;



    }

    public JobResponseDTO getJobByContractor(String email, long id) {

        Contractor contractor = contractorRepo.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("Contractor Not Found"));
        // Grabbing the Id from the email
        Job job = jobRepo.findById(id);

        if (!job.getContractor().getId().equals(contractor.getId())){
            throw new IllegalArgumentException("Job does not belong to a contractor");
        }
        // Grabbing all the billables

        List<BillableItemSubmission> submissions = billableItemSubmissionRepo.findAllByJob_Id(job.getId());

        List<BillableItemsSummary> itemsSummaryList = new ArrayList<>();

        for (BillableItemSubmission submission : submissions){
            BillableItemsSummary summary = new BillableItemsSummary(
                    submission.getBillableType().name(),
                    submission.getBillableType().getDescription(),
                    submission.getQuantity(),
                    submission.getBillableType().getRate(),
                    submission.getTotalPrice(),
                    job.getAddress(),
                    job.getDate()
            );
            itemsSummaryList.add(summary);
        }

        return new JobResponseDTO(
                job.getId(),
                job.getDate(),
                job.getAddress(),
                itemsSummaryList
        );

    }

    public void deleteJobByContractor(String email, long id) {
        Contractor contractor = contractorRepo.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Contractor Not Found"));

        Job job = jobRepo.findById(id);

        if (!job.getContractor().getId().equals(contractor.getId())) {
            throw new IllegalArgumentException("Unauthorized: Job does not belog to this contractor");
        }

        jobRepo.delete(job);

    }


}//!Class
