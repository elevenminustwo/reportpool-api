package tr.edu.akdeniz.reportpool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.edu.akdeniz.reportpool.model.UserDto;
import tr.edu.akdeniz.reportpool.model.UserUnitEditDto;
import tr.edu.akdeniz.reportpool.pdfGenerater.GeneratePdfReport;
import tr.edu.akdeniz.reportpool.service.impl.UserService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;


@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/")
    public List<UserDto> getUser() {
        return userService.getUser();
    }

    @RequestMapping(path = "/api/tdd/getUser", method= RequestMethod.GET)
    public List<UserDto> getUserTest(){
        return userService.getUser();
    }

    @RequestMapping("/api/addDunit")
    @CrossOrigin
    public ResponseEntity addDunit(@RequestBody UserUnitEditDto userUnitEditDto) {

        return userService.addDunit(userUnitEditDto);
    }
    @RequestMapping("/api/delDunit")
    @CrossOrigin
    public ResponseEntity delDunit(@RequestBody UserUnitEditDto userUnitEditDto) {

        return userService.delDunit(userUnitEditDto);
    }

    @RequestMapping("/api/getUserIdOf/{username}")
    @CrossOrigin
    public ResponseEntity<Integer> getUserIdOf(@PathVariable String username) {
        return ok(userService.getUserIdOfUser(username));
    }

    @RequestMapping(value = "/pdfreport", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> citiesReport() throws IOException {

        ByteArrayInputStream bis = GeneratePdfReport.stream();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=pdfreport.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }


}
