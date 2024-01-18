package be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.projection;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.UserEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.WorkHourEntity;
import lombok.ToString;
import lombok.Value;
import org.apache.catalina.User;

import java.math.BigDecimal;

public interface WorkHourSummary {
    Long getUserEntity();
//    BigDecimal getTotalSalaryCost();
    BigDecimal getCalculationOfWorkingHours();
    BigDecimal getTotalSalaryCost();



//    String getOuvrier();
//    BigDecimal getcalculationOfWorkingHours();

//    Long getId();

//    BigDecimal getHourlySalaryCost();
//    BigDecimal getTotalSalaryCost();
//     Long userId();
//    String getName();
//    LocalDate getDate();
//    BigDecimal getTotalSalaryCost();
//        String getUserName();  // Cambiado de Long a String
//        BigDecimal getTotalWorkHours();
//        BigDecimal getTotalCostOfWork();
}
