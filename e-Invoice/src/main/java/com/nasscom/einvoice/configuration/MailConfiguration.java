package com.nasscom.einvoice.configuration;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MailConfiguration {
	@Value("${spring.mail.host}")
	private String host;

	@Value("${spring.mail.port}")
	private String port;

	@Value("${spring.mail.username}")
	private String username;

	@Value("${spring.mail.password}")
	private String password;

	@Bean
	public JavaMailSender mailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");

		mailSender.setJavaMailProperties(props);

		mailSender.setHost(host);
		mailSender.setPort(Integer.parseInt(port));
		mailSender.setUsername(username);
		mailSender.setPassword(password);			
		return mailSender;
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		builder.additionalMessageConverters(new FormHttpMessageConverter(),new MappingJackson2HttpMessageConverter());
		return builder.build();
	}
}
