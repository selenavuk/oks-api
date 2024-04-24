package rs.oks.api.service;

import org.springframework.data.repository.query.Param;
import rs.oks.api.model.User;
import rs.oks.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        try {
            Optional<User> userOptional = userRepository.findById(id);
            return userOptional.orElse(null);
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public User getUserByName(String firstName, String lastName) {
        try {
            Optional<User> userOptional = userRepository.findByName(firstName, lastName);
            return userOptional.orElse(null);
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public List<User> getUserByUsername(String username) {
        try {
            return userRepository.findByUsername(username);
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public void addUser(User user){
        userRepository.save(user);
    }

    public void updateUserWithExcelData(User user) {
        userRepository.updateUserWithExcelData(
            user.getFirstName(),
                user.getLastName(),
                user.getDate(),
                user.getPaymentMethod(),
                user.getMembershipFee(),
                user.getTotalTrainingSessions().toString(),
                user.getPhoneNumber(),
                user.getInViberGroup(),
                user.getAccessCard(),
                user.getHeight(),
                user.getNote(),
                user.getColorFlaggedInfo()
        );
    }

}
