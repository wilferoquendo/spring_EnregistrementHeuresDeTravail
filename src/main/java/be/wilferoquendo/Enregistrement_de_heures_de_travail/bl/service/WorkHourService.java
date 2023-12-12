package be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.service;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto.WorkHourDTO;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.form.WorkHourForm;

import java.util.List;

public interface WorkHourService {

    List<WorkHourDTO> findAllWorkHours();

    void saveWorkHour(WorkHourForm workHourForm);

}
