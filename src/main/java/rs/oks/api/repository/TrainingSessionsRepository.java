package rs.oks.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.oks.api.model.TrainingSessions;

import java.util.Optional;
import java.util.List;

@Repository
public interface TrainingSessionsRepository extends JpaRepository<TrainingSessions,Integer> {

    @Transactional
    @Modifying
    @Query(
            "UPDATE TrainingSessions ts " +
            "SET ts.trainingSessions = :trainingSessions " +
            "WHERE ts.firstName = :firstName AND ts.lastName = :lastName AND ts.email = :email AND ts.monthYear = :monthYear"
    )
    void updateTrainingSessions(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("email") String email,
            @Param("monthYear") String monthYear,
            @Param("trainingSessions") String trainingSessions
    );

    @Query(
            "SELECT ts " +
            "FROM TrainingSessions ts " +
            "WHERE ts.firstName = :firstName AND ts.lastName = :lastName AND ts.email = :email AND ts.monthYear = :monthYear"
    )
    Optional<TrainingSessions> findByMonthFullNameAndEmail(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("email") String email,
            @Param("monthYear") String monthYear
    );

    @Query(
            "SELECT ts " +
            "FROM TrainingSessions ts " +
            "WHERE ts.firstName = :firstName AND ts.lastName = :lastName AND ts.email = :email"
    )
    List<TrainingSessions> findByFullNameAndEmail(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("email") String email
    );
}
