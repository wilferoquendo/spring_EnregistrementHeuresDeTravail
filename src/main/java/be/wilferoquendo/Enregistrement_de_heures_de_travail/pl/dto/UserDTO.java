package be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.UserEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.WorkHourEntity;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
public class UserDTO {

    private Long id;
    private String name;

    public static UserDTO fromEntity(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setName(userEntity.getName());
        return userDTO;
    }
}
