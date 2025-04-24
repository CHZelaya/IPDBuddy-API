package oosd.sait.ipdbuddyapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "contractors")
public class Contractor {

    //* Contractor details
   @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

   @NotNull
   @Column(nullable = false)
    private String firstName;

   @NotNull
   @Column(nullable = false)
    private String lastName;

   @NotNull
   @Column(unique = true, nullable = false)
    private String email;

   @NotNull
   @Column(nullable = false)
   private String phoneNumber;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;

    @OneToMany
    private List<Job> jobs = new ArrayList<>();

    //* Money and Tax related
    private BigDecimal taxRate;

    private BigDecimal savingsRate;


   //* Empty Constructor
    public Contractor() {
    }

    //* Getters & Setters


    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getSavingsRate() {
        return savingsRate;
    }

    public void setSavingsRate(BigDecimal savingsRate) {
        this.savingsRate = savingsRate;
    }
}// ! class
