package com.ecommerce.aafrincosmetics.service.Others;


import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Async
    public void sendEmail(String toEmail, String subject, String userMsg){
//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//        simpleMailMessage.setTo(toEmail);
//        simpleMailMessage.setSubject(subject);
//        simpleMailMessage.setText(userMsg);
//        simpleMailMessage.setFrom("beeplaw21@gmail.com");
//        javaMailSender.send(simpleMailMessage);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try{
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(userMsg);
            helper.setFrom("beeplaw21@gmail.com");
            javaMailSender.send(message);

        }catch (Exception e){

        }


    }
}
