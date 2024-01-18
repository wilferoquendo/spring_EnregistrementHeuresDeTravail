package be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.respository;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.WorkHourEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.projection.WorkHourSummary;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.projection.WorkHourSummaryWithUserName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkHourJpaRepository extends JpaRepository<WorkHourEntity, Long> {

//    List<WorkHourSummary> findByDateBetween(LocalDate startDateFilter,
//                                            LocalDate endDateFilter);

    List<WorkHourSummary> findByIdGreaterThan(Long id);

//    @Query(
////            value = "SELECT user_name AS ouvrier,\n" +
////                    "       SUM(w.heures_de_travail) AS totalHeuresTravaillees,\n" +
////                    "       SUM(w.cout_total) AS totalAPayer\n" +
////                    "FROM heures_de_travail w\n" +
////                    "JOIN users u ON w.user_id = u.user_id\n" +
////                    "WHERE w.date BETWEEN :starDate AND :endDate\n" +
////                    "GROUP BY user_name\n" +
////                    "ORDER BY totalAPayer DESC",
//            value = "select user_id, sum(heures_de_travail) as \"total heures travaillêes\" , SUM" +
//                    "(cout_total) as \"total à payer\" from heures_de_travail \n" +
//                    "where  \"date\" between :startDate and :endDate" +
//                    "group by user_id \n" +
//                    "order  by \"total à payer\" desc",
//            nativeQuery = true
//    )
//    List<WorkHourSummary> totalSalaryByDate(@Param("startDate") LocalDate startDateFilter,
//                                            @Param("endDate") LocalDate endDateFilter);


//    @Query(
//            """
//                    SELECT wh.userEntity.userId as userEntity, SUM(wh.totalSalaryCost) as totalSalaryCost
//                    FROM WorkHourEntity wh
//                    WHERE wh.userEntity.id =
//                    GROUP BY userEntity
//                    """
//    )
//    WorkHourSummary findByTotalSalaryCostByUserId();

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


}
