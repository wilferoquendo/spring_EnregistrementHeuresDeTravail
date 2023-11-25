package be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.form;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.UserEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.WorkHourEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

import java.util.List;

@Getter
@Setter
public class UserForm {

    private Long id;
    private String name;

    public UserEntity toEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(getName());
        return userEntity;
    }
}
