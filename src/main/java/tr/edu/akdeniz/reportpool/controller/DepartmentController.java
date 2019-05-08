package tr.edu.akdeniz.reportpool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.edu.akdeniz.reportpool.model.DepartmentDto;
import tr.edu.akdeniz.reportpool.service.impl.DepartmentService;
import tr.edu.akdeniz.reportpool.service.impl.UserService;

import java.util.List;

@RestController
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;
    @Autowired
    UserService userService;

    @RequestMapping("/api/getDepartments")
    @CrossOrigin
    public List<DepartmentDto> getDepartments(Authentication authentication) {

        // checks if user is admin from token
        if (!userService.isUserAdmin(authentication.getName())) {
            throw new BadCredentialsException("");
        }


        return departmentService.getAllDepartments();
    }


}
