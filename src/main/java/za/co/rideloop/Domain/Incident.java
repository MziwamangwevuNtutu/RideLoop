package za.co.rideloop.Domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

/**
 * Incident.java
 * Incident model class
 *
 * @author : Swatsi Bongani Ratia
 * @studnr : 230724477
 * @group : 3I
 * @Java version: "21.0.3" 2024-04-16 LTS
 */
@Entity
@Table(name = "incidents")
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer incidentID;

    @Column(nullable = false)
    private String type; // Security, Maintenance, Accident

    @Column(nullable = false)
    private String description;

    @Column(name = "date_reported", nullable = false, updatable = false)
    private LocalDate dateReported = LocalDate.now(ZoneId.of("Africa/Johannesburg"));

    // ✅ Owning side of Many-to-Many
    @ManyToMany
    @JoinTable(
            name = "incident_rentals",
            joinColumns = @JoinColumn(name = "incident_id"),
            inverseJoinColumns = @JoinColumn(name = "rental_id")
    )
    private List<Rental> rentals;

    protected Incident() {}

    private Incident(Builder builder) {
        this.incidentID = builder.incidentID;
        this.type = builder.type;
        this.description = builder.description;
        this.dateReported = builder.dateReported != null ? builder.dateReported : LocalDate.now();
        this.rentals = builder.rentals;
    }

    public Integer getIncidentID() { return incidentID; }
    public String getType() { return type; }
    public String getDescription() { return description; }
    public LocalDate getDateReported() { return dateReported; }
    public List<Rental> getRentals() { return rentals; }



    public static class Builder {
        private Integer incidentID;
        private String type;
        private String description;
        private LocalDate dateReported;
        private List<Rental> rentals;

        public Builder incidentID(Integer incidentID) { this.incidentID = incidentID; return this; }
        public Builder type(String type) { this.type = type; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder dateReported(LocalDate dateReported) { this.dateReported = dateReported; return this; }
        public Builder rentals(List<Rental> rentals) { this.rentals = rentals; return this; }

        public Incident build() { return new Incident(this); }
    }
}
