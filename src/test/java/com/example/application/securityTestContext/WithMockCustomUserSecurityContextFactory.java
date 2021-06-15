package com.example.application.securityTestContext;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import com.example.application.negocio.domain.entities.seguridad.User;
import com.example.application.negocio.domain.repository.security.UserRepository;
import com.example.application.negocio.domain.service.security.CustomAuthenticationProvider;

public class WithMockCustomUserSecurityContextFactory
    implements WithSecurityContextFactory<WithMockCustomUser> {
	

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserDetailsService userDetailsService;

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
       System.out.println(customUser.name()+" "+customUser.password());
    	
    	SecurityContext context = SecurityContextHolder.createEmptyContext();

        final User user = userRepository.findByEmail(customUser.username());
		if ((user == null)) {
			throw new BadCredentialsException("Invalid username or password");
		}
        
        Collection<? extends GrantedAuthority> s=new  ArrayList<GrantedAuthority>();
	//CustomUserDetails principal =new CustomUserDetails(customUser.name(), customUser.username());
       Authentication auth1 =          new UsernamePasswordAuthenticationToken (user, "t",s);
//       CustomAuthenticationProvider authProvider=   new CustomAuthenticationProvider();
//	   authProvider.setUserDetailsService(userDetailsService);
//		authProvider.setPasswordEncoder(new BCryptPasswordEncoder(12));
		//Authentication auth =      new CustomAuthenticationProvider().authenticate(auth1);
        context.setAuthentication(auth1);
        SecurityContextHolder.setContext(context);
      //  SecurityUtils.setContext(context);
        return context;
    }
    
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(12);
	}

	@Bean
	public DaoAuthenticationProvider authProvider() {
		final CustomAuthenticationProvider authProvider = new CustomAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(encoder());
		// authProvider.setPostAuthenticationChecks(differentLocationChecker);
		return authProvider;
	}

}