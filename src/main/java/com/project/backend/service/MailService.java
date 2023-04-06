package com.project.backend.service;

import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public interface MailService {
    /**
     * @return 메일 코드
     */
    public String createcode();

    /**
     * @return 메일 코드
     */
    public MimeMessage sendmail(String email) throws MessagingException;

    /**
     * @return 인증코드
     */
    String returnsentcode();
}
