package oosd.sait.ipdbuddyapi.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "contractors")
public class ContractorEntity {

    //* Contractor details
   @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

   @Column(nullable = false)
    private String firstName;

   @Column(nullable = false)
    private String lastName;

   @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
   private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "contractors")
    private UserEntity user;

    //* Money and Tax related
    private BigDecimal taxRate;

    private BigDecimal savingsRate;


   //* Empty Constructor
    public ContractorEntity() {
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
