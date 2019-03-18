package tr.edu.akdeniz.reportpool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tr.edu.akdeniz.reportpool.entity.Report;
import tr.edu.akdeniz.reportpool.service.ReportService;

import java.util.List;

@RestController
public class ReportController {


    @Autowired
    ReportService reportService;

    @RequestMapping("/allreports")
    public List<Report> getAllReports() {
        return reportService.findAll();
    }



    // request body ornegi:  {"reportId":null,"title":"Rapor basligi","text":"Rapor icerigi","isCompleted":1,"dateCompleted":"2006-12-24","userId":1,"departmentunitId":1}

    @RequestMapping(value = "/savereport", method = RequestMethod.POST)
    public Report saveReport(@RequestBody Report report) {
        return reportService.save(report);
    }



}
