package tr.edu.akdeniz.reportpool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tr.edu.akdeniz.reportpool.model.UserDto;
import tr.edu.akdeniz.reportpool.service.impl.UserService;

import java.util.List;



@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/")
    public List<UserDto> getUser() {
        return userService.getUser();
    }

    @RequestMapping(path = "/api/tdd/getUser", method= RequestMethod.GET)
    public List<UserDto> getUserTest(){
        return userService.getUser();
    }






}
