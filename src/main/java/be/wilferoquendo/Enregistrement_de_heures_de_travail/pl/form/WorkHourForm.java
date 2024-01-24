package be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.form;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.DeliveryNoteEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.UserEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.WorkHourEntity;
import lombok.Getter;
import lombok.Setter;

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
    private Long deliveryNoteId;

    public WorkHourEntity toEntity(UserEntity userEntity, DeliveryNoteEntity deliveryNoteEntity){
        WorkHourEntity workHourEntity = new WorkHourEntity();
        workHourEntity.setDate(getDate());
        workHourEntity.setStartTime(getStartTime());
        workHourEntity.setEndTime(getEndTime());
        workHourEntity.setProjectName(getProjectName());
        workHourEntity.setUserEntity(userEntity);
        workHourEntity.setDeliveryNoteEntity(deliveryNoteEntity);

        return workHourEntity;
    }
}
