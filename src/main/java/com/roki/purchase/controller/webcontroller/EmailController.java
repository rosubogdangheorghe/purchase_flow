package com.roki.purchase.controller.webcontroller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;

@Controller
public class EmailController {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail() {

    }
}
