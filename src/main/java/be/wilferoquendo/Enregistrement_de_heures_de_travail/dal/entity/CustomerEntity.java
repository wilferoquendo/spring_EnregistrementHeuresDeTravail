package be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.TimeZoneColumn;

import java.util.List;

@Entity
@Table(name = "client")
@Getter
@Setter
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long customerId;

    @Column(name = "nom_de_client")
    private String customerName;

    @Column(unique = true)
    private String email;

    @Column(name = "gsm")
    private String phoneNumber;

    @Column(name = "adresse")
    private String address;

    @OneToMany
    private List<DeliveryNoteEntity> deliveryNoteEntityList;

}
