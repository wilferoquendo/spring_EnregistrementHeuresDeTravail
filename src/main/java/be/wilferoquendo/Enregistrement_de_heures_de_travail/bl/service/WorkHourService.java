package be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.service;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.WorkHourEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto.WorkHourDTO;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.form.WorkHourForm;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

public interface WorkHourService {

    List<WorkHourDTO> findAllWorkHours();

    void SaveWorkHour(WorkHourForm workHourForm);
}
