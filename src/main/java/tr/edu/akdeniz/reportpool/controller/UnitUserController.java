package tr.edu.akdeniz.reportpool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.edu.akdeniz.reportpool.model.UserUnitDto;
import tr.edu.akdeniz.reportpool.service.impl.UserUnitsService;

import java.util.List;

@RestController
public class UnitUserController {

    @Autowired
    private UserUnitsService userUnitsService;

    @RequestMapping(value="api/getUserUnits/{userId}",method = RequestMethod.GET)
    @CrossOrigin
    public ResponseEntity<List<UserUnitDto>> getUserUnitsById(@PathVariable int userId) {

        return new ResponseEntity(userUnitsService.getUserUnits(userId), HttpStatus.OK);
    }

}
