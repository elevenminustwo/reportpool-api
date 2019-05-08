package tr.edu.akdeniz.reportpool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tr.edu.akdeniz.reportpool.security.CustomAuthenticationProvider;
import tr.edu.akdeniz.reportpool.security.JwtTokenProvider;
import tr.edu.akdeniz.reportpool.service.impl.UserService;

@RestController
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    CustomAuthenticationProvider authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/api/login/{username}/{psw}",method = RequestMethod.POST)
    @CrossOrigin
    public ResponseEntity login(@PathVariable("username") String username,@PathVariable("psw") String psw) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, psw));
        String token = jwtTokenProvider.createToken(username);

        return userService.login(username,psw, token);
    }
    @RequestMapping(value = "/api/register/{email}/{username}/{psw}/{name}/{surname}",method = RequestMethod.POST)
    @CrossOrigin
    public ResponseEntity register(
            @PathVariable("email") String email,
            @PathVariable("username") String username,
            @PathVariable("psw") String psw,
            @PathVariable("name") String name,
            @PathVariable("surname") String surname) {

        psw = passwordEncoder.encode(psw);

        return userService.register(email,username,psw,name,surname);
    }
}

