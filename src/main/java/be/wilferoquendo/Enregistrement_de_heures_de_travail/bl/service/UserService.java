package be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.service;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.UserEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto.UpdateUserDTO;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto.UserDTO;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.form.UserForm;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserService {

    UserEntity findById(Long id);

    boolean existsByUserId(Long userId);

    UserEntity getUserEntityById(Long userId);
    List<UserDTO> findAllUsers();

    void saveUser(UserForm userForm);

    void updateUser(UpdateUserDTO newDataUser);
}
