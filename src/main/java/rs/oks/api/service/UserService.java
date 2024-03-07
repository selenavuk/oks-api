package rs.oks.api.service;

import rs.oks.api.model.User;
import rs.oks.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUser(Integer id) {
        try {
            return userRepository.findById(id).get();
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        return null;
    }

//    public void updateUsersFromExcel(MultipartFile file) {
//        try {
//            List<User> users = ExcelHandler.readUsersFromExcelTable(file.getInputStream());
//            userRepository.saveAll(users);
//        } catch (IOException e) {
//            // TODO: handle error
//            throw new RuntimeException("fail to store excel data: " + e.getMessage());
//        }
//    }

    public User addUser(User user){
        return userRepository.save(user);
    }
}
