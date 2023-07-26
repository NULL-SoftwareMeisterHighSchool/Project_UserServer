package com.project.backend.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class AuthMail {




    /*
    회원가입 버튼을 눌렀는가? 이라면
    클라이언트한테서 유저 정보 받아오기
    DB에 저장 후 useridx 변수에 getwithemail()로 user의 idx값 저장

    mailservice에서 인증코드 생성
    인증코드 AuthMail class에 저장

     */
}
