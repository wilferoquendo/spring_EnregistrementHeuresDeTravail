package be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.form;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UserForm {

    private String name;
    private BigDecimal hourlySalaryCost;

    public UserEntity toEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(getName());
        userEntity.setHourlySalaryCost(getHourlySalaryCost());
        return userEntity;
    }
}
