package be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCustomerDTO {
    private Long customerId;

    private String newCustomerName;

    private String newEmail;

    private String newPhoneNumber;

    private String newAddress;
}
