package com.myapp.quizapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.myapp.quizapp.service.ThemeService;

@Configuration
public class SecurityConfig {

	private final UserDetailsService userDetailsService;
	private final ThemeService themeService;

	@Autowired
	public SecurityConfig(UserDetailsService userDetailsService, ThemeService themeService) {
		this.userDetailsService = userDetailsService;
		this.themeService = themeService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()) // CSRF を無効にする
				.authorizeHttpRequests(authorize -> authorize.requestMatchers("/quiz/set-questions")
						.access((authenticationSupplier, context) -> {
							// themeIdを取得し、認証が必要かどうかを判定
							String themeIdParam = context.getRequest().getParameter("themeId");
							Long themeId = themeIdParam != null ? Long.valueOf(themeIdParam) : null;
							boolean isAuthRequired = themeId != null && themeService.isAuthenticationRequired(themeId);

							// authenticationSupplierからAuthenticationを取得して判定
							Authentication authentication = authenticationSupplier.get();
							boolean isAuthenticated = authentication != null
									&& !(authentication instanceof AnonymousAuthenticationToken)
									&& authentication.isAuthenticated();

							// 認証が必要ない場合、または認証済みの場合はアクセスを許可する
							return new AuthorizationDecision(!isAuthRequired || isAuthenticated);
						}).anyRequest().permitAll())
				.formLogin(form -> form.loginPage("/quiz/login") // ログインページのURL
						.loginProcessingUrl("/quiz/login") // ログイン処理のURLを指定
						.successHandler(new CustomAuthenticationSuccessHandler()).permitAll())
				.logout(logout -> logout.permitAll());

		return http.build();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// @Bean アノテーションを削除して通常のメソッドにする
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		Logger logger = LogManager.getLogger();
//		logger.error("★ ★ ★SecurityConfig securityFilterChain Start");
//		http.csrf((csrf) -> csrf.disable())
//				.authorizeHttpRequests(authorize -> authorize.requestMatchers("/test").authenticated() // このURLには認証が必要
//						.anyRequest().permitAll())
//				.formLogin(form -> form.loginPage("/quiz/login") // ログインページのURL
//						.permitAll())
//				.logout(logout -> logout.permitAll());
//		return http.build();
//	}
}
