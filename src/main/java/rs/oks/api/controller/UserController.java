package rs.oks.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import rs.oks.api.model.TrainingSessions;
import rs.oks.api.model.User;
import rs.oks.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<String> getUser(){

        try {
            List<User> users = userService.getAllUsers();

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(users);

            return new ResponseEntity<>(json, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Integer id){
        return userService.getUserById(id);
    }

    @PostMapping("/confirm")
    public ResponseEntity<String> confirmRulesRead(@RequestBody String email) {
        try {
            List<User> users = userService.getUserByEmail(email);

            for (User user : users) {
                user.setConfirmed(true);
                userService.updateConfirmation(user);
            }

            // TODO: handle sending emails to admin and user that performed confirmation

            return ResponseEntity.ok("Rules confirmation information updated successfully for users with email: " + email);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

//    @PostMapping("/")
//    public User getUser(@RequestBody User user){
//        return userService.addUser(user);
//    }
}
