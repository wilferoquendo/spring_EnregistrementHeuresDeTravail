package be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.projection;

import java.math.BigDecimal;

public interface WorkHourSummary {
    Long getUserEntity();
    BigDecimal getCalculationOfWorkingHours();
    BigDecimal getTotalSalaryCost();
}
