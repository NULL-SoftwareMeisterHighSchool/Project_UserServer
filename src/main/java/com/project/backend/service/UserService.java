package com.project.backend.service;

import batang.common.domain.RestResult;
import com.project.backend.domain.TokenInfo;
import com.project.backend.domain.User;

import java.util.List;

public interface UserService {
    /**
     * 현재 로그인한 사용자 정보를 되돌린다.
     *
     * @return
     */
    public User getAuthorizedUser();

    /**
     * 주어진 idx을 가진 사용자 정보를 되돌린다.
     *
     * @param idx 사용자 idx
     * @return 사용자 정보
     */
    public User getwithidx(int idx);

    /**
     * 주어진 이메일을 가진 사용자 정보를 되돌린다.
     *
     * @param email 사용자 이메일
     * @return 사용자 정보
     */
    public User getwithemail(String email);

    /**
     * 사용자 목록을 되돌린다.
     *
     * @param offset offset
     * @param count limit
     * @return
     */
    public List<User> getUserList(int offset, int count);

    /**
     * 사용자 수를 되돌린다.
     *
     * @return
     */
    public int getCount();

    /**
     * 사용자를 등록한다.
     *
     * @param user 사용자 정보
     * @return 등록된 사용자의 idx
     */
    public User register(User user);

    /**
     * 사용자 정보를 갱신한다.
     *
     * @param user 사용자 정보
     */
    public void update(int id, User user);

    /**
     * 주어진 idx을 가진 회원을 탈퇴 처리한다.
     *
     * @param userIdx idx
     */
    public void withdraw(int userIdx);

    /**
     * 주어진 email 사용자의 인증키와 인증시간, 비밀번호를 변경 한다
     *
     * @param user 유저정보
     */
    public void updateMailAuth(User user);

    /**
     * 주어진 idx을 가진 회원을 승인한다.
     *
     * @param userIdx idx
     */
    public void approve(int userIdx);


    /**
     * 주어진 사용자의 마지막 로그인 시간을 현재 시간으로 설정한다.
     *
     * @param email 회원 email
     */
    public void updateLastLoginTime(String email);

    /**
     * 사용자의 비밀번호를 변경한다.
     *
     * @param oldPassword
     * @param newPassword
     */
    public void changePassword(String oldPassword, String newPassword);

    /**
     * 비밀번호를 잊은 사용자의 비밀번호를 변경한다
     *
     * @return
     * @Param
     */
    RestResult.ResultCode forgetpassword(String email);

    /**
     * 주어진 정보로 사용자의 아이디를 찾아 되돌린다.
     *
     * @param email
     * @return
     */
    public String finduseridwithemail(String email);

    /**
     * 로그인 한다.
     *
     * @Param email 이메일
     * @Param password 비밀번호
     * @return
     */
    public TokenInfo login(String email, String password);

}
