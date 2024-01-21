package be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.respository;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.UserEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto.UpdateHourWorkHourDTO;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto.UpdateUserDTO;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    @Query(
            value = """
                    UPDATE users
                    SET user_name = :#{#newDataUser.newUserName}, cout_heure = :#{#newDataUser.newHourlySalaryCost}
                    WHERE user_id  = :#{#newDataUser.userId}
                    """,
            nativeQuery = true
    )
    @Modifying
    void updateUser(@Param("newDataUser") UpdateUserDTO newDataUser);
}
