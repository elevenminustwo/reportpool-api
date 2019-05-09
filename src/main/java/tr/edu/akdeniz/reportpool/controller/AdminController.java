package tr.edu.akdeniz.reportpool.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import javafx.scene.control.Pagination;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tr.edu.akdeniz.reportpool.model.PaginationDto;
import tr.edu.akdeniz.reportpool.model.UserDto;
import tr.edu.akdeniz.reportpool.model.UserrolesDto;
import tr.edu.akdeniz.reportpool.service.impl.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;

@RestController
public class AdminController {

    @Autowired
    UserService userService;


    @RequestMapping(value = "/api/getPersons")
    @CrossOrigin
    public String getUser(Authentication authentication, HttpServletRequest request) throws JsonProcessingException {

        // checks if user is admin from token
        if (!userService.isUserAdmin(authentication.getName())) {
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

        PaginationDto paginationDto = userService.getPersons(draw,length,skip,sortDir,sortColumnIndex,search);

        String jsonString = gson.toJson(paginationDto);
        return jsonString;
    }
    @RequestMapping(value = "/api/tdd/getPersons")
    @CrossOrigin
    public String getUserTest(HttpServletRequest request) throws JsonProcessingException {
        Enumeration<String> parameterNames = request.getParameterNames();
        Gson gson = new Gson();
        String search = request.getParameter("search[value]");
        String sortColumnIndex = request.getParameter("order[0][column]");
        String sortDir = request.getParameter("order[0][dir]");
        String skip = request.getParameter("start");
        String length = request.getParameter("length");
        String draw = request.getParameter("draw");

        PaginationDto paginationDto = userService.getPersons("0","10","0","0","0","");

        String jsonString = gson.toJson(paginationDto);
        return jsonString;
    }

    @RequestMapping(value = "/api/addRole")
    @CrossOrigin
    public ResponseEntity addRole(Authentication authentication, @RequestBody UserrolesDto userrolesDto) {

        // checks if user is admin from token
        if (!userService.isUserAdmin(authentication.getName())) {
            throw new BadCredentialsException("");
        }

        return userService.addRole(userrolesDto);
    }
    @RequestMapping(value = "/api/delRole")
    @CrossOrigin
    public ResponseEntity delRole(Authentication authentication, @RequestBody UserrolesDto userrolesDto) {

        // checks if user is admin from token
        if (!userService.isUserAdmin(authentication.getName())) {
            throw new BadCredentialsException("");
        }

        return userService.delRole(userrolesDto);
    }

    @GetMapping(value = "/api/getLogs/{surfaceOrTechnical}")
    @CrossOrigin
    public ResponseEntity<String> getLogs(Authentication authentication, @PathVariable boolean surfaceOrTechnical) {

        // checks if user is admin from token
        if (!userService.isUserAdmin(authentication.getName())) {
            throw new BadCredentialsException("");
        }

        StringBuilder sb = new StringBuilder();
        // read logs from file
        File file;
        if (surfaceOrTechnical) {
            file = new File("log.txt");
        } else {
            file = new File("logfile.log");
        }
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (sc.hasNextLine()) {
            sb.append(sc.nextLine());
            sb.append("<br>");
        }

        sc.close();

        return ResponseEntity.ok(sb.toString());
    }


}
