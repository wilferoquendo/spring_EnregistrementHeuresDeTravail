package be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.projection;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface WorkHoursBetweenDateAndByUserName {

    String getUserName();
    LocalDate getDate();

    LocalTime getStartTime();

    LocalTime getEndTime();

    BigDecimal getCalculationOfWorkingHours();

    BigDecimal getTotalSalaryCost();

    String getProjectName();

    LocalDateTime getCreationDate();
}
