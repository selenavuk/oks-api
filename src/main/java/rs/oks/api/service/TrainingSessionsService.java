package rs.oks.api.service;

//import rs.oks.api.model.TrainingSessions;
//import rs.oks.api.model.User;
//import rs.oks.api.repository.TrainingSessionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.oks.api.model.TrainingSessions;
import rs.oks.api.model.User;
import rs.oks.api.repository.TrainingSessionsRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TrainingSessionsService {

    @Autowired
    private TrainingSessionsRepository trainingSessionsRepository;


    public void addTrainingSessions(TrainingSessions trainingSessions) {
        trainingSessionsRepository.save(trainingSessions);
    }

    public void updateTrainingSessions(TrainingSessions trainingSessions) {
        trainingSessionsRepository.updateTrainingSessions(
                trainingSessions.getFirstName(),
                trainingSessions.getLastName(),
                trainingSessions.getEmail(),
                trainingSessions.getMonthYear(),
                trainingSessions.getTrainingSessions()
        );
    }

    public List<TrainingSessions> getAllTrainingSessions() {
        return trainingSessionsRepository.findAll();
    }

    public TrainingSessions getTrainingSessionsForSingleMonthForUserByFullNameAndEmail(String firstName, String lastName, String email, String month_year) {
        try {
            Optional<TrainingSessions> trainingSessionsOptional = trainingSessionsRepository.findByMonthFullNameAndEmail(firstName, lastName, email, month_year);
            return trainingSessionsOptional.orElse(null);
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public List<TrainingSessions> getTrainingSessionsForUserByFullNameAndEmail(String firstName, String lastName, String email) {
        try {
            return trainingSessionsRepository.findByFullNameAndEmail(firstName, lastName, email);
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

}

