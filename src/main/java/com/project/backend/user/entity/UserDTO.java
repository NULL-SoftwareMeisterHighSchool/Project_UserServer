package com.project.backend.user.entity;

public class UserDTO {
    private String email;
    private String name;
    private String userid;
    private String userType;
    private String password;
    private Integer schoolName;
    private Integer schoolYear;

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return this.email;
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


    public Integer getschoolName() {
        return this.schoolName;
    }

    public Integer getSchoolYear() {
        return this.schoolYear;
    }

}
