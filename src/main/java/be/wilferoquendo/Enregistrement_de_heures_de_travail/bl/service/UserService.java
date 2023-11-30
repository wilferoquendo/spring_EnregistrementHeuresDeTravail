package be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.service;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto.UserDTO;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.form.UserForm;

import java.util.List;


public interface UserService {
    List<UserDTO> findAllUsers();

    void saveUser(UserForm userForm);
}
