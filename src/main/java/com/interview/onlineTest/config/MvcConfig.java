package com.interview.onlineTest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/home").setViewName("home");
		registry.addViewController("/").setViewName("home");
		registry.addViewController("/index").setViewName("index");
		registry.addViewController("/register_users").setViewName("register_users");
        registry.addViewController("/online_test").setViewName("online_test");
		registry.addViewController("/user_answers").setViewName("user_answers");
		registry.addViewController("/common_statistics").setViewName("common_statistics");
		registry.addViewController("/user_statistics").setViewName("user_statistics");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/admin").setViewName("admin");
		registry.addViewController("/error").setViewName("error");
		registry.addViewController("/registration").setViewName("registration");
		registry.addViewController("/403").setViewName("403");
		registry.addViewController("/send_new_query").setViewName("send_new_query");
		registry.addViewController("/send_new_query2").setViewName("send_new_query2");
	}

}
