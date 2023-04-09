package com.project.backend.controller;

import com.project.backend.domain.User;
import com.project.backend.service.MailService;
import com.project.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;


@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final MailService mailService;

    @PutMapping("/edit/{id}")
    public User edituser(User user, @PathVariable int id) {
        userService.update(id, user);
        return userService.getwithidx(id);
    }

    @PutMapping("/edit/pwd/{id}")
    public User changepwd(@PathVariable int id, String nowpwd, String newpwd) {
        userService.changePassword(nowpwd, newpwd);
        return userService.getwithidx(id);
    }

    @DeleteMapping("/withdraw")
    public String withdraw(String email) throws MessagingException {
        mailService.sendmail(email);
        if ( )
        userService.withdraw();
        return "회원탈퇴 완료";
    }
}
