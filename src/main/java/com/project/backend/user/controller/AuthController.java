package com.project.backend.user.controller;

import com.project.backend.user.domain.TokenInfo;
import com.project.backend.user.domain.User;
import com.project.backend.user.domain.UserDTO;
import com.project.backend.user.service.MailService;
import com.project.backend.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    public final UserService userService;
    final MailService mailService;

    @PostMapping("/login")
    public ResponseEntity<TokenInfo> student_login(@RequestBody User user) {
        TokenInfo tokenInfo = userService.login(user.getEmail(), user.getPassword());

        if (tokenInfo != null) {
            return ResponseEntity.ok(tokenInfo);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/sendcode")
    public ResponseEntity<String> sendcode(String email) throws MessagingException {
        mailService.sendmail(email) ;
        return ResponseEntity.ok("hello");
    }

    @PostMapping("/signup/student")
    public ResponseEntity<String> student_register(@RequestBody UserDTO userDTO) {
        try {
            userService.register(userDTO);
            userDTO.setUserType("S");
            return ResponseEntity.ok("User registered successfully");
        } catch (IllegalArgumentException e) { // 입력값이 유효하지 않음 ( Bad Request ) - 400
            return ResponseEntity.badRequest().body("Invalid input" + e.getMessage());
        } catch (DataIntegrityViolationException e) { // 중복된 사용자 정보 ( Conflict )  - 409
            ResponseEntity.status(HttpStatus.CONFLICT).body("Registration failed : email already exist");
        } catch (Exception e) { // 서버 에러로 인한 회원가입 실패 ( Internal Server Error ) - 500
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed : Internal Server Error");
        }
        return null; // 여기까지 올일 없긴 해
    }

    @PostMapping("/signup/graduated")
    public ResponseEntity<String> graduated_register(@RequestBody UserDTO userDTO) {
        try {
            userService.register(userDTO);
            userDTO.setUserType("G");
            return ResponseEntity.ok("User registered successfully");
        } catch (IllegalArgumentException e) { // 입력값이 유효하지 않음 ( Bad Request ) - 400
            return ResponseEntity.badRequest().body("Invalid input" + e.getMessage());
        } catch (DataIntegrityViolationException e) { // 중복된 사용자 정보 ( Conflict )  - 409
            ResponseEntity.status(HttpStatus.CONFLICT).body("Registration failed : email already exist");
        } catch (Exception e) { // 서버 에러로 인한 회원가입 실패 ( Internal Server Error ) - 500
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed : Internal Server Error");
        }
        return null; // 꺄항
    }
}
