package be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.impl;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.service.WorkHourService;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.WorkHourEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.respository.WorkHourJpaRepository;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto.WorkHourDTO;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.form.WorkHourForm;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class WorkHourServiceImpl implements WorkHourService {

    private final WorkHourJpaRepository workHourJpaRepository;

    public WorkHourServiceImpl(WorkHourJpaRepository workHourJpaRepository) {
        this.workHourJpaRepository = workHourJpaRepository;
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
        WorkHourEntity workHourEntity = new WorkHourEntity();
        workHourEntity.setDate(workHourForm.getDate());
        workHourEntity.setStartTime(workHourForm.getStartTime());
        workHourEntity.setEndTime(workHourForm.getEndTime());
        workHourEntity.setProjectName(workHourForm.getProjectName());

        // Calcular la diferencia de horas
        LocalTime startTime = workHourForm.getStartTime();
        LocalTime endTime = workHourForm.getEndTime();

        Duration duration = Duration.between(startTime, endTime);
        long minutes = duration.toMinutes();
        double hours = (double) minutes / 60;

        BigDecimal calculatedHours = BigDecimal.valueOf(hours).setScale(2, RoundingMode.HALF_UP);
        workHourEntity.setCalculationOfWorkingHours(calculatedHours);

        this.workHourJpaRepository.save(workHourEntity);
    }

}
