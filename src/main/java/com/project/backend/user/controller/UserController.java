package com.project.backend.user.controller;

import com.project.backend.user.domain.User;
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
    public ResponseEntity<User> edituser(@PathVariable int id) {
        User user = userService.getAuthorizedUser();

        //JWT의 userIdx가 사용자의 userIdx와 일치한가?
        if ( user.getUserIdx() != id) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authorized");
        }

        User authUser = userService.getwithidx(id);

        if ( user != null) {
            return ResponseEntity.ok(authUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> edituser(@PathVariable int id, @RequestBody User requestuser) {
        User user = userService.getAuthorizedUser();

        if ( user.getUserIdx() != id ) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authorized");
        } else if ( user == null) {
            return ResponseEntity.notFound().build();
        }
        User updateduser = userService.update(id, requestuser);

        if ( updateduser != null) {
            return ResponseEntity.ok("User updated successfully");
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update user");
        }
    }

    @PutMapping("/edit/pwd{id}")
    public ResponseEntity<String> changepwd(@PathVariable int id, @RequestParam String nowpwd, @RequestParam String newpwd) {
        User user = userService.getAuthorizedUser();

        if ( user.getUserIdx() != id ) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authorized");
        } else if ( user == null) {
            return ResponseEntity.notFound().build();
        }

        if ( userService.changePassword(nowpwd, newpwd) ) {
            return ResponseEntity.ok("Password updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update password");
        }
    }

    @DeleteMapping("/withdraw/{id}")
    public ResponseEntity<String> withdraw(@PathVariable int id) {
        User user = userService.getAuthorizedUser();

        if ( user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        } else if ( user.getUserIdx() != id) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
        }

        String user_email = user.getEmail();
        if ( userService.withdraw(user_email)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원탈퇴 실패");
        }


    }
}
