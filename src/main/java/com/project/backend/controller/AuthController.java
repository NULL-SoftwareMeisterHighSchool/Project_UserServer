package com.project.backend.controller;

import com.project.backend.domain.TokenInfo;
import com.project.backend.domain.User;
import com.project.backend.service.MailService;
import com.project.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    public final UserService userService;
    final MailService mailService;

    @PostMapping("/login")
    public TokenInfo login(@RequestBody User user) {
        TokenInfo tokenInfo = userService.login(user.getEmail(), user.getPassword());
        return tokenInfo;
    }

    @PostMapping("/sendcode")
    public String sendcode(String email) throws MessagingException {
        mailService.sendmail(email) ;
        return mailService.returnsentcode();
    }

    @PostMapping("/signup")
    public String register(User user) {
        userService.register(user);
        return "redirect:/login";
    }

    @PostMapping("/forgetpwd")
    public String forgetpwd() {

        return "redirect:/login";
    }


}
