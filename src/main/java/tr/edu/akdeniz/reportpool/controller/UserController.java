package tr.edu.akdeniz.reportpool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tr.edu.akdeniz.reportpool.model.UserDto;
import tr.edu.akdeniz.reportpool.model.UserUnitEditDto;
import tr.edu.akdeniz.reportpool.service.impl.EmailSenderService;
import tr.edu.akdeniz.reportpool.service.impl.UserService;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;


@RestController
public class UserController {

    @Value("${reportpool.uilocation}")
    private String uiLocation;

    @Autowired
    UserService userService;
    @Autowired
    EmailSenderService emailSenderService;

    @RequestMapping("/")
    public List<UserDto> getUser(Authentication authentication) {

        // checks if user is admin from token
        if (!userService.isUserAdmin(authentication.getName())) {
            throw new BadCredentialsException("");
        }


        return userService.getUser();
    }

    @RequestMapping(path = "/api/tdd/getUser", method= RequestMethod.GET)
    public List<UserDto> getUserTest(){
        return userService.getUser();
    }

    @RequestMapping("/api/addDunit")
    @CrossOrigin
    public ResponseEntity addDunit(Authentication authentication, @RequestBody UserUnitEditDto userUnitEditDto) {

        // checks if user is admin from token
        if (!userService.isUserAdmin(authentication.getName())) {
            throw new BadCredentialsException("");
        }

        return userService.addDunit(userUnitEditDto);
    }
    @RequestMapping("/api/delDunit")
    @CrossOrigin
    public ResponseEntity delDunit(Authentication authentication, @RequestBody UserUnitEditDto userUnitEditDto) {

        // checks if user is admin from token
        if (!userService.isUserAdmin(authentication.getName())) {
            throw new BadCredentialsException("");
        }

        return userService.delDunit(userUnitEditDto);
    }

    @RequestMapping("/api/getUserIdOf/{username}")
    @CrossOrigin
    public ResponseEntity<Integer> getUserIdOf(Authentication authentication, @PathVariable String username) {

        // only user himself can access his user id
        if (!authentication.getName().equals(username)) {
            throw new BadCredentialsException("");
        }

        return ok(userService.getUserIdOfUser(username));
    }

    @RequestMapping("/api/sendVerificationEmail/{userId}")
    @CrossOrigin
    public ResponseEntity sendVerificationEmail(Authentication authentication, @PathVariable int userId) {

        // checks if user is admin from token
        if (!userService.isUserAdmin(authentication.getName())) {
            throw new BadCredentialsException("");
        }

        String username = userService.getUsernameOfUserId(userId);
        String email = userService.getEmailOfUserId(userId);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Hesabınız aktifleştirildi");
        mailMessage.setFrom("reportpooladmin@gmail.com");
        // during development
        mailMessage.setText(username + " kullanıcı adlı hesabınız aktifleştirildi. Artık giriş yapabilirsiniz: \n\n"  + uiLocation + "login.html");

        emailSenderService.sendEmail(mailMessage);

        return ok("sent");
    }


}
