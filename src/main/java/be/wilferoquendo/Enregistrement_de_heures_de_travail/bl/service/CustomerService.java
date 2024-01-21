package be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.service;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.CustomerEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.projection.CustomerByPhoneNumber;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto.CustomerDTO;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto.UpdateCustomerDTO;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.form.CustomerForm;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerService {

    List<CustomerDTO> findAllCustomer();

    void saveCustomer(CustomerForm CustomerForm);

    boolean existsCustomerById(Long customerId);

    CustomerByPhoneNumber findCustomerByPhoneNumber(String phoneNumber);

    void updateCustomer(UpdateCustomerDTO newDataCustomer);
}
