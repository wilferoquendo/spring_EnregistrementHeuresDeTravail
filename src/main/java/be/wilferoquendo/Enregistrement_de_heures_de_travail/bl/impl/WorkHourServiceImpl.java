package be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.impl;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.exception.RequestNotFoundException;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.exception.UserNotFoundException;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.service.UserService;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.service.WorkHourService;
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
import java.util.Optional;

@Service
public class WorkHourServiceImpl implements WorkHourService {

    private final WorkHourJpaRepository workHourJpaRepository;
    private final UserService userService;

    public WorkHourServiceImpl(WorkHourJpaRepository workHourJpaRepository, UserService userService) {
        this.workHourJpaRepository = workHourJpaRepository;
        this.userService = userService;
    }


    @Override
    public boolean existsByHourId(Long id) {
        return workHourJpaRepository.existsById(id);
    }

    @Override
    public boolean existsByUserEntityAndStartTimeAndDate(UserEntity userId, LocalTime startTime,
                                                         LocalDate date) {
        return workHourJpaRepository.existsByUserEntityAndStartTimeAndDate(userId, startTime, date);
    }

    @Override
    public boolean existsByUserEntityAndEndTimeAndDate(UserEntity userId, LocalTime endTime, LocalDate date) {
        return workHourJpaRepository.existsByUserEntityAndEndTimeAndDate(userId, endTime, date);
    }

    @Override
    public boolean existsByUserEntityAndDateAndStartTimeBefore(UserEntity userId,
                                                               LocalDate date,
                                                               LocalTime startTime
    ) {
        return workHourJpaRepository.existsByUserEntityAndDateAndStartTimeBefore(userId, date,
                startTime);
    }

