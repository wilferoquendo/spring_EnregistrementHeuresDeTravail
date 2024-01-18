package be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.UserEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.WorkHourEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class WorkHourDTO {

    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String projectName;
    private BigDecimal calculationOfWorkingHours;
//    private BigDecimal hourlySalaryCost;
    private Long userId;

    public static WorkHourDTO fromEntity(WorkHourEntity workHourEntity){
        WorkHourDTO workHourDTO = new WorkHourDTO();
        workHourDTO.setId(workHourEntity.getId());
        workHourDTO.setDate(workHourEntity.getDate());
        workHourDTO.setStartTime(workHourEntity.getStartTime());
        workHourDTO.setEndTime(workHourEntity.getEndTime());
        workHourDTO.setProjectName(workHourEntity.getProjectName());
        workHourDTO.setCalculationOfWorkingHours(workHourEntity.getCalculationOfWorkingHours());
//        workHourDTO.setHourlySalaryCost(workHourEntity.getHourlySalaryCost());

        if (workHourEntity.getUserEntity() != null) {
            workHourDTO.setUserId(workHourEntity.getUserEntity().getUserId());
        }

        return workHourDTO;
    }
}
