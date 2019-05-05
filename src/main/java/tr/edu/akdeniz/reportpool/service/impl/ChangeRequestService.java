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

    @Transactional
    public String[] sendPasswordChangeRequest(String username) {

        User user = userRepository.findByUsername(username);


        if (user != null) {

            // send password change form email

            tokenRepository.deleteAllByUserId(user.getUserId());
            Token token = new Token(user.getUserId());
            tokenRepository.save(token);

            String[] emailToken = new String[2];
            emailToken[0] = user.getEmail();
            emailToken[1] = token.getToken();

            return emailToken;

        } else {
            return null;
        }

    }


    @Transactional
    public String sendPasswordChangeRequestByEmail(String email) {

        User user = userRepository.findByEmail(email);


        if (user != null) {

            // send password change form email
            tokenRepository.deleteAllByUserId(user.getUserId());
            Token token = new Token(user.getUserId());
            tokenRepository.save(token);

            return token.getToken();

        } else {
            return "";
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
                out.println(formatter.format(date) + " " + userRepository.findByUserId(user.getUserId()).getUsername() + " isimli kullanici sifre degistirdi.");
            }
            catch (Exception e){
                System.out.println("not found file");
            }

            return true;

        }
        try (FileWriter fileWriter = new FileWriter("log.txt", true)) {
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            PrintWriter out = new PrintWriter(fileWriter);
            out.println(formatter.format(date) + " [HATA] " + userRepository.findByUserId(user.getUserId()).getUsername() + " isimli kullanici sifre degistirme islemi basarisiz oldu.");
        }
        catch (Exception e){
            System.out.println("not found file");
        }

        return false;

    }



}
