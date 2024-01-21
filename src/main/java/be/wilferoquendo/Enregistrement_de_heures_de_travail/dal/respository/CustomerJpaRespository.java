package be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.respository;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.CustomerEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.projection.CustomerByPhoneNumber;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto.UpdateCustomerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerJpaRespository extends JpaRepository<CustomerEntity, Long> {

    @Query(
            """
                    SELECT c.customerName AS customerName, c.email AS email, c.address AS address, c.phoneNumber AS phoneNumber
                    FROM CustomerEntity c
                      WHERE c.phoneNumber = :phoneNumber
                              """
    )
    CustomerByPhoneNumber findCustomerByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Query(
            value = """
                    UPDATE client
                    SET  nom_de_client = :#{#newDataCustomer.newCustomerName}, email = :#{#newDataCustomer.newEmail}, gsm = :#{#newDataCustomer.newPhoneNumber}, adresse = :#{#newDataCustomer.newAddress}
                    WHERE client_id = :#{#newDataCustomer.customerId}
                    """,
            nativeQuery = true
    )
    @Modifying
    void updateCustomer(@Param("newDataCustomer") UpdateCustomerDTO newDataCustomer);
}
