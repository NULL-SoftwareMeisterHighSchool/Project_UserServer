package com.project.backend.user.controller;

import com.project.backend.user.domain.User;
import com.project.backend.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class SuperController {

    private final UserService userService;

    @GetMapping("/userlist")
    public List<User> userList() {
        return userService.getUserList(0, userService.getCount());
    }

    //이메일로 유저 검색
    //
}
