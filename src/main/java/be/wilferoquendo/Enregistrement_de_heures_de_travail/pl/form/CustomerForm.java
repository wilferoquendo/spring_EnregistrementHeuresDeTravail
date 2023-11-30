package be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.form;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.CustomerEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerForm {
    private String customerName;
    private String email;
    private String phoneNumber;
    private String address;


    public CustomerEntity toEntity(){
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCustomerName(getCustomerName());
        customerEntity.setEmail(getEmail());
        customerEntity.setPhoneNumber(getPhoneNumber());
        customerEntity.setAddress(getAddress());
        return customerEntity;
    }

}
