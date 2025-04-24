package oosd.sait.ipdbuddyapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import oosd.sait.ipdbuddyapi.enums.Billables;

import java.util.UUID;

@Entity
public class BillableWork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_id", referencedColumnName = "id")
    @NotNull
    private Job job;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Billables type;

    private String context;
    private String notes;

    public BillableWork() {
    }

    public Long getId() {
        return id;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Billables getType() {
        return type;
    }

    public void setType(Billables type) {
        this.type = type;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
