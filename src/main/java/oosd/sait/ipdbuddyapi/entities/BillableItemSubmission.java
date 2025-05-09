package oosd.sait.ipdbuddyapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import oosd.sait.ipdbuddyapi.enums.Billables;

import java.math.BigDecimal;

@Entity
public class BillableItemSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private Billables billableType;

    @NotNull
    private int quantity;

    @NotNull
    private BigDecimal rate;

    @NotNull
    private BigDecimal totalPrice;

    private String notes;

    @NotNull
    @ManyToOne
    private Job job;

    public BillableItemSubmission() {
    }

    public long getId() {
        return id;
    }

    public Billables getBillableType() {
        return billableType;
    }

    public void setBillableType(Billables billableType) {
        this.billableType = billableType;
        this.rate = billableType.getRate();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
} //* Class
