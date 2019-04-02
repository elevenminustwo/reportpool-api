package tr.edu.akdeniz.reportpool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tr.edu.akdeniz.reportpool.entity.Unit;
import tr.edu.akdeniz.reportpool.model.UnitDto;
import tr.edu.akdeniz.reportpool.service.impl.UnitService;

import java.util.List;

@RestController
public class UnitController {
    @Autowired
    UnitService unitService;

    @RequestMapping(value = "/api/getUnits/{id}",method = RequestMethod.GET)
    @CrossOrigin
    public List<UnitDto> getDepartments(@PathVariable int id) {
        return unitService.getAllUnitsByDepartmentId(id);
    }

}
