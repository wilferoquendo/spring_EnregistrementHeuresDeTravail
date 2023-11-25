package be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.UserEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.WorkHourEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkHourDTO {

    private Long id;
    private String date;
    private String startTime;
    private String endTime;
    private String projectName;


    public static WorkHourDTO fromEntity(WorkHourEntity workHourEntity){
        WorkHourDTO workHourDTO = new WorkHourDTO();
        workHourDTO.setId(workHourEntity.getId());
        workHourDTO.setDate(workHourEntity.getDate());
        workHourDTO.setStartTime(workHourEntity.getStartTime());
        workHourDTO.setEndTime(workHourEntity.getEndTime());
        workHourDTO.setProjectName(workHourEntity.getProjectName());
        return workHourDTO;
    }
}
