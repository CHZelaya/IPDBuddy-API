package oosd.sait.ipdbuddyapi.controllers;

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

@RestController
@RequestMapping("api/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping
    public ResponseEntity<JobSubmissionResponseDTO> submitJob(@RequestBody JobSubmissionRequestDTO requestDTO) {
        JobSubmissionResponseDTO responseDTO  = jobService.handleJobSubmission(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);


    }

}
