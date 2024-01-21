package be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.controller;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.impl.CustomerServiceImpl;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.service.CustomerService;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.CustomerEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.projection.CustomerByPhoneNumber;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto.CustomerDTO;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.form.CustomerForm;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;


    @GetMapping("/listcustomer")
    public ResponseEntity<List<CustomerDTO>> getAll() {
        List<CustomerDTO> customerDTOList = customerService.findAllCustomer();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDTOList);
    }

    @GetMapping("/customerbyphonenumber")
    public ResponseEntity<Object> findByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber) {
        CustomerByPhoneNumber customerByPhoneNumber =
                customerService.findCustomerByPhoneNumber(phoneNumber);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(customerByPhoneNumber);

    }

    @PostMapping("/savecustomer")
    public ResponseEntity<String> saveCustomer(@RequestBody CustomerForm customerForm) {
        customerService.saveCustomer(customerForm);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(customerForm.getCustomerName());
    }
}


