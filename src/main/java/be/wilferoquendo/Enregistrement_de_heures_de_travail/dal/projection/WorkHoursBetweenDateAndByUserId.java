package be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.projection;


import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.UserEntity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface WorkHoursBetweenDateAndByUserId {

    Long getUserEntity();
    LocalDate getDate();

    LocalTime getStartTime();

    LocalTime getEndTime();

    BigDecimal getCalculationOfWorkingHours();

    BigDecimal getTotalSalaryCost();

    String getProjectName();

    LocalDateTime getCreationDate();
}
