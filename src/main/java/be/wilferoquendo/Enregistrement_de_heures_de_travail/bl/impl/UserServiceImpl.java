package be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.impl;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.service.UserService;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.UserEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.respository.UserJpaRepository;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto.UserDTO;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserJpaRepository userJpaRepository;

    public UserServiceImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<UserEntity> userEntities = userJpaRepository.findAll();
        List<UserDTO> userDTOList = userEntities.stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
        return userDTOList;
    }

    @Override
    public void saveUser(UserForm userForm) {
        UserEntity userEntity = userForm.toEntity();
        userJpaRepository.save(userEntity);
    }
}
