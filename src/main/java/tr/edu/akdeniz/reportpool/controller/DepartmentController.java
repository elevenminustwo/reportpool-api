package tr.edu.akdeniz.reportpool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tr.edu.akdeniz.reportpool.entity.Departmentunit;
import tr.edu.akdeniz.reportpool.model.DepartmentDto;
import tr.edu.akdeniz.reportpool.model.DepartmentUnitAddDto;
import tr.edu.akdeniz.reportpool.model.DepartmentUnitDto;
import tr.edu.akdeniz.reportpool.model.UnitDto;
import tr.edu.akdeniz.reportpool.service.impl.DepartmentService;
import tr.edu.akdeniz.reportpool.service.impl.UserService;

import javax.servlet.http.HttpServletRequest;
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
    @RequestMapping(value ="/api/addDepartment",method = RequestMethod.POST)
    @CrossOrigin
    public ResponseEntity addDepartment(Authentication authentication,@RequestBody DepartmentDto departmentDto) {

        // checks if user is admin from token
        if (!userService.isUserAdmin(authentication.getName())) {
            throw new BadCredentialsException("");
        }
        return departmentService.addDepartment(departmentDto.getName());
    }
    @RequestMapping(value ="/api/delDepartment",method = RequestMethod.POST)
    @CrossOrigin
    public ResponseEntity delDepartment(Authentication authentication , @RequestBody DepartmentDto departmentDto) {

        // checks if user is admin from token
        if (!userService.isUserAdmin(authentication.getName())) {
            throw new BadCredentialsException("");
        }


        return departmentService.delDepartment(departmentDto.getDepartmentId());
    }
    @RequestMapping(value ="/api/getDepartmentUnits",method = RequestMethod.POST)
    @CrossOrigin
    public List<UnitDto> getDepartmentUnits(Authentication authentication , @RequestBody DepartmentDto departmentDto) {

        // checks if user is admin from token
        if (!userService.isUserAdmin(authentication.getName())) {
            throw new BadCredentialsException("");
        }


        return departmentService.getDepartmentUnits(departmentDto.getDepartmentId());
    }
    @RequestMapping(value ="/api/addUnit",method = RequestMethod.POST)
    @CrossOrigin
    public ResponseEntity addUnit(Authentication authentication , @RequestBody DepartmentUnitAddDto departmentUnitAddDto) {

        // checks if user is admin from token
        if (!userService.isUserAdmin(authentication.getName())) {
            throw new BadCredentialsException("");
        }


        return departmentService.addUnit(departmentUnitAddDto);
    }
    @RequestMapping(value ="/api/delUnit",method = RequestMethod.POST)
    @CrossOrigin
    public ResponseEntity delUnit(Authentication authentication , @RequestBody DepartmentUnitDto departmentUnitDto) {

        // checks if user is admin from token
        if (!userService.isUserAdmin(authentication.getName())) {
            throw new BadCredentialsException("");
        }


        return departmentService.delUnit(departmentUnitDto);
    }

}
