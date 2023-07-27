package com.project.backend.user.controller;

import com.project.backend.JwtTokenProvider;
import com.project.backend.user.entity.TokenInfo;
import com.project.backend.user.entity.User;
import com.project.backend.user.service.MailService;
import com.project.backend.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class AuthController {
    private final UserService userService;
    private final MailService mailService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<String> student_login(@RequestHeader("Authorization") String authorizationHeader, @RequestBody User user) {
        String token = authorizationHeader.substring(7);

        if (jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token.");
        }
        TokenInfo tokenInfo = userService.login(user.getEmail(), user.getPassword());

        if (tokenInfo != null) {
            return ResponseEntity.status(HttpStatus.OK).body("성공");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/sendcode")
    public ResponseEntity<?> sendcode(String email) {
        try {
            if ( userService.getwithemail(email) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 이메일");
            }
            return new ResponseEntity<>(mailService.sendmail(email), HttpStatus.OK);
        }catch ( Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("에러메시지 : " + e.getMessage());
        }
    }

    @PostMapping("/signup/student")
    public ResponseEntity<String> student_register(@RequestBody User user) {
        try {
            user.setUserType("S");
            userService.register(user);
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
    public ResponseEntity<String> graduated_register(@RequestBody User user) {
        try {
            user.setUserType("G");
            userService.register(user);
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
