package oosd.sait.ipdbuddyapi;

import jakarta.transaction.Transactional;
import oosd.sait.ipdbuddyapi.entities.*;
import oosd.sait.ipdbuddyapi.enums.Billables;
import oosd.sait.ipdbuddyapi.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BillableItemSubmissionRepo billableItemSubmissionRepo;

    @Autowired
    private ContractorRepo contractorRepo;

    @Autowired
    private JobRepo jobRepo;



    @Transactional
    @Override
    public void run(String... args) throws Exception {
        // Create User
        User user = new User();
        user.setEmail("contractor@example.com");
        user.setPassword("password123");

        user = userRepo.save(user);

        // Create Contractor linked to User
        Contractor contractor = new Contractor();
        contractor.setFirstName("John");
        contractor.setLastName("Doe");
        contractor.setEmail("john.doe@example.com");
        contractor.setPhoneNumber("123-456-7890");
        contractor.setTaxRate(new BigDecimal("0.13"));
        contractor.setSavingsRate(new BigDecimal("0.10"));
        contractor.setUser(user);

        contractor = contractorRepo.save(contractor);

        // Create Job
        Job job = new Job();
        job.setDate(LocalDate.now());
        job.setAddress("1234 Maple St, SomeCity, SomeProvince");
        job.setContractor(contractor);

        job = jobRepo.save(job);

        // Create BillableItemSubmissions (linking Billables + Job + quantity)
        BillableItemSubmission insulation = new BillableItemSubmission();
        insulation.setJob(job);
        insulation.setBillableType(Billables.INSULATION);
        insulation.setQuantity(50); // Example: 50 sq ft

        BillableItemSubmission drywall = new BillableItemSubmission();
        drywall.setJob(job);
        drywall.setBillableType(Billables.DRYWALL);
        drywall.setQuantity(30); // Example: 30 panels

        BillableItemSubmission scaffolding = new BillableItemSubmission();
        scaffolding.setJob(job);
        scaffolding.setBillableType(Billables.SCAFFOLDING);
        scaffolding.setQuantity(1); // Just setup once

        billableItemSubmissionRepo.save(insulation);
        billableItemSubmissionRepo.save(drywall);
        billableItemSubmissionRepo.save(scaffolding);

        System.out.println("Database seeded successfully!");
    }
}
