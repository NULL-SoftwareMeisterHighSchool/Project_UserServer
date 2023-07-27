package com.project.backend.user.service.impl;

import com.project.backend.JwtTokenProvider;
import com.project.backend.user.entity.TokenInfo;
import com.project.backend.user.entity.User;
import com.project.backend.user.entity.UserForSecurity;
import com.project.backend.user.repository.UserRepository;
import com.project.backend.user.service.MailService;
import com.project.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import setting.common.domain.CommonException;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtTokenProvider jwtTokenProvider;
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

			return user;
		} else {
			throw new CommonException("not authorized");
		}
	}


	@Override
	public User getwithidx(int idx) {
		return userRepository.getUserByUserIdx(idx);
	}

	@Override
	public User getwithemail(String email) {
		return userRepository.findUserByEmail(email);
	}

	@Override
	public List<User> getUserList(int offset, int count) {
		PageRequest pageRequest = PageRequest.of(offset, count);
		return userRepository.findUserByWithdrawed("N", pageRequest);
	}

	@Override
	public int getCount() {
		return userRepository.countByWithdrawedFalse();
	}
	
	@Override
	public User register(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		if ("G".equals(user.getUserType())) {
			user.setApprovedYn("Y");
		} else {
			user.setApprovedYn("N");
		}
		user.setRegisterTime(String.valueOf(LocalDateTime.now()));

		userRepository.save(user);

		return getwithidx(user.getUserIdx());
	}




	@Override
	public User update(int idx, User updateuser) {
		User user = userRepository.getUserByUserIdx(idx);

		if ( user != null) {
			user.setEmail(updateuser.getEmail());
			user.setName(updateuser.getName());
			user.setUserid(updateuser.getUserid());
			user.setSchoolYear(updateuser.getSchoolYear());
		}
		user.setUpdateTime(String.valueOf(LocalDateTime.now()));
		userRepository.save(user);
		return user;
	}


	@Override
	public boolean withdraw(String email) {

		//id가 있는 유저인가? ( 탈퇴하지 않은 유저인가? )
		if ( getwithemail(email) == null ) {
			//user 없다요
			return false;
		}
		int userIdx = getwithemail(email).getUserIdx();
		User user = userRepository.findUserByUserIdx(userIdx);
		user.setWithdrawed_time(String.valueOf(LocalDateTime.now()));
		deleteuser(userIdx);

		return true;
	}

	private void deleteuser(int userIdx) {
		User user = userRepository.findUserByUserIdx(userIdx);
		user.setDeletedTime(String.valueOf(LocalDateTime.now()));
		userRepository.delete(user);
	}
	
	@Override
	public void approve(int userIdx) {
		User user = userRepository.findUserByUserIdx(userIdx);
		user.setApprovedYn("Y");

		userRepository.save(user);
	}

	@Override
	public void updateLastLoginTime(String email) {
		User user = userRepository.findUserByEmail(email);
		user.setLastLoginTime(String.valueOf(LocalDateTime.now()));
		userRepository.save(user);
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
		user.setPassword(oldPassword);
		userRepository.save(user);
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

