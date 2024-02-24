package rs.oks.api.service;

import rs.oks.api.model.User;
import rs.oks.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUser(Integer id){
        try{
            return userRepository.findById(id).get();
        }catch (Exception e){
            return new User();
        }

    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public User addUser(User user){
        return userRepository.save(user);
    }
}
