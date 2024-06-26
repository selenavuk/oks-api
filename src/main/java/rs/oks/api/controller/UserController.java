package rs.oks.api.controller;

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
    public List<User> getUser(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Integer id){
        return userService.getUserById(id);
    }

//    @PostMapping("/")
//    public User getUser(@RequestBody User user){
//        return userService.addUser(user);
//    }
}
