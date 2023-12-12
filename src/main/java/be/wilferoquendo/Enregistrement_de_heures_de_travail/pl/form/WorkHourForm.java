package be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.form;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.WorkHourEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalTime;

@Getter
@Setter
public class WorkHourForm {
    private String date;
    private LocalTime startTime;
    private LocalTime endTime;
    private BigDecimal calculationOfWorkingHours;
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
