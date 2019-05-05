package tr.edu.akdeniz.reportpool.controller;

import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.edu.akdeniz.reportpool.service.impl.ChangeRequestService;

@RestController
@RequestMapping("/api/change")
public class ChangeRequestController {

    @Autowired
    ChangeRequestService changeRequestService;

    @PostMapping("/sendPasswordChangeRequest")
    @CrossOrigin
    public ResponseEntity<String> passwordChangeRequest(@RequestParam(value = "username", required = true) String username) {

        boolean success = false;

        success = changeRequestService.sendPasswordChangeRequest(username);


        if (success) {
            return ResponseEntity.ok("Request sent.");
        } else {
            return ResponseEntity.ok("Request could not be sent.");
        }

    }

    @PostMapping("/sendPasswordChangeRequestByEmail")
    @CrossOrigin
    public ResponseEntity<String> passwordChangeRequestByEmail(@RequestParam(value = "email", required = true) String email) {

        boolean success = false;

        success = changeRequestService.sendPasswordChangeRequestByEmail(email);


        if (success) {
            return ResponseEntity.ok("Request sent.");
        } else {
            return ResponseEntity.ok("Request could not be sent.");
        }

    }

    @PostMapping("/changePassword")
    @CrossOrigin
    public ResponseEntity<String> changePassword(@RequestParam(value = "resetToken", required = true) String resetToken,
                                                 @RequestParam(value = "newPassword", required = true) String newPassword) {

        boolean success = changeRequestService.changePassword(resetToken, newPassword);

        if (!success) {
            throw new RuntimeException();
        } else {
            return ResponseEntity.ok("success");
        }

    }



}
