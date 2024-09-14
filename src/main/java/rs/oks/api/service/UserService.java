package rs.oks.api.service;

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

    public User getUserByFullNameAndEmail(String firstName, String lastName, String email) {
        try {
            Optional<User> userOptional = userRepository.findByFullNameAndEmail(firstName, lastName, email);
            return userOptional.orElse(null);
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public List<User> getUserByEmail(String email) {
        try {
            return userRepository.findByEmail(email);
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
                user.getDateDoctorReview(),
                user.getDateOfBirth(),
                user.getPaymentMethod(),
                user.getMembershipFee(),
                user.getTrainingSessions(),
                user.getTotalTrainingSessions().toString(),
                user.getPhoneNumber(),
                user.getInViberGroup(),
                user.getAccessCard(),
                user.getHeight(),
                user.getNote(),
                user.getAgeGroup(),
                user.getEmail(),
                user.getPassword()
        );
    }

    public void updateUserLastLoginTime(User user) {
        userRepository.updateUserLastLoginTime(
                user.getLast_login(),
                user.getEmail()
        );
    }

    public void updateConfirmation(User user) {
        userRepository.updateConfirmation(
                user.getConfirmed(),
                user.getEmail()
        );
    }

    public void addDefaultAdminUser() {

        String adminEmail = "okrodahotvolley@gmail.com";
        String adminPass = "Okrodahotvolley123";

        // Check if the default user already exists
        Optional<User> existingUser = userRepository.findByEmail(adminEmail).stream().findAny();

        if (existingUser.isPresent()) {
            return;
        }

        User defaultUser = new User();
        defaultUser.setAdministrator(true);
        defaultUser.setFirstName("");
        defaultUser.setLastName("");
        defaultUser.setDate("");
        defaultUser.setDateOfBirth("");
        defaultUser.setPaymentMethod("");
        defaultUser.setPhoneNumber("");
        defaultUser.setInViberGroup("");
        defaultUser.setAccessCard("");
        defaultUser.setHeight("");
        defaultUser.setNote("");
        defaultUser.setAgeGroup("");
        defaultUser.setEmail(adminEmail);
        defaultUser.setPassword(adminPass);
        defaultUser.setConfirmed(true);

        userRepository.save(defaultUser);
    }

    public void addTestUser() {

        String adminEmail = "selenavu93@gmail.com";
        String adminPass = "DeepGalaxy1907!";

        // Check if the default user already exists
        Optional<User> existingUser = userRepository.findByEmail(adminEmail).stream().findAny();

        if (existingUser.isPresent()) {
            return;
        }

        User defaultUser = new User();
        defaultUser.setAdministrator(false);
        defaultUser.setFirstName("");
        defaultUser.setLastName("");
        defaultUser.setDate("");
        defaultUser.setDateOfBirth("");
        defaultUser.setPaymentMethod("");
        defaultUser.setPhoneNumber("");
        defaultUser.setInViberGroup("");
        defaultUser.setAccessCard("");
        defaultUser.setHeight("");
        defaultUser.setNote("");
        defaultUser.setAgeGroup("");
        defaultUser.setEmail(adminEmail);
        defaultUser.setPassword(adminPass);
        defaultUser.setConfirmed(true);

        userRepository.save(defaultUser);
    }
}
