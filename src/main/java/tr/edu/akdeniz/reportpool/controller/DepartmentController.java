package tr.edu.akdeniz.reportpool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.edu.akdeniz.reportpool.model.DepartmentDto;
import tr.edu.akdeniz.reportpool.service.impl.DepartmentService;

import java.util.List;

@RestController
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @RequestMapping("/api/getDepartments")
    @CrossOrigin
    public List<DepartmentDto> getDepartments() {
        return departmentService.getAllDepartments();
    }


}
