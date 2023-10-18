package com.manage.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//为了静态资源不被拦截
		registry.addResourceHandler("/img/**").addResourceLocations("file:" + System.getProperty("user.dir") + "/src/main/resources/static/img/");
		registry.addResourceHandler("/avatar/**").addResourceLocations("file:" + System.getProperty("user.dir") + "/src/main/resources/static/avatar/");
	}
}
