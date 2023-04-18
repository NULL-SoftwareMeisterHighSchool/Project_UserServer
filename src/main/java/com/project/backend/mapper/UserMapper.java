package com.project.backend.mapper;

import com.project.backend.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

	/**
	 * 주어진 idx을 가진 사용자 정보를 되돌린다.
	 * 
	 * @param idx 사용자 idx
	 * @return 사용자 정보
	 */
	public User get(@Param("idx") int idx);

	/**
	 * 주어진 이메일을 가진 사용자 정보를 되돌린다.
	 * 
	 * @param email email
	 * @return 사용자 정보
	 */
	public User getByEmail(@Param("email") String email);

	/**
	 * 사용자 목록을 되돌린다.
	 *
	 * @return user list
	 */
	public List<User> getUserList();

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
	public int register(User user);

	/**
	 * 깃허브 링크 넣는다
	 */
	public void setgithublink(String link);

	/**
	 * 사용자 정보를 갱신한다.
	 * 
	 * @param user 사용자 정보
	 */
	public void update(User user);

	/**
	 * 주어진 idx을 가진 회원을 삭제한다.
	 * 
	 * @param userIdx idx
	 */
	public void delete(@Param("idx") int userIdx);

	/**
	 * 주어진 email을 가진 회원을 승인한다
	 * 
	 * @param userIdx idx
	 */
	public void updateApprove(@Param("idx") int userIdx);

	/**
	 * 주어진 email 사용자의 인증키와 인증시간, 비밀번호를 변경 한다
	 *
	 * @param user 사용자 정보
	 */
	public void updateAuthkey(User user);

	/**
	 * 주어진 사용자의 마지막 로그인 시간을 현재 시간으로 설정한다.
	 * 
	 * @param email 회원 email
	 */
	public void updateLastLoginTime(@Param("email") String email);

	/**
	 * @param idx
	 * @param password
	 */
	public void updatePassword(@Param("idx") int idx,
			@Param("password") String password);

}
