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

    @GetMapping("/edit/{id}")
    public User edituser(@PathVariable int id) {
        return userService.getwithidx(id);
    }

    @PutMapping("/edit/{id}")
    public User edituser(User user, @PathVariable int id) {
        userService.update(id, user);
        return userService.getwithidx(id);
    }

    @GetMapping("/findid/{email}")
    public String finduserid(@PathVariable String email) {
        return userService.finduseridwithemail(email);
    }

    @PutMapping("/edit/pwd/{id}")
    public User changepwd(@PathVariable int id, String nowpwd, String newpwd) {
        userService.changePassword(nowpwd, newpwd);
        return userService.getwithidx(id);
    }

    @DeleteMapping("/withdraw")
    public String withdraw(String email) throws MessagingException {
        mailService.sendmail(email);
        userService.withdraw(email);

        //만약 회원이 존재하지 않는다면
        if ( true ) {

        } else {
            //회원이 존재한다면
            //userService.withdraw(id);
        }
        return "회원탈퇴 완료";
    }
}
