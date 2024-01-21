package be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.service;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.UserEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.WorkHourEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.projection.WorkHourSummary;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.projection.WorkHourSummaryWithUserName;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.projection.WorkHoursBetweenDateAndByUserId;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.projection.WorkHoursBetweenDateAndByUserName;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto.UpdateHourWorkHourDTO;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto.WorkHourDTO;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.form.WorkHourForm;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface WorkHourService {

    boolean existsByHourId(Long id);
    WorkHourEntity findById(Long id);
    List<WorkHourDTO> findAllWorkHours();
    void saveWorkHour (WorkHourForm workHourForm);

    void updateHourWorkHour (UpdateHourWorkHourDTO newWorkHour);

    void deleteHourWorkHour (Long hourId);

    List<WorkHourSummary> findBetweenDateTotalSalary(LocalDate startDate,
                                                     LocalDate endDate);

    List<WorkHourSummaryWithUserName> findBetweenDateTotalSalaryWithUserName(@Param("startDate") LocalDate startDate,
                                                                             @Param("endDate") LocalDate endDate);

    List<WorkHoursBetweenDateAndByUserId> findWorkHoursBetweenDateAndByUserId(@Param("startDate") LocalDate startDate,
                                                                              @Param("endDate") LocalDate endDate,
                                                                              @Param("userId") Long userId);

    List<WorkHoursBetweenDateAndByUserName> findWorkHoursBetweenDateAndByUserName(@Param(
            "startDate") LocalDate startDate,
                                                                                  @Param("endDate") LocalDate endDate,
                                                                                  @Param("userName") String userName);
}


