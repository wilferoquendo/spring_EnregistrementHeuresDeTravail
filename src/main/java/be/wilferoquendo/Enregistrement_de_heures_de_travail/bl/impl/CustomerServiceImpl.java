package be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.impl;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.exception.RequestNotFoundException;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.service.CustomerService;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.CustomerEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.projection.CustomerByPhoneNumber;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.respository.CustomerJpaRespository;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto.CustomerDTO;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto.UpdateCustomerDTO;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.form.CustomerForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerJpaRespository customerJpaRespository;

    public CustomerServiceImpl(CustomerJpaRespository customerJpaRespository) {
        this.customerJpaRespository = customerJpaRespository;
    }

    @Override
    public List<CustomerDTO> findAllCustomer() {
        List<CustomerEntity> customerEntities = customerJpaRespository.findAll();
        List<CustomerDTO> customerDTOList = customerEntities.stream()
                .map(CustomerDTO::fromEntity)
                .collect(Collectors.toList());
        return customerDTOList;
    }

    @Override
    public void saveCustomer(CustomerForm customerForm) {
            CustomerEntity customerEntity = customerForm.toEntity();
            customerJpaRespository.save(customerEntity);
    }

    @Override
    public boolean existsCustomerById(Long customerId) {
        return customerJpaRespository.existsById(customerId);
    }

    @Override
    public CustomerByPhoneNumber findCustomerByPhoneNumber(String phoneNumber) {
        return customerJpaRespository.findCustomerByPhoneNumber(phoneNumber);
    }

    @Override
    @Transactional
    public void updateCustomer(UpdateCustomerDTO newDataCustomer) {
        try {
            customerJpaRespository.updateCustomer(newDataCustomer);
        }catch (Exception e) {
            throw new RequestNotFoundException("@Service " + e);
        }
    }

}
