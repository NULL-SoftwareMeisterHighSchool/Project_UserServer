package com.project.backend.service.impl;

import com.project.backend.domain.User;
import com.project.backend.domain.UserForSecurity;
import com.project.backend.mapper.UserMapper;
import com.project.backend.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userMapper.getByEmail(username);
		System.out.println(user.toString());
		if (user != null) {
			return UserForSecurity.builder()
					.user(user)
					.build();
		} else {
			throw new UsernameNotFoundException(username);
		}
	}
}
