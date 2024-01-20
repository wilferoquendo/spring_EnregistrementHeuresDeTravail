package be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.respository;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.UserEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.WorkHourEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.projection.WorkHourSummary;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.projection.WorkHourSummaryWithUserName;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.projection.WorkHoursBetweenDateAndByUserId;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkHourJpaRepository extends JpaRepository<WorkHourEntity, Long> {
    @Query(
            """
                    SELECT wh.userEntity.userId as userEntity, SUM(wh.calculationOfWorkingHours) as calculationOfWorkingHours, SUM(wh.totalSalaryCost) as totalSalaryCost 
                    FROM WorkHourEntity wh
                    WHERE date BETWEEN :startDate AND :endDate
                    GROUP BY userEntity
                    ORDER BY totalSalaryCost DESC
                    """
    )
    List<WorkHourSummary> findBetweenDateTotalSalary(@Param("startDate") LocalDate startDate,
                                                     @Param("endDate") LocalDate endDate);

    @Query(
            value = """
                    SELECT user_name AS userName,
                           SUM(w.heures_de_travail) AS calculationOfWorkingHours,
                           SUM(w.cout_total) AS totalSalaryCost
                    FROM heures_de_travail w
                    JOIN users u ON w.user_id = u.user_id
                    WHERE w.date BETWEEN :startDate AND :endDate
                    GROUP BY user_name
                    ORDER BY totalSalaryCost DESC;
                    """,
            nativeQuery = true
    )
    List<WorkHourSummaryWithUserName> findBetweenDateTotalSalaryWithUserName(@Param("startDate") LocalDate startDate,
                                                                             @Param("endDate") LocalDate endDate);

    @Query (
            value = """
            SELECT user_id AS userEntity, date AS date, heure_de_debut AS startTime, heure_du_final AS endTime, heures_de_travail AS calculationOfWorkingHours, cout_total AS totalSalaryCost, nom_de_projet AS projectName, date_de_creation AS creationDate
                    FROM heures_de_travail wh
                    WHERE date BETWEEN :startDate AND :endDate
                    and wh.user_id = :userId
                    order BY date  asc;
                    """,
            nativeQuery = true
    )
    List<WorkHoursBetweenDateAndByUserId> findWorkHoursBetweenDateAndByUserId(@Param("startDate") LocalDate startDate,
                                                                              @Param("endDate") LocalDate endDate,
                                                                              @Param("userId") Long userId);



}
