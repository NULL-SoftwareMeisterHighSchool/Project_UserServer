package com.project.backend.user.service;

import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public interface MailService {
    /**
     * @return 메일 코드
     */
    public String createcode();

    /**
     * @return 메일 코드
     */
    public String sendmail(String email) throws MessagingException;

    /**
     * @return
     */
    String returnsentcode();
}
