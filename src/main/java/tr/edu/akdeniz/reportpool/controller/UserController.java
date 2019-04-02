package tr.edu.akdeniz.reportpool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.edu.akdeniz.reportpool.model.UserDto;
import tr.edu.akdeniz.reportpool.model.UserUnitEditDto;
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

    @RequestMapping("/api/addDunit")
    @CrossOrigin
    public ResponseEntity addDunit(@RequestBody UserUnitEditDto userUnitEditDto) {

        return userService.addDunit(userUnitEditDto);
    }
    @RequestMapping("/api/delDunit")
    @CrossOrigin
    public ResponseEntity delDunit(@RequestBody UserUnitEditDto userUnitEditDto) {

        return userService.delDunit(userUnitEditDto);
    }





}
