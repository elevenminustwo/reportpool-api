package tr.edu.akdeniz.reportpool.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import javafx.scene.control.Pagination;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import tr.edu.akdeniz.reportpool.model.PaginationDto;
import tr.edu.akdeniz.reportpool.model.UserDto;
import tr.edu.akdeniz.reportpool.service.impl.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

@RestController
public class AdminController {

    @Autowired
    UserService userService;


    @RequestMapping(value = "/api/getPersons")
    @CrossOrigin
    public String getUser(HttpServletRequest request) throws JsonProcessingException {
        Enumeration<String> parameterNames = request.getParameterNames();
        Gson gson = new Gson();
        String search = request.getParameter("search[value]");
        String sortColumnIndex = request.getParameter("order[0][column]");
        String sortDir = request.getParameter("order[0][dir]");
        String skip = request.getParameter("start");
        String length = request.getParameter("length");
        String draw = request.getParameter("draw");

        PaginationDto paginationDto = userService.getPersons(draw,length,skip,sortDir,sortColumnIndex,search);

        String jsonString = gson.toJson(paginationDto);
        return jsonString;
    }
    @RequestMapping(value = "/api/tdd/getPersons", method= RequestMethod.POST)
    public PaginationDto getUserTest() {
        return null;
    }

}