package tr.edu.akdeniz.reportpool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.edu.akdeniz.reportpool.entity.Token;
import tr.edu.akdeniz.reportpool.entity.User;
import tr.edu.akdeniz.reportpool.repository.TokenRepository;
import tr.edu.akdeniz.reportpool.repository.UserRepository;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ChangeRequestService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    EmailSenderService emailSenderService;

    private static final long TOKEN_EXPIRE_TIME_MILLIS = 7200000; // 2 hours
    //test
    public static final String UI_LOCATION_TEST = "file:///Users/mertbicak/reportpool-ui/reportpool-ui/";
    // real
    private static final String UI_LOCATION = "localhost";

    @Transactional
    public boolean sendPasswordChangeRequest(String username) {

        User user = userRepository.findByUsername(username);


        if (user != null) {

            // send password change form email

            tokenRepository.deleteAllByUserId(user.getUserId());
            Token token = new Token(user.getUserId());
            tokenRepository.save(token);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Şifre Yenileme");
            mailMessage.setFrom("reportpooladmin@gmail.com");
            // during development
            mailMessage.setText("Şifrenizi yenilemek için buraya tıklayınız : "
                    + UI_LOCATION_TEST + "/passwordReset.html?token="+token.getToken());
            /* after deployment
            mailMessage.setText("To reset your password, please click here : "
                    +"http://" + UI_LOCATION + ":8080/api/change/changePassword?token="+token.getToken());
                    */


            try {
                emailSenderService.sendEmail(mailMessage);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            return true;

        } else {
            return false;
        }

    }


    @Transactional
    public boolean sendPasswordChangeRequestByEmail(String email) {

        User user = userRepository.findByEmail(email);


        if (user != null) {

            // send password change form email
            tokenRepository.deleteAllByUserId(user.getUserId());
            Token token = new Token(user.getUserId());
            tokenRepository.save(token);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Şifre Yenileme");
            mailMessage.setFrom("reportpooladmin@gmail.com");
            // during development
            mailMessage.setText("Şifrenizi yenilemek için buraya tıklayınız : "
                    + UI_LOCATION_TEST + "/passwordReset.html?token="+token.getToken());
            /* after deployment
            mailMessage.setText("To reset your password, please click here : "
                    +"http://" + UI_LOCATION + ":8080/api/change/changePassword?token="+token.getToken());
                    */


            try {
                emailSenderService.sendEmail(mailMessage);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            return true;

        } else {
            return false;
        }

    }


    @Transactional
    public boolean changePassword(String resetToken, String newPassword) {

        Token token = tokenRepository.findByToken(resetToken);
        User user = null;


        if (token != null) {

            // check date
            long timePassed = System.currentTimeMillis() - token.getCreatedDate();
            if (timePassed > TOKEN_EXPIRE_TIME_MILLIS) {
                // token expired
                return false;
            }

            user = userRepository.findByUserId(token.getUserId());
        }

        if (user != null) {

            user.setPassword(newPassword);
            userRepository.save(user);

            // delete token after all is done
            tokenRepository.deleteAllByUserId(user.getUserId());

            try (FileWriter fileWriter = new FileWriter("log.txt", true)) {
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                PrintWriter out = new PrintWriter(fileWriter);
                out.println(formatter.format(date) + " " + user.getUserId() + " idli kullanici sifre degistirdi.");
            }
            catch (Exception e){
                System.out.println("not found file");
            }

            return true;

        }

        return false;

    }



}
