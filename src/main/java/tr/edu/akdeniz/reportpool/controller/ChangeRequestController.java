package tr.edu.akdeniz.reportpool.controller;

import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tr.edu.akdeniz.reportpool.service.impl.ChangeRequestService;
import tr.edu.akdeniz.reportpool.service.impl.EmailSenderService;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api/change")
public class ChangeRequestController {

    @Value("${reportpool.uilocation}")
    private String uiLocation;

    @Autowired
    ChangeRequestService changeRequestService;
    @Autowired
    EmailSenderService emailSenderService;

    // for test
    public static final String UI_LOCATION_TEST = "file:///Users/mertbicak/reportpool-ui/reportpool-ui/";
    // real
    private static final String UI_LOCATION = "localhost";

    @PostMapping("/sendPasswordChangeRequest")
    @CrossOrigin
    public ResponseEntity<String> passwordChangeRequest(Authentication authentication, @RequestParam(value = "username", required = true) String username) {

        // if username got from token is not the same user that we're trying to change the password, abort
        if(!authentication.getName().equals(username)) {
            throw new BadCredentialsException("");
        }

        String[] emailAndToken;

        emailAndToken = changeRequestService.sendPasswordChangeRequest(username);

        if (emailAndToken == null) {
            return ResponseEntity.ok("could not be sent");
        }

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailAndToken[0]);
        mailMessage.setSubject("Şifre Yenileme");
        mailMessage.setFrom("reportpooladmin@gmail.com");
        mailMessage.setText("Şifrenizi yenilemek için buraya tıklayınız : "
                + uiLocation + "/passwordReset.html?token="+ emailAndToken[1]);


        emailSenderService.sendEmail(mailMessage);


        return ResponseEntity.ok("Request sent.");


    }

    @PostMapping("/sendPasswordChangeRequestByEmail")
    @CrossOrigin
    public ResponseEntity<String> passwordChangeRequestByEmail(@RequestParam(value = "email", required = true) String email) {

        String tokenToSend = "";

        tokenToSend = changeRequestService.sendPasswordChangeRequestByEmail(email);



        if (!tokenToSend.equals("")) {

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(email);
            mailMessage.setSubject("Şifre Yenileme");
            mailMessage.setFrom("reportpooladmin@gmail.com");
            mailMessage.setText("Şifrenizi yenilemek için buraya tıklayınız : "
                    + uiLocation + "/passwordReset.html?token="+tokenToSend);



            try {
                emailSenderService.sendEmail(mailMessage);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.ok("Request could not be sent.");
            }
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
