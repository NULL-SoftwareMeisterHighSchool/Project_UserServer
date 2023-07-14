package com.project.backend.controller;

import com.project.backend.domain.TokenInfo;
import com.project.backend.domain.User;
import com.project.backend.domain.UserDTO;
import com.project.backend.service.MailService;
import com.project.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import setting.common.domain.RestResult;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    public final UserService userService;
    final MailService mailService;

    @PostMapping("/login")
    public TokenInfo student_login(@RequestBody User user) {
        TokenInfo tokenInfo = userService.login(user.getEmail(), user.getPassword());
        return tokenInfo;
    }

    @PostMapping("/sendcode")
    public String sendcode(String email) throws MessagingException {
        mailService.sendmail(email) ;
        return mailService.returnsentcode();
    }

    @PostMapping("/signup/student")
    public ResponseEntity<String> student_register(@RequestBody UserDTO userDTO) {
        userService.register(userDTO);
        userDTO.setUserType("S");
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/signup/graduated")
    public ResponseEntity<String> graduated_register(@RequestBody UserDTO userDTO) {
        userService.register(userDTO);
        userDTO.setUserType("G");
        return ResponseEntity.ok("User registered successfully");
    }



    @PostMapping("/forgetpwd")
    public String forgetpwd() {

        return "redirect:/login";
    }


}
