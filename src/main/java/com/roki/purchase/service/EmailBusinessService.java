package com.roki.purchase.service;


import com.roki.purchase.entity.AuthorityEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailBusinessService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void passwordResetEmail(String password, String username, String to) {
        // String from="rosu.bogdan@gmail.com";
//         to = "rosu.bogdan@gmail.com";

        SimpleMailMessage message = new SimpleMailMessage();
       // message.setFrom(from);
        message.setTo(to);
        message.setSubject("Password reset");
        message.setText(" Dear "+ username +" Your password was reset by admin based on your request. Your temporary password is:" + password
        + ". Login in the application and change the password -> http://localhost:8080/web/login-form");

        javaMailSender.send(message);
    }
    public void userCreationEmail(String password, String username, String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("User creation");
        message.setText("Dear " + username + " your profile was created in Purchase flow management App. Your temporary password is:" + password +
                       ". Login in the application and change the password -> http://localhost:8080/web/login-form");

        javaMailSender.send(message);
    }

    public void userAuthorityChange(String username, String authority, String to, String action, List<AuthorityEntity> authList) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Authority change");
        message.setText("Dear " + username + " the following authority: "+ authority + " has been " + action  +" your profile." +
                " You have now the following authorities " + authList);
        javaMailSender.send(message);
    }
}
