package oosd.sait.ipdbuddyapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @OneToOne
    private Contractor contractor;

    public User() {
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
