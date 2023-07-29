package com.project.backend.user.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "user_table")
@NoArgsConstructor
@AllArgsConstructor
public class User extends RegisterTimeEntity {

    /**
     * idx
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "useridx", nullable = false)
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
    private Schoolenum school;

    /**
     * 입학년도
     */
    private Integer admissionYear;

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
    private String withdrawed;

    /**
     * 탈퇴시간
     */
    private String withdrawed_time;

    /**
     * 깃허브 링크
     */
    private String githubId;

    public Integer getUserIdx() {
        return userIdx;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getUserid() {
        return userid;
    }

    public String getUserType() {
        return userType;
    }

    public String getPassword() {
        return password;
    }

    public Schoolenum getSchoolName() {
        return school;
    }

    public Integer getSchoolYear() {
        return admissionYear;
    }

    public String getApprovedYn() {
        return approvedYn;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public String getMailKey() {
        return mailKey;
    }

    public String getMailAuth() {
        return mailAuth;
    }

    public String getWithdrawed_yn() {
        return withdrawed;
    }

    public String getWithdrawed_time() {
        return withdrawed_time;
    }

    public String getGithub_link() {
        return githubId;
    }

    public void setUserIdx(Integer userIdx) {
        this.userIdx = userIdx;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSchoolName(Schoolenum schoolName) {
        this.school = schoolName;
    }

    public void setSchoolYear(Integer schoolYear) {
        this.admissionYear = schoolYear;
    }

    public void setApprovedYn(String approvedYn) {
        this.approvedYn = approvedYn;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public void setMailKey(String mailKey) {
        this.mailKey = mailKey;
    }

    public void setMailAuth(String mailAuth) {
        this.mailAuth = mailAuth;
    }

    public void setWithdrawed_yn(String withdrawed_yn) {
        this.withdrawed = withdrawed_yn;
    }

    public void setWithdrawed_time(String withdrawed_time) {
        this.withdrawed_time = withdrawed_time;
    }

    public void setGithub_link(String github_link) {
        this.githubId = github_link;
    }
}
