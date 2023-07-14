package com.project.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends RegisterTimeEntity {

    /**
     * idx
     */
    private Integer userIdx;

    /**
     * 이메일
     */
    private String email;

    /**
     * 이름
     */
    private String name;

    /**
     * 유저 아이디
     */
    private String userid;

    /**
     * S : Student, G : Graduated, Z : Administrator
     */
    private String userType;

    /**
     * 비밀번호
     */
    private String password;

    /**
     * 학교
     */
    private Integer schoolName;

    /**
     * 입학년도
     */
    private Integer schoolYear;

    /**
     * 승인 여부
     */
    private String approvedYn;

    /**
     * 마지막 로그인 시간
     */
    private String lastLoginTime;

    /**
     * 메일 인증 코드
     */
    private String mailKey;

    /**
     * 메일 인증 확인
     */
    private String mailAuth;

    /**
     * 탈퇴여부
     */
    private String withdrawed_yn;

    /**
     * 탈퇴시간
     */
    private String withdrawed_time;

    /**
     * 깃허브 랭킹에 들어갈 것인감?
     */
    private String rankinYn;

    /**
     * 깃허브 링크
     */
    private String github_link;
}
