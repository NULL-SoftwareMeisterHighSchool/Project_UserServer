package com.project.backend.domain;

public class UserDTO {
    private String email;
    private String nickname;
    private String name;
    private String userid;
    private String userType;
    private String password;
    private int grade;
    private Integer schoolName;
    private Integer schoolYear;

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return this.email;
    }

    public String getNickname() {
        return this.nickname;
    }

    public String getName() {
        return this.name;
    }

    public String getUserid() {
        return this.userid;
    }

    public String getUserType() {
        return this.userType;
    }

    public String getPassword() {
        return this.password;
    }

    public int getGrade() {
        return this.grade;
    }

    public Integer getschoolName() {
        return this.schoolName;
    }

    public Integer getSchoolYear() {
        return this.schoolYear;
    }

    public void setGrade(int s) {
        this.grade = s;
    }
}
