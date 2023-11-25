package be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "heures_de_travail")
@Getter
@Setter
public class WorkHourEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "heures_id")
    private Long id;

    private String date;

    @Column(name = "heure_de_debut")
    private String startTime;

    @Column(name = "heure_du_final")
    private String endTime;

    @Column(name = "nom_de_projet")
    private String projectName;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity userEntity;





}
