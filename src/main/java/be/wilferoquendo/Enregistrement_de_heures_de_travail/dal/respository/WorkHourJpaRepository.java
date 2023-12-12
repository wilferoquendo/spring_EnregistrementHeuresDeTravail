package be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.respository;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.WorkHourEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkHourJpaRepository extends JpaRepository<WorkHourEntity, Long> {

    @Query(value = "SELECT EXTRACT(HOUR FROM (w.end_time - w.start_time)) " +
            "FROM WorkHourEntity w " +
            "WHERE w.id = :id", nativeQuery = true)
    Integer calculateHoursDifference(Long id);
}
