package be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class UpdateHourWorkHourDTO {
    private Long hourId;
    private LocalTime newStartTime;
    private LocalTime newEndTime;
}
