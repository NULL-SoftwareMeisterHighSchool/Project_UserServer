package com.project.backend.user.service.impl;

import com.project.backend.user.service.MailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
public class MailServiceImpl implements MailService {
    private String authcode;
    private  JavaMailSender emailSender;


    @Override
    public String createcode() {

        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for (int i = 0 ; i < 6 ; i++) {
            int index = random.nextInt(3);

            switch (index) {
                case 0 :
                    key.append((char) ((int)random.nextInt(26) + 97));
                    break;
                case 1:
                    key.append((char) ((int)random.nextInt(26) + 65));
                    break;
                case 2:
                    key.append(random.nextInt(9));
                    break;
            }
        }
        return key.toString();
    }

    @Override
    public String sendmail(String email) throws MessagingException {

        createcode();

        String sendFrom ="softwaremeister.null@gmail.com";
        String sendTo = email;
        String subject = "Somei인 인증코드입니다";
        String context = "<!DOCTYPE html><html lang=\"ko\"><head><meta charset=\"UTF-8\"><title>Somei인 인증코드입니다</title></head><body><div class=\"container\"><span style=\"--i:1\">" + authcode.charAt(0) + "</span><span style=\"--i:2\">" + authcode.charAt(1) + "</span><span style=\"--i:3\">" + authcode.charAt(2) +"</span><span style=\"--i:4\">" + authcode.charAt(3) + "</span><span style=\"--i:5\">O</span><span style=\"--i:6\">!</span></div></body></html><style>@import url('https://fonts.googleapis.com/css2?family=Alfa+Slab+One&display=swap');*{padding:0;margin:0;box-sizing:border-box;}body{background-color:#151719;display:flex;justify-content:center;align-items:center;min-height:100vh;}.container{position:relative;font-size:60px;}.container span{font-family:\"Alfa Slab One\",cursive;position:relative;display:inline-block;color: #fff;animation: wave 1s infinite;animation-delay: calc(.1s * var(--i));}@keyframes wave{0%,40%,100%{transform:translateY(0);}20%{transform:translateY(-20px);}}</style>";

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
        messageHelper.setFrom(sendFrom);
        messageHelper.setTo(sendTo);
        messageHelper.setSubject(subject);
        messageHelper.setText(context, "true");
        emailSender.send(message);

        return authcode;
    }

    @Override
    public String returnsentcode() {
        return authcode;
    }
}
