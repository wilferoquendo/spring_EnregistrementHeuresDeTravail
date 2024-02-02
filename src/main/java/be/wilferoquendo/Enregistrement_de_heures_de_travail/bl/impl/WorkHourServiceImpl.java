package be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.impl;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.exception.RequestNotFoundException;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.service.DeliveryNoteService;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.service.UserService;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.service.WorkHourService;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.DeliveryNoteEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.UserEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.WorkHourEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.projection.WorkHourSummaryWithUserName;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.projection.WorkHoursBetweenDateAndByUserId;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.projection.WorkHoursBetweenDateAndByUserName;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.respository.WorkHourJpaRepository;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.projection.WorkHourSummary;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto.UpdateHourWorkHourDTO;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto.WorkHourDTO;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.form.WorkHourForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final DeliveryNoteService deliveryNoteService;

    public WorkHourServiceImpl(WorkHourJpaRepository workHourJpaRepository, UserService userService, DeliveryNoteService deliveryNoteService) {
        this.workHourJpaRepository = workHourJpaRepository;
        this.userService = userService;
        this.deliveryNoteService = deliveryNoteService;
    }


    @Override
    public boolean existsByHourId(Long id) {
        return workHourJpaRepository.existsById(id);
    }

    public List<WorkHourEntity> findByUserEntityAndDate(UserEntity userId, LocalDate date) {
        return workHourJpaRepository.findByUserEntityAndDate(userId, date);
    }

    @Override
    public WorkHourEntity findById(Long id) {
        return workHourJpaRepository.findById(id).orElse(null);
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
    @Transactional
    public void saveWorkHour(WorkHourForm workHourForm) {

        try {
            UserEntity userEntity = userService.getUserEntityById(workHourForm.getUserId());
            DeliveryNoteEntity deliveryNoteEntity =
                    deliveryNoteService.getDeliveryNoteEntityById(workHourForm.getDeliveryNoteId());
            LocalDate date = workHourForm.getDate();
            LocalTime startTime = workHourForm.getStartTime();
            LocalTime endTime = workHourForm.getEndTime();

            List<WorkHourEntity> existingWorkHours = findByUserEntityAndDate(userEntity, date);

            for (WorkHourEntity existingWorkHour : existingWorkHours) {
                if (hasTimeOverlap(existingWorkHour, workHourForm)) {
                    throw new RequestNotFoundException("A record already exists for this user with the same time or within the same range.");
                }
            }

            WorkHourEntity workHourEntity = workHourForm.toEntity(userEntity,
                    deliveryNoteEntity);
            BigDecimal calculatedHours = calculateWorkingHours(startTime, endTime, date);
            BigDecimal totalSalaryCost = calculateAndUpdateTotalSalaryCost(calculatedHours, userEntity.getHourlySalaryCost());

            workHourEntity.setCalculationOfWorkingHours(calculatedHours);
            workHourEntity.setHourlySalaryCost(userEntity.getHourlySalaryCost());
            workHourEntity.setTotalSalaryCost(totalSalaryCost);

            this.workHourJpaRepository.save(workHourEntity);

        } catch (Exception e) {
            throw new RequestNotFoundException("@Service: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void updateHourWorkHour(UpdateHourWorkHourDTO newWorkHour) {
        try {
            WorkHourEntity workHourEntity = this.findById(newWorkHour.getHourId());
            if (workHourEntity != null) {
                BigDecimal calculatedHours = calculateWorkingHours(newWorkHour.getNewStartTime(),
                        newWorkHour.getNewEndTime(),
                        workHourEntity.getDate());
                workHourEntity.setCalculationOfWorkingHours(calculatedHours);
                BigDecimal totalSalaryCost = calculateAndUpdateTotalSalaryCost(calculatedHours,
                        workHourEntity.getHourlySalaryCost());
                workHourEntity.setTotalSalaryCost(totalSalaryCost);
                this.workHourJpaRepository.updateHourWorkHour(newWorkHour);
            }
        } catch (Exception e) {
            throw new RequestNotFoundException(" @Service " + e);
        }
    }

    @Override
    public void deleteHourWorkHour(Long hourId) {
        workHourJpaRepository.deleteById(hourId);
    }


    @Override
    public List<WorkHourSummary> findBetweenDateTotalSalary(LocalDate startDate,
                                                            LocalDate endDate) {
        try {
            return workHourJpaRepository.findBetweenDateTotalSalary(startDate, endDate);
        } catch (Exception e) {
            throw new RequestNotFoundException("Request not found from @Service");
        }
    }

    @Override
    public List<WorkHourSummaryWithUserName> findBetweenDateTotalSalaryWithUserName(LocalDate startDate, LocalDate endDate) {
        try {
            return workHourJpaRepository.findBetweenDateTotalSalaryWithUserName(startDate, endDate);
        } catch (Exception e) {
            throw new RequestNotFoundException("Request not found from @Service");
        }
    }

    @Override
    public List<WorkHoursBetweenDateAndByUserId> findWorkHoursBetweenDateAndByUserId(LocalDate startDate, LocalDate endDate, Long userEntity) {
        try {
            return workHourJpaRepository.findWorkHoursBetweenDateAndByUserId(startDate, endDate,
                    userEntity);
        } catch (Exception e) {
            throw new RequestNotFoundException("Request not found from @Service");
        }
    }

    @Override
    public List<WorkHoursBetweenDateAndByUserName> findWorkHoursBetweenDateAndByUserName(LocalDate startDate, LocalDate endDate, String userName) {
        try {
            return workHourJpaRepository.findWorkHoursBetweenDateAndByUserName(startDate, endDate
                    , userName);
        } catch (Exception e) {
            throw new RequestNotFoundException("Request not found from @Service");
        }
    }

    private boolean hasTimeOverlap(WorkHourEntity workHourFound, WorkHourForm workHourToCreate) {

        LocalDateTime startHourFound = LocalDateTime.of(workHourFound.getDate(), workHourFound.getStartTime());
        LocalDateTime endHourFound = LocalDateTime.of(workHourFound.getDate(), workHourFound.getEndTime());

        LocalDateTime startHourToCreate = LocalDateTime.of(workHourToCreate.getDate(), workHourToCreate.getStartTime());
        LocalDateTime endHourToCreate = LocalDateTime.of(workHourToCreate.getDate(), workHourToCreate.getEndTime());

        return startHourFound.isBefore(endHourToCreate) && startHourToCreate.isBefore(endHourFound);
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
        if (hourlySalaryCost != null) {
            return calculatedHours.multiply(hourlySalaryCost);
        } else {
            return BigDecimal.ZERO;
        }
    }
}

