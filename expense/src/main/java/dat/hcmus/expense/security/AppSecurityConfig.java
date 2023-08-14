package dat.hcmus.expense.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import dat.hcmus.expense.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {
	private final CustomUserDetailsService userDetailsService;
	private final JwtTokenFilter jwtTokenFilter;

	@Autowired
	public AppSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtTokenFilter jwtTokenFilter) {
		this.userDetailsService = customUserDetailsService;
		this.jwtTokenFilter = jwtTokenFilter;
	}

	// AuthenticationManager which will be used to authenticate the user.
	// We passed our CustomUserDetailsService object & password encoding object to
	// AuthenticationManager.
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder)
			throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
		return authenticationManagerBuilder.build();
	}

	@SuppressWarnings({ "removal", "deprecation" })
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().requestMatchers("/login", "/register").permitAll();
		http.authorizeRequests().requestMatchers("/profile", "/expenses", "/expense/**").hasAnyRole("USER", "ADMIN");
		http.authorizeRequests().requestMatchers("/users/**", "/user/**").hasAnyRole("ADMIN");
		http.authorizeRequests().anyRequest().authenticated();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
		// Here we have added our jwt filter before the
		// UsernamePasswordAuthenticationFilter.
		// Because we want every request to be authenticated before going through spring
		// security filter.
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
