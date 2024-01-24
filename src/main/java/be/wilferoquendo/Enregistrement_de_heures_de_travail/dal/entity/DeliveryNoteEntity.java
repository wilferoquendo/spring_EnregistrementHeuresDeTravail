package be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "bon_de_livraison")
@Getter
@Setter
public class DeliveryNoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bon_de_livraison_id")
    private Long deliveryNoteId;

    private String date;
    @Column(name = "heure_de_debut")
    private String startTime;

    @Column(name = "heure_du_final")
    private String endTime;

    @Column(name = "nom_de_projet")
    private String projectName;

    @ManyToOne
    @JoinColumn(name = "client_id", insertable = false,updatable = false)
    private CustomerEntity customerEntity;

    @OneToMany(targetEntity = WorkHourEntity.class, mappedBy = "deliveryNoteEntity")
    private List<WorkHourEntity> workHourEntity;
}
