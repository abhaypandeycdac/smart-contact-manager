package com.smart.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Myconfig {

	@Bean
	public UserDetailsService getUserDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		System.out.println("in dao class");
		return daoAuthenticationProvider;
	}

//	@Bean
//	public AuthenticationProvider authenticationProvider() {
//	    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//	    provider.setUserDetailsService(getUserDetailsService());
//	    provider.setPasswordEncoder(passwordEncoder());
//         return provider;
//	}
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		System.out.println("jaya");
		return authConfig.getAuthenticationManager();
	}

	// @SuppressWarnings("removal")
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests().requestMatchers("/admin/**").hasRole("ADMIN").requestMatchers("/user/**")
				.hasRole("USER").requestMatchers("/**").permitAll().and().formLogin()
				.loginPage("/signin")
				.loginProcessingUrl("/dologin")
				.defaultSuccessUrl("/user/index")
				.and().csrf().disable();
		System.out.println("in security method");
		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		System.out.println("abhiraj");
		return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/webjars/**");
	}

}

