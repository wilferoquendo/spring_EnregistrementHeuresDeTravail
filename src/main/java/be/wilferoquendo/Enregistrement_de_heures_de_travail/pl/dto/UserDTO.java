package be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;

@Getter
@Setter
public class UserDTO {

    private Long userId;
    private String userName;
    private BigDecimal hourlySalaryCost;

    public static UserDTO fromEntity(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userEntity.getUserId());
        userDTO.setUserName(userEntity.getUserName());
        userDTO.setHourlySalaryCost(userEntity.getHourlySalaryCost());
        return userDTO;
    }
}
