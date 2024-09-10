package rs.oks.api.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
//import rs.oks.api.misc.JwtUtil;
import rs.oks.api.controller.util.LoginRequest;
import rs.oks.api.model.User;
import rs.oks.api.service.UserService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
//@RequestMapping("/login")
public class LoginController {

//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private JwtUtil jwtUtil;

    @Autowired
    private Gson gson;

    @Autowired
    private UserService userService;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

//        SecurityContextHolder.getContext().setAuthentication(authentication);

//        UserDetails userDetails = convertUserToUserDetails(userService.getUserByUsername(loginRequest.getUsername()));
//        String token = jwtUtil.generateToken(userDetails.getUsername());

        // TODO: find out some better way to handle this logic
        List<User> users = userService.getUserByEmail(loginRequest.getUsername());

        User firstUserUsedToCheckCredentials = users.stream().findFirst().orElse(null);
        if (firstUserUsedToCheckCredentials != null && firstUserUsedToCheckCredentials.getPassword().equals(loginRequest.getPassword())) {

            // TODO: token will be usefull when security is added
//            String token = jwtUtil.generateToken(firstUserUsedToCheckCredentials.getEmail());
            String token = "token";

            // Generate response
            boolean firstTimeLogin = firstUserUsedToCheckCredentials.getConfirmed() == null;
            LoginResponse loginResponse = new LoginResponse(users, firstTimeLogin, token);

            // Update users with last login time
            for (User user : users) {
                user.setLast_login(Timestamp.valueOf(LocalDateTime.now()));
                userService.updateUserLastLoginTime(user);
            }

            return ResponseEntity.ok(gson.toJson(loginResponse));
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

class LoginResponse {
    private List<User> users;
    private boolean firstTimeLogin;
    private String token;

    public LoginResponse(List<User> users, boolean firstTimeLogin, String token) {
        this.users = users;
        this.firstTimeLogin = firstTimeLogin;
        this.token = token;
    }

}