package com.project.backend.user.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserForSecurity implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	@NonNull
	private User user;
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(authority);

		if (user.getUserType() == "Z") {
			authority = new SimpleGrantedAuthority("ROLE_SUPERVISOR");
			authorities.add(authority);
		}

		return authorities;
	}

	@Override
	public String getPassword() {
		return ((user != null) ? user.getPassword() : null);
	}

	@Override
	public String getUsername() {
		return ((user != null) ? user.getEmail() : null);
	}

	@Override
	public boolean isAccountNonExpired() {
		if (user != null) {
			return ("N".equals(user.getDeletedYn()));
		}
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		if (user != null) {
			return ("Y".equals(user.getApprovedYn()));
		}
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
