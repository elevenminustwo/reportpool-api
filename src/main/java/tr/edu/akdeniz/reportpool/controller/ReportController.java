package tr.edu.akdeniz.reportpool.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tr.edu.akdeniz.reportpool.entity.Report;
import tr.edu.akdeniz.reportpool.service.ReportService;

import java.util.List;

// Resource used when constructing this controller:
// https://www.callicoder.com/spring-boot-file-upload-download-rest-api-example/

@RestController
public class ReportController {


    @Autowired
    ReportService reportService;

    @RequestMapping("/allreports")
    @CrossOrigin
    public List<Report> getAllReports() {
        return reportService.findAll();
    }

    @RequestMapping("tdd/allreports")
    @CrossOrigin
    public List<Report> getAllReportsTest() {
        return reportService.findAll();
    }


    // request body ornegi:  {"reportId":null,"title":"Rapor basligi","text":"Rapor icerigi","isCompleted":1,"dateCompleted":"2006-12-24","userId":1,"departmentunitId":1}

    @PostMapping(value = "/savereport")
    @CrossOrigin
    public Report saveReport(@RequestParam("report") String reportJson, @RequestParam("files") MultipartFile[] files) {
        Gson gson = new Gson();
        Report report = gson.fromJson(reportJson, Report.class);
        return reportService.save(report, files);
    }

    @GetMapping(value = "/api/getIncompleteReportOf/{userId}")
    @CrossOrigin
    public Report getInCompleteReportOf(@PathVariable int userId) {
        return reportService.getIncompleteReportOf(userId);
    }


}
