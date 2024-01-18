package be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.projection;

import jakarta.persistence.OrderBy;

import java.math.BigDecimal;

public interface WorkHourSummaryWithUserName {
    @OrderBy("username")
    String getUserName();
    BigDecimal getCalculationOfWorkingHours();
    BigDecimal getTotalSalaryCost();


}
