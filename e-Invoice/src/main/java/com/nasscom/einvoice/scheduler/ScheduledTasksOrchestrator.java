package com.nasscom.einvoice.scheduler;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
public class ScheduledTasksOrchestrator implements SchedulingConfigurer {

	private static final Logger log = LoggerFactory.getLogger(ScheduledTasksOrchestrator.class);

	private static Map<String, SchedulerObjectInterface> schduledJobsMap = new HashMap<>();

	@Autowired
	InvoiceScheduledTask invoiceJob;
	
	@Autowired
	ReminderScheduledTask reminderJob;
	
	@Autowired
	AutoSyncMemberScheduledTask autoSyncMemberJob;

	
	@Bean
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(10);
		threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
		return threadPoolTaskScheduler;
	}
	
	@PostConstruct
	public void initScheduler() {
		schduledJobsMap.put(InvoiceScheduledTask.TASK, invoiceJob);
		 schduledJobsMap.put(ReminderScheduledTask.TASK, reminderJob);
		 schduledJobsMap.put(AutoSyncMemberScheduledTask.TASK, autoSyncMemberJob);
		//startAll();//at system startup starting all scheduled jobs/tasks
	}

	public void restart(String job) {
		stop(job);
		start(job);
	}

	public void stop(String job) {
		schduledJobsMap.get(job).stop();
	}

	public void start(String job) {
		schduledJobsMap.get(job).start();
	}

	public void startAll() {
		for (SchedulerObjectInterface schedulerObjectInterface : schduledJobsMap.values()) {
			schedulerObjectInterface.start();
		}
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		startAll();//at system startup starting all scheduled jobs/tasks
	}

}
