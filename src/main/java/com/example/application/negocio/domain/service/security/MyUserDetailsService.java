package com.example.application.negocio.domain.service.security;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.application.negocio.domain.entities.seguridad.Permission;
import com.example.application.negocio.domain.entities.seguridad.Role;
import com.example.application.negocio.domain.entities.seguridad.User;
import com.example.application.negocio.domain.repository.security.UserRepository;
@Service("userDetailsService")
@Transactional
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	/*
	 * @Autowired private LoginAttemptService loginAttemptService;
	 * 
	 * @Autowired private HttpServletRequest request;
	 */
	public MyUserDetailsService() {
		super();
	}

	// API

	@Override
	public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
		// final String ip = getClientIP();
		/*
		 * if (loginAttemptService.isBlocked(ip)) { throw new
		 * RuntimeException("blocked"); }
		 */

		try {
			final User user = userRepository.findByEmail(email);
			if (user == null) {
				throw new UsernameNotFoundException("No user found with username: " + email);
			}

			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
					user.getActivo(), true, true, true, getAuthorities(user.getRoles()));
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	// UTIL

	private Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {
		return getGrantedAuthorities(getPrivileges(roles));
	}

	private List<String> getPrivileges(final Collection<Role> roles) {
		final List<String> privileges = new ArrayList<>();
		final List<Permission> collection = new ArrayList<>();
		for (final Role role : roles) {
			collection.addAll(role.getPermisos() );
		}
		for (final Permission item : collection) {
			privileges.add(item.getDescripcion());
		}

		return privileges;
	}

	private List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges) {
		final List<GrantedAuthority> authorities = new ArrayList<>();
		for (final String privilege : privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}
		return authorities;
	}
	/*
	 * private String getClientIP() { final String xfHeader =
	 * request.getHeader("X-Forwarded-For"); if (xfHeader != null) { return
	 * xfHeader.split(",")[0]; } return request.getRemoteAddr(); }
	 */

}
