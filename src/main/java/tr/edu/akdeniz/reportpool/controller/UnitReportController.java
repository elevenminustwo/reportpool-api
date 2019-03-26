package tr.edu.akdeniz.reportpool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tr.edu.akdeniz.reportpool.model.UnitReportDto;
import tr.edu.akdeniz.reportpool.service.impl.UnitReportService;

import java.util.List;

@RestController
public class UnitReportController {

    @Autowired
    UnitReportService unitReportService;
    @CrossOrigin
    @RequestMapping(value = "/unitreports/{id}",method = RequestMethod.GET)
    public List<UnitReportDto> getUnitReports(@PathVariable int id){
      return  unitReportService.getUnitReports(id);
    }
}
