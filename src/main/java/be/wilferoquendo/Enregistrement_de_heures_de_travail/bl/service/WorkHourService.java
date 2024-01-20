package be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.service;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.UserEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.projection.WorkHourSummary;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.projection.WorkHourSummaryWithUserName;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.projection.WorkHoursBetweenDateAndByUserId;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto.WorkHourDTO;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.form.WorkHourForm;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface WorkHourService {

    List<WorkHourDTO> findAllWorkHours();

    void saveWorkHour(WorkHourForm workHourForm);

    List<WorkHourSummary> findBetweenDateTotalSalary(LocalDate startDate,
                                                     LocalDate endDate);

    List<WorkHourSummaryWithUserName> findBetweenDateTotalSalaryWithUserName(@Param("startDate") LocalDate startDate,
                                                                             @Param("endDate") LocalDate endDate);

    List<WorkHoursBetweenDateAndByUserId> findWorkHoursBetweenDateAndByUserId(@Param("startDate") LocalDate startDate,
                                                                              @Param("endDate") LocalDate endDate,
                                                                              @Param("userId") Long userId);
}


