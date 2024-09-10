package rs.oks.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.oks.api.model.TrainingSessions;
import rs.oks.api.model.User;
import rs.oks.api.service.TrainingSessionsService;
import rs.oks.api.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/trainingsessions")
public class TrainingSessionsController {

    @Autowired
    private TrainingSessionsService trainingSessionsService;

    @PostMapping("/user")
    public ResponseEntity<String> getTrainingSessionsForUserByFullNameAndEmail(
            @RequestBody UserRequest userRequest) {
        try {
            List<TrainingSessions> trainingSessions = trainingSessionsService.getTrainingSessionsForUserByFullNameAndEmail(
                    userRequest.getFirstName(), userRequest.getLastName(), userRequest.getEmail());

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(trainingSessions);

            return new ResponseEntity<>(json, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

@Getter
class UserRequest {
    private String firstName;
    private String lastName;
    private String email;

}