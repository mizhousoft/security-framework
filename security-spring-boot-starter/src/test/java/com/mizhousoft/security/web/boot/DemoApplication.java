package com.mizhousoft.security.web.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mizhousoft.commons.web.context.CommonContextLoaderListener;

import jakarta.servlet.ServletContextListener;

@ComponentScan("com.mizhousoft")
@EnableTransactionManagement
@SpringBootApplication
public class DemoApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public ServletListenerRegistrationBean<ServletContextListener> getServletContextListener()
	{
		ServletListenerRegistrationBean<ServletContextListener> registrationBean = new ServletListenerRegistrationBean<>();
		registrationBean.setListener(new CommonContextLoaderListener());
		registrationBean.setOrder(10);

		return registrationBean;
	}
}
