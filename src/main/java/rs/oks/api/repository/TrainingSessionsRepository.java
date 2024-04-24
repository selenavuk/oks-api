package rs.oks.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.oks.api.model.TrainingSessions;

@Repository
public interface TrainingSessionsRepository extends JpaRepository<TrainingSessions,Integer> {
}
