package tr.edu.akdeniz.reportpool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tr.edu.akdeniz.reportpool.model.UserUnitDto;
import tr.edu.akdeniz.reportpool.service.impl.UserService;
import tr.edu.akdeniz.reportpool.service.impl.UserUnitsService;

import java.util.List;

@RestController
public class UnitUserController {

    @Autowired
    private UserUnitsService userUnitsService;
    @Autowired
    private UserService userService;

    @RequestMapping(value="api/getUserUnits/{userId}",method = RequestMethod.GET)
    @CrossOrigin
    public ResponseEntity<List<UserUnitDto>> getUserUnitsById(Authentication authentication, @PathVariable int userId) {

        // token owner can only access his own units
        if (!userService.getUsernameOfUserId(userId).equals(authentication.getName())) {
            throw new BadCredentialsException("");
        }


        return new ResponseEntity(userUnitsService.getUserUnits(userId), HttpStatus.OK);
    }

}
