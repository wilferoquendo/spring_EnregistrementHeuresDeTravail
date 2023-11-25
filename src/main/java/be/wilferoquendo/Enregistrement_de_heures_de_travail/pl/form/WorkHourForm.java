package be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.form;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.WorkHourEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto.WorkHourDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkHourForm {
    private String date;
    private String startTime;
    private String endTime;
    private String projectName;

    public WorkHourEntity toEntity(){
        WorkHourEntity workHourEntity = new WorkHourEntity();
        workHourEntity.setDate(getDate());
        workHourEntity.setStartTime(getStartTime());
        workHourEntity.setEndTime(getEndTime());
        workHourEntity.setProjectName(getProjectName());
        return workHourEntity;
    }
}
