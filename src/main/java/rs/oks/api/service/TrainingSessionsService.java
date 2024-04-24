package rs.oks.api.service;

import rs.oks.api.model.TrainingSessions;
import rs.oks.api.model.User;
import rs.oks.api.repository.TrainingSessionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TrainingSessionsService {

    @Autowired
    private TrainingSessionsRepository trainingSessionsRepository;

//    public void updateTrainingSessionForUser(User user, int orderIndex, boolean newTrainingSessionValue) {
//        Optional<TrainingSessions> trainingSessionOptional = trainingSessionsRepository.findByUserAndOrderIndex(user, orderIndex);
//
//        if (trainingSessionOptional.isPresent()) {
//            TrainingSessions trainingSession = trainingSessionOptional.get();
//            trainingSession.setTrainingSession(newTrainingSessionValue);
//            trainingSession.setUpdated_at(new Timestamp(System.currentTimeMillis()));
//
//            trainingSessionsRepository.save(trainingSession);
//        } else {
//            // Handle the case where the training session doesn't exist for the specified user and order index
//            // You can choose to throw an exception or handle it according to your application logic
//        }
//    }
}

