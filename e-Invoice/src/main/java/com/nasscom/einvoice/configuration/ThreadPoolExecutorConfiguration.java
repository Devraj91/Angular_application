package com.nasscom.einvoice.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPoolExecutorConfiguration {
	private static final Logger logger = LoggerFactory.getLogger(ThreadPoolExecutorConfiguration.class);
	
	@Value("${mail.threads.corePoolSize}")
	private String corePoolSize;
	@Value("${mail.threads.maxPoolSize}")
	private String maxPoolSize;
	@Value("${mail.threads.queueCapacity}")
	private String queueCapacity;
	@Value("${mail.threads.keepAliveSeconds}")
	private String keepAliveSeconds;

	@Bean
	@Qualifier("EmailExecutor")
	public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
		logger.debug("EmailExecutor instanciated");
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(Integer.valueOf(corePoolSize));
		threadPoolTaskExecutor.setMaxPoolSize(Integer.valueOf(maxPoolSize));
		threadPoolTaskExecutor.setQueueCapacity(Integer.valueOf(queueCapacity));
		threadPoolTaskExecutor.setKeepAliveSeconds(Integer.valueOf(keepAliveSeconds));
		return threadPoolTaskExecutor;
	}
}
