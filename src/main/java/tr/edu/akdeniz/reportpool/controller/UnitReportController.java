package tr.edu.akdeniz.reportpool.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tr.edu.akdeniz.reportpool.model.PaginationDto;
import tr.edu.akdeniz.reportpool.model.UnitReportDto;
import tr.edu.akdeniz.reportpool.service.impl.UnitReportService;
import tr.edu.akdeniz.reportpool.service.impl.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

@RestController
public class UnitReportController {

    @Autowired
    UnitReportService unitReportService;
    @Autowired
    UserService userService;


    @RequestMapping(value = "/unitreports/{dept}/{unit}/{fromDate}/{toDate}/")
    @CrossOrigin
    public String getUnitReports(Authentication authentication, @PathVariable int dept, @PathVariable int unit, @PathVariable String fromDate, @PathVariable String toDate, HttpServletRequest request) throws JsonProcessingException
    {

        // abort if token owner is not assigned to dept/unit as authority
        if(!userService.userIsDepartmentUnitAuthority(authentication.getName(), dept, unit)) {
            throw new BadCredentialsException("");
        }

        Enumeration<String> parameterNames = request.getParameterNames();
        Gson gson = new Gson();
        String search = request.getParameter("search[value]");
        String sortColumnIndex = request.getParameter("order[0][column]");
        String sortDir = request.getParameter("order[0][dir]");
        String skip = request.getParameter("start");
        String length = request.getParameter("length");
        String draw = request.getParameter("draw");

        if (fromDate.equals("undefined")) {
            fromDate = "1970-01-01";
        }
        if (toDate.equals("undefined")) {
            toDate = "2200-01-01";
        }
        System.out.println("from date: " + fromDate);
        System.out.println("to date: " + toDate);

        PaginationDto paginationDto = unitReportService.getUnitsReports(dept,unit,fromDate,toDate,draw,length,skip,sortDir,sortColumnIndex,search);

        String jsonString = gson.toJson(paginationDto);
        return jsonString;

    }



    @CrossOrigin
    @RequestMapping(value = "tdd/unitreports/{id}",method = RequestMethod.GET)
    public List<UnitReportDto> getUnitReportsTest(@PathVariable int id){
        return  null;
    }
}