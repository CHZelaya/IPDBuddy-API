package oosd.sait.ipdbuddyapi;

import jakarta.transaction.Transactional;
import oosd.sait.ipdbuddyapi.entities.BillableWork;
import oosd.sait.ipdbuddyapi.entities.Contractor;
import oosd.sait.ipdbuddyapi.entities.Job;
import oosd.sait.ipdbuddyapi.entities.User;
import oosd.sait.ipdbuddyapi.enums.Billables;
import oosd.sait.ipdbuddyapi.repositories.BillableWorkRepo;
import oosd.sait.ipdbuddyapi.repositories.ContractorRepo;
import oosd.sait.ipdbuddyapi.repositories.JobRepo;
import oosd.sait.ipdbuddyapi.repositories.UserRepo;
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
    private BillableWorkRepo billableWorkRepo;

    @Autowired
    private ContractorRepo contractorRepo;

    @Autowired
    private JobRepo jobRepo;



    @Transactional
    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setEmail("contractor@example.com");
        user.setPassword("password123");

        user = userRepo.save(user);

        // Create Contractor linked to the User
        Contractor contractor = new Contractor();
        contractor.setFirstName("John");
        contractor.setLastName("Doe");
        contractor.setEmail("john.doe@example.com");
        contractor.setPhoneNumber("123-456-7890");
        contractor.setTaxRate(new BigDecimal("0.13"));
        contractor.setSavingsRate(new BigDecimal("0.10"));
        contractor.setUser(user);

        contractor = contractorRepo.save(contractor);

        // Create Job for the Contractor
        Job job = new Job();
        job.setDate(LocalDate.now());
        job.setAddress("1234 Maple St, SomeCity, SomeProvince");
        job.setContractor(contractor);

        job = jobRepo.save(job);

        // Create BillableWork for the Job
        BillableWork billableWork1 = new BillableWork();
        billableWork1.setJob(job);
        billableWork1.setType(Billables.INSULATION);
        billableWork1.setContext("Insulation of attic");
        billableWork1.setNotes("High quality spray insulation");

        BillableWork billableWork2 = new BillableWork();
        billableWork2.setJob(job);
        billableWork2.setType(Billables.SCAFFOLDING);
        billableWork2.setContext("Setting up scaffolding for work on the upper floors");
        billableWork2.setNotes("Standard scaffolding for three floors");

        billableWorkRepo.save(billableWork1);
        billableWorkRepo.save(billableWork2);

        System.out.println("Database seeded successfully!");
    }
}
