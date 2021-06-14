package com.example.application.negocio.seguridad.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;

import com.example.application.negocio.domain.repository.security.UserRepository;
import com.example.application.negocio.domain.service.security.CustomAuthenticationProvider;
import com.example.application.negocio.domain.service.security.CustomRememberMeServices;
import com.example.application.negocio.seguridad.CustomRequestCache;
import com.example.application.negocio.seguridad.SecurityUtils;

@EnableWebSecurity
@Configuration
@Order(0)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final String LOGIN_PROCESSING_URL = "/login";
	private static final String LOGIN_FAILURE_URL = "/login?error";
	private static final String LOGIN_URL = "/login";
	private static final String LOGOUT_SUCCESS_URL = "/login";
	@Autowired
	private UserDetailsService userDetailsService;
	

	/*
	 * @Autowired private AuthenticationSuccessHandler
	 * myAuthenticationSuccessHandler;
	 */
	/*
	 * @Autowired private LogoutSuccessHandler myLogoutSuccessHandler;
	 * 
	 * @Autowired private AuthenticationFailureHandler authenticationFailureHandler;
	 */

	@Autowired
	private UserRepository userRepository;

	/**
	 * Require login to access internal pages and configure login form.
	 */

	/**
	 * Allows access to static resources, bypassing Spring security.
	 */
	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers(
				// Client-side JS

				// the standard favicon URI
				"/favicon.ico",

				// the robots exclusion standard
				"/robots.txt",

				// web application manifest
				"/manifest.webmanifest", "/sw.js", "/offline.html",

				// icons and images
				"/icons/**", "/images/**", "/styles/**",

				// (development mode) H2 debugging console
				"/h2-console/**");
	}

	/*
	 * @Bean
	 * 
	 * @Override public UserDetailsService userDetailsService() { // typical logged
	 * in user with some privileges UserDetails normalUser =
	 * User.withUsername("user") .password("{noop}user") .roles("User") .build();
	 * 
	 * // admin user with all privileges UserDetails adminUser =
	 * User.withUsername("admin") .password("{noop}admin") .roles("User", "Admin")
	 * .build();
	 * 
	 * return new InMemoryUserDetailsManager(normalUser, adminUser); }
	 */

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception { //

		return super.authenticationManagerBean();
	}

	@Bean
	public CustomRequestCache requestCache() { //

		return new CustomRequestCache();
	}

	/**
	 * Require login to access internal pages and configure login form.
	 */
	/*
	 * @Override protected void configure(HttpSecurity http) throws Exception { //
	 * Not using Spring CSRF here to be able to use plain HTML for the login page
	 * http.csrf().disable()
	 * 
	 * // Register our CustomRequestCache that saves unauthorized access attempts,
	 * so // the user is redirected after login. .requestCache().requestCache(new
	 * CustomRequestCache())
	 * 
	 * // Restrict access to our application. .and().authorizeRequests()
	 * 
	 * // Allow all flow internal requests.
	 * .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()
	 * 
	 * // Allow all requests by logged in users. .anyRequest().authenticated()
	 * 
	 * // Configure the login page. .and().formLogin().loginPage("/" +
	 * LoginView.ROUTE).permitAll() //
	 * 
	 * 
	 * 
	 * // Configure logout .and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL); }
	 */

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Not using Spring CSRF here to be able to use plain HTML for the login page
		http.csrf().disable()

				// Register our CustomRequestCache, that saves unauthorized access attempts, so
				// the user is redirected after login.
				.requestCache().requestCache(new CustomRequestCache())

				// Restrict access to our application.
				.and().authorizeRequests()

				// Allow all flow internal requests.
				.requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()

				// Vaadin Flow static resources
				// Now, those requests are handled by Spring Security's filter chain which
				// results in a fully initialized
				// security context. This is used in the upload's success listener to do
				// additional authentication checks for example.
				.antMatchers("/VAADIN/**").permitAll()

				// Allow all requests by logged in users.
				.anyRequest().authenticated()

				// Configure the login page.
				.and().formLogin().loginPage(LOGIN_URL).permitAll().loginProcessingUrl(LOGIN_PROCESSING_URL)
				.failureUrl(LOGIN_FAILURE_URL)

				// Configure logout
				.and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);
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

	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	@Bean
	public RememberMeServices rememberMeServices() {
		CustomRememberMeServices rememberMeServices = new CustomRememberMeServices("theKey", userDetailsService,
				new InMemoryTokenRepositoryImpl());
		return rememberMeServices;
	}

}
