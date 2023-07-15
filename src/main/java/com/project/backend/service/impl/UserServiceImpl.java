package com.project.backend.service.impl;

import com.project.backend.domain.UserDTO;
import setting.common.domain.CommonException;
import com.project.backend.JwtTokenProvider;
import com.project.backend.domain.TokenInfo;
import com.project.backend.domain.User;
import com.project.backend.domain.UserForSecurity;
import com.project.backend.mapper.UserMapper;
import com.project.backend.service.MailService;
import com.project.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtTokenProvider jwtTokenProvider;
	private final SqlSession sqlSession;
	private final MailService mailService;

	@Override
	public User getAuthorizedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			throw new CommonException("not authorized");
		}

		Object principal = authentication.getPrincipal();
		String email = null;

		if (principal == null) {
			throw new CommonException("not authorized");
		}

		if (principal instanceof UserForSecurity) {
			email = ((UserForSecurity)authentication.getPrincipal()).getUsername();
		}

		User user = getwithemail(email);

		if (user != null) {
			if ("Y".equals(user.getDeletedYn()) || !"Y".equals(user.getApprovedYn())) {
				throw new CommonException("account expired");
			}

			user.setPassword(null);

			return user;
		} else {
			throw new CommonException("not authorized");
		}
	}


	@Override
	public User getwithidx(int idx) {
		return userMapper.get(idx);
	}

	@Override
	public User getwithemail(String email) {
		return userMapper.getByEmail(email);
	}

	@Override
	public List<User> getUserList(int offset, int count) {

		RowBounds rowBounds = new RowBounds(offset, count);
		return sqlSession.selectList("UserMapper.getUserList", null, rowBounds);
	}

	@Override
	public int getCount() {
		return userMapper.getCount();
	}
	
	@Override
	public User register(UserDTO userDTO) {
		User user = convertToUser(userDTO);
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		if ("G".equals(user.getUserType())) {
			user.setApprovedYn("Y");
		} else {
			user.setApprovedYn("N");
		}

		userMapper.register(user);
		return getwithidx(user.getUserIdx());
	}

	private User convertToUser(UserDTO userDTO) {
		User user = new User();
		user.setEmail(userDTO.getEmail());
		user.setName(userDTO.getName());
		user.setUserid(userDTO.getUserid());
		user.setUserType(userDTO.getUserType());
		user.setPassword(userDTO.getPassword());
		user.setSchoolName(userDTO.getschoolName());
		user.setSchoolYear(userDTO.getSchoolYear());

		return user;
	}





	@Override
	public User update(int idx, User updateuser) {
		User user = userMapper.get(idx);

		if ( user != null) {
			user.setEmail(updateuser.getEmail());
			user.setName(updateuser.getName());
			user.setUserid(updateuser.getUserid());
			user.setSchoolYear(updateuser.getSchoolYear());
		}
		return user;
	}

	@Override
	public void updateMailAuth(User user) {
		userMapper.updateAuthkey(user);
	}

	@Override
	public String withdraw(String email) {

		//id가 있는 유저인가? ( 탈퇴하지 않은 유저인가? )
		if ( true ) {
			//user 없다요
			return "유저가 없다";
		}
		int userIdx = getwithemail(email).getUserIdx();
		
		deleteuser(userIdx);

		return "성공함";
	}

	private void deleteuser(int userIdx) {
		userMapper.delete(userIdx);
	}
	
	@Override
	public void approve(int userIdx) {
		userMapper.updateApprove(userIdx);
	}

	@Override
	public void updateLastLoginTime(String email) {
		userMapper.updateLastLoginTime(email);
	}
	
	@Override
	public boolean changePassword(String oldPassword, String newPassword) {
		User user = getAuthorizedUser();
		if (user == null) {
			throw new CommonException("user not found");
		}
		
		if (!isPasswordMatched(user, oldPassword)) {
			throw new CommonException("password mismatch");
		}
		
		userMapper.updatePassword(user.getUserIdx(), passwordEncoder.encode(newPassword));

		return true;
	}

	@Override
	public boolean forgetpassword(String email) {
		User user = getwithemail(email);

		if ( user == null) {
			return false;
		}

		try {
			mailService.sendmail(email);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return true;
	}

	@Override
	public String finduseridwithemail(String email) {

		User user = getwithemail(email);
		return user.getUserid();
	}

	@Override
	public TokenInfo login(String email, String password) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

		updateLastLoginTime(email);

		return tokenInfo;
	}

	private boolean isPasswordMatched(User user, String oldPassword) {
		User saved = getwithidx(user.getUserIdx());
		
		return (passwordEncoder.matches(oldPassword, saved.getPassword()));
	}
}

