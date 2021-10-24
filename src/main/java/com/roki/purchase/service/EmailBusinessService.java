package com.roki.purchase.service;

import com.roki.purchase.entity.AuthorityEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class EmailBusinessService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void passwordResetEmail(String password, String username, String to) {

        SimpleMailMessage message = new SimpleMailMessage();
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



    public void purchaseRequestPromotionToAccounting(String[] to, String purchaseHeaderNumber, String status) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(purchaseHeaderNumber +" to be " + status);
        message.setText("Dear " + Arrays.toString(to) + ", you have following purchase requests: " + purchaseHeaderNumber + " that you have to treat.Please log in application to proceed with your task-> http://localhost:8080/web/login-form");
        javaMailSender.send(message);
    }

    public void purchaseRequestPromotionToManager(String to,String purchaseHeaderNumber,String status) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(purchaseHeaderNumber +" to be " + status);
        message.setText("Dear " + to + ", you have following purchase requests: " + purchaseHeaderNumber + " that you have to treat.Please log in application to proceed with your task-> http://localhost:8080/web/login-form");
        javaMailSender.send(message);
    }

    public void emailInitiatorNotification(String to, String purchaseHeaderNumber,String status) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject(purchaseHeaderNumber + " has been promoted to next level");
        message.setText("Dear initiator: " + to + " your purchase request no: " + purchaseHeaderNumber + " has been " + status);
        javaMailSender.send(message);

    }
}
