package tr.edu.akdeniz.reportpool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.edu.akdeniz.reportpool.service.impl.UserService;

@RestController
public class LoginController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/api/login/{username}/{psw}",method = RequestMethod.POST)
    @CrossOrigin
    public ResponseEntity login(@PathVariable("username") String username,@PathVariable("psw") String psw) {

        return userService.login(username,psw);
    }
    @RequestMapping(value = "/api/register/{email}/{username}/{psw}/{name}/{surname}",method = RequestMethod.POST)
    @CrossOrigin
    public ResponseEntity register(
            @PathVariable("email") String email,
            @PathVariable("username") String username,
            @PathVariable("psw") String psw,
            @PathVariable("name") String name,
            @PathVariable("surname") String surname) {

        return userService.register(email,username,psw,name,surname);
    }
}

