package com.permansys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // RESTful API 配置
		http.securityMatcher("/getEmployee/**", "/loginCase1", "/checkLogin", "/insertCase1") // 指定 /api 開頭的路徑
        .csrf(csrf -> csrf.disable()) // 關閉 CSRF，適用於 REST API
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/getEmployee/**", "/loginCase1", "/checkLogin", "/insertCase1").permitAll() // API 無需身份驗證
        );
		http.authorizeHttpRequests(auth -> auth // 設定路徑的授權規則
				.requestMatchers("/css/**", "/js/**", "/images/**").permitAll() // 允許訪問靜態資源 (CSS, JS, Images)
				.requestMatchers("/public/**").permitAll() // 公共路徑，所有人可訪問
				.requestMatchers("/add/**").permitAll() // 公共路徑，所有人可訪問
				.requestMatchers("/delete/**").permitAll() // 公共路徑，所有人可訪問
				.requestMatchers("/update/**").permitAll() // 公共路徑，所有人可訪問
				.requestMatchers("/search/**").permitAll() // 公共路徑，所有人可訪問
				.anyRequest().authenticated() // 其他所有路徑需登入
		).formLogin(
				login -> login.loginPage("/home").permitAll().successHandler((request, response, authentication) -> { // 不進行導向
					response.getWriter().write("Login success!");
				}).failureHandler((request, response, exception) -> { // 回傳 401 狀態碼
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				}));
		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user = User.withUsername("user").password(passwordEncoder().encode("password")).roles("USER")
				.build();
		return new InMemoryUserDetailsManager(user);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