    @Override
    public boolean existsByUserEntityAndDateAndEndTimeAfter(UserEntity userId, LocalDate date, LocalTime endTime) {
        return workHourJpaRepository.existsByUserEntityAndDateAndEndTimeAfter(userId, date, endTime);
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
                LocalDate date = workHourForm.getDate();
                LocalTime startTime = workHourForm.getStartTime();
                LocalTime endTime = workHourForm.getEndTime();

                List<WorkHourEntity> existingWorkHours = findByUserEntityAndDate(userEntity, date);

                for (WorkHourEntity existingWorkHour : existingWorkHours) {
                    if (hasTimeOverlap(existingWorkHour, workHourForm)) {
                        throw new RequestNotFoundException("A record already exists for this user with the same time or within the same range.");
                    }
                }

                WorkHourEntity workHourEntity = workHourForm.toEntity(userEntity);
                BigDecimal calculatedHours = calculateWorkingHours(startTime, endTime, date);
                BigDecimal totalSalaryCost = calculateAndUpdateTotalSalaryCost(calculatedHours, userEntity.getHourlySalaryCost());

                workHourEntity.setCalculationOfWorkingHours(calculatedHours);
                workHourEntity.setHourlySalaryCost(userEntity.getHourlySalaryCost());
                workHourEntity.setTotalSalaryCost(totalSalaryCost);

                this.workHourJpaRepository.save(workHourEntity);

            } catch (Exception e) {
                throw new RequestNotFoundException("@Service " + e);
            }










//        try {
//            UserEntity userIdLikeEntity = userService.getUserEntityById(workHourForm.getUserId());
//            List<WorkHourEntity> existingWorkHours = findByUserEntityAndDate(userIdLikeEntity
//                    , workHourForm.getDate());
//
//            System.out.println("***************\nThere is existingWorkHours\n***************\n" + existingWorkHours);
//
//            if (!existingWorkHours.isEmpty()) {
//                for (WorkHourEntity existingWorkHour : existingWorkHours) {
//                    if (hasTimeOverlap(existingWorkHour, workHourForm)) {
//                        System.out.println("***************\nThere is superposition\n***************");
//                        throw new RequestNotFoundException("A record already exists for this user with the same time or within the same range.");
//                    } else {
//                        System.out.println("***************\nThere is NOT superposition\n***************");
//
//                        UserEntity userEntity = userService.findById(workHourForm.getUserId());
//                        if (userEntity != null) {
//                            WorkHourEntity workHourEntity = workHourForm.toEntity(userEntity);
//
//                            BigDecimal calculatedHours = calculateWorkingHours(workHourForm.getStartTime(), workHourForm.getEndTime(), workHourForm.getDate());
//                            workHourEntity.setCalculationOfWorkingHours(calculatedHours);
//
//                            BigDecimal totalSalaryCost = calculateAndUpdateTotalSalaryCost(calculatedHours,
//                                    userEntity.getHourlySalaryCost());
//
//                            workHourEntity.setHourlySalaryCost(userEntity.getHourlySalaryCost());
//                            workHourEntity.setTotalSalaryCost(totalSalaryCost);
//
//                            this.workHourJpaRepository.save(workHourEntity);
//                        } else {
//                            throw new UserNotFoundException("User not found for ID: " + workHourForm.getUserId());
//                        }
//                    }
//                }
//            } else {
//                System.out.println("/////////\nThis is vide\n////////////");
//                UserEntity userEntity = userService.findById(workHourForm.getUserId());
//                if (userEntity != null) {
//                    WorkHourEntity workHourEntity = workHourForm.toEntity(userEntity);
//
//                    BigDecimal calculatedHours = calculateWorkingHours(workHourForm.getStartTime(), workHourForm.getEndTime(), workHourForm.getDate());
//                    workHourEntity.setCalculationOfWorkingHours(calculatedHours);
//
//                    BigDecimal totalSalaryCost = calculateAndUpdateTotalSalaryCost(calculatedHours,
//                            userEntity.getHourlySalaryCost());
//
//                    workHourEntity.setHourlySalaryCost(userEntity.getHourlySalaryCost());
//                    workHourEntity.setTotalSalaryCost(totalSalaryCost);
//
//                    this.workHourJpaRepository.save(workHourEntity);
//                }
//            }
//        } catch (Exception e) {
//            throw new RequestNotFoundException(" @Service " + e);
//        }


//
//        if (existsByUserEntityAndDateAndStartTimeBeforeOrEndTimeAfter(userIdLikeEntity,
//                workHourForm.getDate(), workHourForm.getStartTime(), workHourForm.getEndTime())) {
//            System.out.println("*******\nThere is a work hour registered between: \n" + workHourForm.getStartTime() + "\n" + workHourForm.getEndTime() + "**************");
//        } else {
//            System.out.println("---------there is not any hour between");
//        }


//        System.out.println("************\nresult for " +
//                "endtime and startTime\n " + existsByUserEntityAndDateAndStartTimeBefore
//                (userIdLikeEntity,
//                        workHourForm.getDate(), workHourForm.getStartTime()) + "\n" +
//                "************");
//
//        System.out.println("/////////////////\nresult for " +
//                "endtime and endTime\n " + existsByUserEntityAndDateAndEndTimeAfter
//                (userIdLikeEntity,
//                        workHourForm.getDate(), workHourForm.getEndTime()) + "\n" +
//                "///////////////");
//
//        List<WorkHourEntity> workHourEntitiesSame = findByUserEntityAndDate
//                (userIdLikeEntity, workHourForm.getDate());
//
//        System.out.println("???????????this is the list with the same user and date \n" +
//
//                "\n???????????");

//code ok
//        if (existsByUserEntityAndEndTimeAndDate(userIdLikeEntity, workHourForm.getEndTime(),
//                workHourForm.getDate())
//                ||
//                existsByUserEntityAndStartTimeAndDate(userIdLikeEntity,
//                        workHourForm.getStartTime(),
//                        workHourForm.getDate())
//                ||
//                existsByUserEntityAndDateAndStartTimeBefore
//                        (userIdLikeEntity,
//                                workHourForm.getDate(), workHourForm.getStartTime())
//                ||
//                existsByUserEntityAndDateAndEndTimeAfter
//                        (userIdLikeEntity,
//                                workHourForm.getDate(), workHourForm.getEndTime())
//        ) {
//            throw new RequestNotFoundException("A record already exists for this user with the same time or within the same range.");
//        } else {
//            UserEntity userEntity = userService.findById(workHourForm.getUserId());
//
//            if (userEntity != null) {
//                WorkHourEntity workHourEntity = workHourForm.toEntity(userEntity);
//
//                BigDecimal calculatedHours = calculateWorkingHours(workHourForm.getStartTime(), workHourForm.getEndTime(), workHourForm.getDate());
//                workHourEntity.setCalculationOfWorkingHours(calculatedHours);
//
//                BigDecimal totalSalaryCost = calculateAndUpdateTotalSalaryCost(calculatedHours,
//                        userEntity.getHourlySalaryCost());
//
//                workHourEntity.setHourlySalaryCost(userEntity.getHourlySalaryCost());
//                workHourEntity.setTotalSalaryCost(totalSalaryCost);
//
//                this.workHourJpaRepository.save(workHourEntity);
//            } else {
//                throw new UserNotFoundException("User not found for ID: " + workHourForm.getUserId());
//            }
//        }
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

    private boolean hasTimeOverlap(WorkHourEntity workHour1, WorkHourForm workHour2) {
        // Verificar si hay superposición de horas
        LocalDateTime start1 = LocalDateTime.of(workHour1.getDate(), workHour1.getStartTime());
        LocalDateTime end1 = LocalDateTime.of(workHour1.getDate(), workHour1.getEndTime());

        LocalDateTime start2 = LocalDateTime.of(workHour2.getDate(), workHour2.getStartTime());
        LocalDateTime end2 = LocalDateTime.of(workHour2.getDate(), workHour2.getEndTime());

        return start1.isBefore(end2) && start2.isBefore(end1);
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


//    private boolean existsByUserIdAndStartTimeAndDate(
//            workHourForm.getUserId(),
//                    workHourForm.getStartTime(),
//                            workHourForm.getDate()) {
//        if (workHourJpaRepository.existsByUserIdAndStartTimeAndDate) {
//
//        }
//    }
//
//                    || workHourJpaRepository.existsByUserIdAndEndTimeAndDate(
//                    workHourForm.getUserId(),
//                    workHourForm.getEndTime(),
//                    workHourForm.getDate())) {
//        throw new DuplicateWorkHourException("Ya existe un registro con la misma hora para el usuario en la misma fecha.");
//    }
//
//    private boolean existsByUserIdAndStartTimeAndDate
}

