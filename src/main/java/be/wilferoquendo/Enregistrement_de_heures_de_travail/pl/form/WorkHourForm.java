package be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.form;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.service.UserService;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.UserEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.WorkHourEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class WorkHourForm {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String projectName;
    private Long userId;

    public WorkHourEntity toEntity(UserEntity userEntity){
        WorkHourEntity workHourEntity = new WorkHourEntity();
        workHourEntity.setDate(getDate());
        workHourEntity.setStartTime(getStartTime());
        workHourEntity.setEndTime(getEndTime());
        workHourEntity.setProjectName(getProjectName());
        workHourEntity.setUserEntity(userEntity);

        return workHourEntity;
    }
}
