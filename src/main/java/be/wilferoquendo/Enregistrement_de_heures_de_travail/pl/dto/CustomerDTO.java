package be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.CustomerEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO {

    private String customerName;
    private String email;
    private String phoneNumber;
    private String address;

    public static CustomerDTO fromEntity(CustomerEntity customerEntity) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerName(customerEntity.getCustomerName());
        customerDTO.setEmail(customerEntity.getEmail());
        customerDTO.setPhoneNumber(customerEntity.getPhoneNumber());
        customerDTO.setAddress(customerEntity.getPhoneNumber());
        return customerDTO;
    }
}
