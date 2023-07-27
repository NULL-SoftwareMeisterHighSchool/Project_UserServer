package com.project.backend.user.entity;

public class Useredit {
    private String userid;
    private String email;
    private String githubId;

    public void setUserid(String userid) {
        this.userid = userid;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setGithubId(String githubId) {
        this.githubId = githubId;
    }

    public String getUserid() {
        return githubId;
    }
    public String getEmail() {
        return email;
    }
    public String getGithubId() {
        return githubId;
    }
}
