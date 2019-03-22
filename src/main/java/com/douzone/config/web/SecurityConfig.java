package com.douzone.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.douzone.security.AuthLoginInterceptor;
import com.douzone.security.AuthLogoutInterceptor;

@Configuration
@EnableWebMvc
public class SecurityConfig extends WebMvcConfigurerAdapter{
	
	// Interceptor
	@Bean
	public AuthLoginInterceptor authLoginInterceptor() {
		return new AuthLoginInterceptor();
	}

	@Bean
	public AuthLogoutInterceptor authLogoutInterceptor() {
		return new AuthLogoutInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.
		addInterceptor(authLoginInterceptor()).
		addPathPatterns("/user/auth");
		
		registry.
		addInterceptor(authLogoutInterceptor()).
		addPathPatterns("/user/logout");
	}
	
}
