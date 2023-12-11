package be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.impl;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.service.WorkHourService;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.WorkHourEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.respository.WorkHourJpaRepository;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto.WorkHourDTO;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.form.WorkHourForm;
import org.springframework.stereotype.Service;
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
        this.workHourJpaRepository.save(workHourEntity);
    }
}
