package oosd.sait.ipdbuddyapi.controllers;

import jakarta.validation.Valid;
import oosd.sait.ipdbuddyapi.dto.JobSubmissionRequestDTO;
import oosd.sait.ipdbuddyapi.dto.JobSubmissionResponseDTO;
import oosd.sait.ipdbuddyapi.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/v1")
public class JobController {

    @Autowired
    private JobService jobService;

    /**
     * Creates a new job submission
     *
     * @param requestDTO The job details including billable items
     * @return Response with the summmary of the submitted job and calculation results.
     */
    @PostMapping("/jobs")
    public ResponseEntity<JobSubmissionResponseDTO> submitJob(@RequestBody @Valid JobSubmissionRequestDTO requestDTO) {
        JobSubmissionResponseDTO responseDTO  = jobService.handleJobSubmission(requestDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{jobId}")
                .buildAndExpand(responseDTO.getJobId())
                .toUri();
        return ResponseEntity.created(location).body(responseDTO);


    }

}
