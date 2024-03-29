package be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "heures_de_travail")
@Getter
@Setter
public class WorkHourEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "heures_id")
    private Long hourId;

    private LocalDate date;

    @Column(name = "heure_de_debut", columnDefinition = "TIME")
    private LocalTime startTime;

    @Column(name = "heure_du_final", columnDefinition = "TIME")
    private LocalTime endTime;

    @Column(name = "heures_de_travail")
    private BigDecimal calculationOfWorkingHours;

    @Column(name = "cout_heure")
    private BigDecimal hourlySalaryCost;

    @Column(name = "cout_total")
    private BigDecimal totalSalaryCost;

    @Column(name = "nom_de_projet")
    private String projectName;

    @Column(name = "date_de_creation")
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false)
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "bon_de_livraison_id", updatable = false)
    private DeliveryNoteEntity deliveryNoteEntity;
    @PrePersist
    public void prePersist() {
        creationDate = LocalDateTime.now();
    }
}
