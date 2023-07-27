package com.project.backend.user.controller;

import com.project.backend.user.entity.User;
import com.project.backend.user.service.MailService;
import com.project.backend.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final MailService mailService;

    @GetMapping("/edit/{id}")
    public ResponseEntity<?> edituser(@PathVariable int id) {
        try {
            User user = userService.getAuthorizedUser();

            //JWT의 userIdx가 사용자의 userIdx와 일치한가?
            if ( user.getUserIdx() != id) {
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authorized");
            }

            User authUser = userService.getwithidx(id);
            if ( authUser.getWithdrawed_yn() == "Y") {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("탈퇴된 회원");
            }

            if ( user != null) {
                return ResponseEntity.status(HttpStatus.OK).body(authUser);
            }
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("유저 조회 실패 : ");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("여기는 코드상 올 수 없음");
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> edituser(@PathVariable int id, @RequestBody User requestuser) {
        try {
            User user = userService.getAuthorizedUser();

            if ( user.getUserIdx() != id ) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authorized");
            } else if ( user == null) {
                return ResponseEntity.notFound().build();
            }
            User updateduser = userService.update(id, requestuser);

            if ( updateduser != null) {
                return ResponseEntity.ok("User updated successfully");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("에러 메세지 : " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("여기는 코드상 올 수 없음");
    }

    @PatchMapping("/edit/pwd/{id}")
    public ResponseEntity<String> changepwd(@PathVariable int id, @RequestParam String nowpwd, @RequestParam String newpwd) {
        try {
            User user = userService.getAuthorizedUser();

            if ( user.getUserIdx() != id ) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authorized");
            } else if ( user == null) {
                return ResponseEntity.notFound().build();
            }

            if ( userService.changePassword(nowpwd, newpwd) ) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("비밀번호 변경 성공");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("에러메세지 : " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("여기는 코드상 올 수 없음");
    }

    @DeleteMapping("/withdraw/{id}")
    public ResponseEntity<String> withdraw(@PathVariable int id) {
        try {
            User user = userService.getAuthorizedUser();

            if ( user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제할 회원이 없음");
            } else if ( user.getUserIdx() != id) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자 인증 실패");
            }

            String user_email = user.getEmail();
            if ( userService.withdraw(user_email)) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("회원탈퇴 성공");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("에러메세지 : " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("여기는 코드상 올 수 없음");
    }
}
