package ch.zmotions.linkit.config;

import ch.zmotions.linkit.config.auth.AjaxAwareAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.context.request.RequestContextListener;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

	@Bean
	SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.csrf(AbstractHttpConfigurer::disable)
				.redirectToHttps((req) -> {
					req.requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null);
				})
				.authorizeHttpRequests((req) -> {
					req
							.requestMatchers("/webjars/**", "/css/**", "/images/logo.png")
							.permitAll()
							.requestMatchers("/**")
							.hasAnyRole("ADMIN", "USER")
							.requestMatchers("/configurations/**")
							.hasRole("ADMIN")
							.requestMatchers(
									"/v1/api/**",
									"/v2/api-docs",
									"/v3/api-docs",
									"/v3/api-docs/**",
									"/swagger-resources",
									"/swagger-resources/**",
									"/configuration/ui",
									"/configuration/security",
									"/swagger-ui/**",
									"/webjars/**",
									"/swagger-ui.html"
							)
							.authenticated()
							.anyRequest()
							.authenticated();
				})
				.httpBasic((customizer) -> {
					customizer.authenticationEntryPoint(new AjaxAwareAuthenticationEntryPoint("/login"));
				})
				.formLogin((configurer) -> {
					configurer.defaultSuccessUrl("/checkLogin")
							.failureUrl("/login?error=true")
							.loginPage("/login")
							.permitAll();
					configurer.failureUrl("/login?error=true")
							.loginPage("/login")
							.permitAll();
					configurer.loginPage("/login")
							.permitAll();
				})
				.logout((configurer) -> {
					configurer.deleteCookies("JSESSIONID")
							.logoutSuccessUrl("/login?logout=true")
							.permitAll();
				})
				.sessionManagement((configurer) -> {
					configurer.maximumSessions(1)
							.sessionRegistry(sessionRegistry())
							.expiredUrl("/expired");
					configurer.invalidSessionUrl("/expired")
							.enableSessionUrlRewriting(false)
							.sessionFixation()
							.migrateSession();
				});
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailsService)
			throws Exception {
		AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
		builder.userDetailsService(userDetailsService)
				.passwordEncoder(bCryptPasswordEncoder);
		return builder.build();
	}

	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}

	@Bean
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}
}