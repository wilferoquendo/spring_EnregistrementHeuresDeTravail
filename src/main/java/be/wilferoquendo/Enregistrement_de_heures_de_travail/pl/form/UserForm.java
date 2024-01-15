package be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.form;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserForm {

    private String name;

    public UserEntity toEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(getName());
        return userEntity;
    }
}
