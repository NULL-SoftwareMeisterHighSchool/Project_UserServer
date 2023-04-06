package com.project.backend.controller;

import com.project.backend.domain.User;
import com.project.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    public final UserService userService;
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

    @DeleteMapping("/withdraw/{id}")
    public String withdraw(@PathVariable int id) {
        //비밀번호 치는 코드 다음에 짜자
        userService.withdraw(id);
        return "회원탈퇴 완료";
    }
}
