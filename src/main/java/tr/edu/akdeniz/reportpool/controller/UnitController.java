package tr.edu.akdeniz.reportpool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tr.edu.akdeniz.reportpool.entity.Unit;
import tr.edu.akdeniz.reportpool.model.UnitDto;
import tr.edu.akdeniz.reportpool.service.impl.UnitService;
import tr.edu.akdeniz.reportpool.service.impl.UserService;

import java.util.List;

@RestController
public class UnitController {
    @Autowired
    UnitService unitService;
    @Autowired
    UserService userService;

    @RequestMapping(value = "/api/getUnits/{id}",method = RequestMethod.GET)
    @CrossOrigin
    public List<UnitDto> getUnits(Authentication authentication, @PathVariable int id) {

        // checks if user is admin from token
        if (!userService.isUserAdmin(authentication.getName())) {
            throw new BadCredentialsException("");
        }

        return unitService.getAllUnitsByDepartmentId(id);
    }

}
