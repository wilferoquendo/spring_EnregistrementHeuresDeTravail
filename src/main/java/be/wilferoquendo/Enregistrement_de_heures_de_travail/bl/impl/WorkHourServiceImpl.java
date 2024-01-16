package be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.impl;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.exception.UserNotFoundException;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.service.UserService;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.service.WorkHourService;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.UserEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.WorkHourEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.respository.WorkHourJpaRepository;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto.WorkHourDTO;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.form.WorkHourForm;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class WorkHourServiceImpl implements WorkHourService {

    private final WorkHourJpaRepository workHourJpaRepository;
    private final UserService userService;

    public WorkHourServiceImpl(WorkHourJpaRepository workHourJpaRepository, UserService userService) {
        this.workHourJpaRepository = workHourJpaRepository;
        this.userService = userService;
    }

    @Override
    public List<WorkHourDTO> findAllWorkHours() {
        List<WorkHourEntity> workHourEntities = workHourJpaRepository.findAll();
        List<WorkHourDTO> workHourDTOList = workHourEntities.stream()
                .map(WorkHourDTO::fromEntity)
                .toList();
        return workHourDTOList;
    }

    @Override
    public void saveWorkHour(WorkHourForm workHourForm) {
        UserEntity userEntity = userService.findById(workHourForm.getUserId());

        if (userEntity != null) {
            WorkHourEntity workHourEntity = workHourForm.toEntity(userEntity);

            BigDecimal calculatedHours = calculateWorkingHours(workHourForm.getStartTime(), workHourForm.getEndTime(), workHourForm.getDate());
            workHourEntity.setCalculationOfWorkingHours(calculatedHours);

            BigDecimal totalSalaryCost = calculateAndUpdateTotalSalaryCost(calculatedHours,
                    userEntity.getHourlySalaryCost());

            workHourEntity.setHourlySalaryCost(userEntity.getHourlySalaryCost());
            workHourEntity.setTotalSalaryCost(totalSalaryCost);

            this.workHourJpaRepository.save(workHourEntity);
        } else {
            throw new UserNotFoundException("User not found for ID: " + workHourForm.getUserId());
        }
    }

    private BigDecimal calculateWorkingHours(LocalTime startTime, LocalTime endTime, LocalDate date) {

        LocalDateTime startDateTime = LocalDateTime.of(date, startTime);
        LocalDateTime endDateTime;

        if (endTime.isBefore(startTime)) {
            endDateTime = LocalDateTime.of(date.plusDays(1), endTime);
        } else {
            endDateTime = LocalDateTime.of(date, endTime);
        }

        Duration duration = Duration.between(startDateTime, endDateTime);
        long minutes = duration.toMinutes();
        double hours = (double) minutes / 60;

        return BigDecimal.valueOf(hours).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateAndUpdateTotalSalaryCost(BigDecimal calculatedHours,
                                                         BigDecimal hourlySalaryCost) {
        return calculatedHours.multiply(hourlySalaryCost);
    }
}

