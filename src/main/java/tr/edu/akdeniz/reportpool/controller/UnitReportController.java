package tr.edu.akdeniz.reportpool.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tr.edu.akdeniz.reportpool.model.PaginationDto;
import tr.edu.akdeniz.reportpool.model.UnitReportDto;
import tr.edu.akdeniz.reportpool.service.impl.UnitReportService;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

@RestController
public class UnitReportController {

    @Autowired
    UnitReportService unitReportService;


    @RequestMapping(value = "/unitreports/{dept}/{unit}/")
    @CrossOrigin
    public String getUnitReports(@PathVariable int dept, @PathVariable int unit,HttpServletRequest request) throws JsonProcessingException
    {

        Enumeration<String> parameterNames = request.getParameterNames();
        Gson gson = new Gson();
        String search = request.getParameter("search[value]");
        String sortColumnIndex = request.getParameter("order[0][column]");
        String sortDir = request.getParameter("order[0][dir]");
        String skip = request.getParameter("start");
        String length = request.getParameter("length");
        String draw = request.getParameter("draw");

        PaginationDto paginationDto = unitReportService.getUnitsReports(dept,unit,draw,length,skip,sortDir,sortColumnIndex,search);

        String jsonString = gson.toJson(paginationDto);
        return jsonString;

    }

    @RequestMapping(value = "/downloadreports/{dept}/{unit}/")
    @CrossOrigin
    public String downloadReports(@PathVariable int dept, @PathVariable int unit,HttpServletRequest request) throws JsonProcessingException
    {

        Enumeration<String> parameterNames = request.getParameterNames();
        Gson gson = new Gson();
        String search = request.getParameter("search[value]");
        String sortColumnIndex = request.getParameter("order[0][column]");
        String sortDir = request.getParameter("order[0][dir]");
        String skip = request.getParameter("start");
        String length = request.getParameter("length");
        String draw = request.getParameter("draw");

        unitReportService.downloadReports(dept,unit,draw,length,skip,sortDir,sortColumnIndex,search);

        //String jsonString = gson.toJson(paginationDto);
        return "";

    }


    @CrossOrigin
    @RequestMapping(value = "tdd/unitreports/{id}",method = RequestMethod.GET)
    public List<UnitReportDto> getUnitReportsTest(@PathVariable int id){
        return  null;
    }
}