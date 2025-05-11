package oosd.sait.ipdbuddyapi.controllers;

import jakarta.validation.Valid;
import oosd.sait.ipdbuddyapi.dto.job.JobResponseDTO;
import oosd.sait.ipdbuddyapi.dto.job.JobSubmissionRequestDTO;
import oosd.sait.ipdbuddyapi.dto.job.JobSubmissionResponseDTO;
import oosd.sait.ipdbuddyapi.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class JobController {


    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/jobs")
    public ResponseEntity<List<JobResponseDTO>> getAllContractorJobs(
            @AuthenticationPrincipal OidcUser user) {

        String email = user.getEmail();
        List<JobResponseDTO> responseDTO = jobService.getAllContractorJobs(email);
        return ResponseEntity.ok(responseDTO);

    }

    @GetMapping("/jobs/{id}")
    public ResponseEntity<JobResponseDTO> getJobByContractor(
            @AuthenticationPrincipal OidcUser user,
            @PathVariable Long id) {

        String email = user.getEmail();
        JobResponseDTO responseDTO = jobService.getJobByContractor(email, id);
        return ResponseEntity.ok(responseDTO);
    }



    /**
     * Creates a new job submission
     *
     * @param requestDTO The job details including billable items
     * @return Response with the summmary of the submitted job and calculation results.
     */
    @PostMapping("/jobs/submit")
    public ResponseEntity<JobSubmissionResponseDTO> submitJob(
            @AuthenticationPrincipal OidcUser user,
            @RequestBody @Valid JobSubmissionRequestDTO requestDTO) {

        String email = user.getEmail();
        JobSubmissionResponseDTO responseDTO = jobService.handleJobSubmission(email, requestDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{jobId")
                .buildAndExpand(responseDTO.getJobId())
                .toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }

    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<Void> deleteJob(
            @AuthenticationPrincipal OidcUser user,
            @PathVariable Long id) {

        String email = user.getEmail();
        jobService.deleteJobByContractor(email, id);
        return ResponseEntity.noContent().build();
    }


}
