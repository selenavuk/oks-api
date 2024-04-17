package rs.oks.api.controller;

import com.google.api.client.json.Json;
import com.google.gson.Gson;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.oks.api.controller.util.JwtUtil;
import rs.oks.api.controller.util.LoginRequest;
import rs.oks.api.model.User;
import rs.oks.api.service.UserService;
import java.util.List;
import java.util.Optional;

@RestController
//@RequestMapping("/login")
public class LoginController {

//    @Autowired
//    private AuthenticationManager authenticationManager;
//
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private Gson gson;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

//        SecurityContextHolder.getContext().setAuthentication(authentication);

//        UserDetails userDetails = convertUserToUserDetails(userService.getUserByUsername(loginRequest.getUsername()));
//        String token = jwtUtil.generateToken(userDetails.getUsername());

        // TODO: find out some better way to handle this logic
        List<User> users = userService.getUserByUsername(loginRequest.getUsername());
        User user = users.stream().findFirst().orElse(null);

        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {

            // TODO: token will be usefull when security is added
//            String token = jwtUtil.generateToken(user.getUsername());

            return ResponseEntity.ok(gson.toJson(users));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

//    private static UserDetails convertUserToUserDetails(User user) {
//        return org.springframework.security.core.userdetails.User.builder()
//                .username(user.getUsername())
//                .password(user.getPassword())
//                .authorities("ROLE_USER")
//                .build();
//    }
}
