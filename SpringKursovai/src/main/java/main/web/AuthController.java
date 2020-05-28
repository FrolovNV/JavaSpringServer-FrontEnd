package main.web;

import main.entity.User;
import main.repository.UserRepository;
import main.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Console;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/bat/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/singin")
    public ResponseEntity singIn(@RequestBody AuthRequest request) {
        try {
            String name = request.getUserName();
            System.out.println("Name = " + name);
            User user = userRepository.findUserByUserName(request.getUserName())
                    .orElseThrow(()->new UsernameNotFoundException("Not found"));

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new UsernameNotFoundException("Incorrect password");
            }
            String token = jwtTokenProvider.createTocken(
                    name,
                    user.getRoles()
            );
            System.out.println("Token = " + token);
            Map<Object, Object> model = new HashMap<>();
            model.put("userName", name);
            model.put("token", token);
            model.put("role", user.getRoles());

            return ResponseEntity.ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid userName");
        }
    }
}
