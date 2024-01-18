package be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name", unique = true)
    private String userName;

    @Column(name = "cout_heure", nullable = false)
    private BigDecimal hourlySalaryCost;

    @OneToMany(targetEntity = WorkHourEntity.class,fetch = FetchType.EAGER, mappedBy = "userEntity")
    private List<WorkHourEntity> userEntity;

}
