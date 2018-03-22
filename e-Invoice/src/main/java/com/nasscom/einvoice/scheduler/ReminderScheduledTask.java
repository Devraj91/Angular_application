package com.nasscom.einvoice.scheduler;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.nasscom.einvoice.entity.Schedule;
import com.nasscom.einvoice.entity.Schedule.Type;
import com.nasscom.einvoice.service.EmailService;

@Component
public class ReminderScheduledTask implements SchedulerObjectInterface {

	private static final Logger logger = LoggerFactory.getLogger(ReminderScheduledTask.class);
	public static final String TASK = "Reminder";

	private ScheduledFuture<?> future;

	@Autowired
	private org.springframework.scheduling.TaskScheduler scheduler;

	@Autowired
	private EmailService emailService;

	@Autowired
	private ScheduledTasksUtility scheduledTasksUtility;

	@Override
	public void start() {
		try {
			Schedule reminderSchedule = scheduledTasksUtility.getActiveSchedule(Type.Reminder);
			if (reminderSchedule != null) {
				future = scheduler.schedule(new Runnable() {
					@Override
					public void run() {
						long startTime = System.currentTimeMillis();
						logger.debug("Processing Reminder Task");
						emailService.executeReminderMailTask();
						logger.debug("Reminder Task Processed  in (ms) => {}", (System.currentTimeMillis() - startTime));
					}
				}, new Trigger() {

					@Override
					public Date nextExecutionTime(TriggerContext triggerContext) {
						String cronExpression = "";
						Schedule updatedReminderSchedule = scheduledTasksUtility.getActiveSchedule(Type.Reminder);
						if (updatedReminderSchedule != null)
							cronExpression = scheduledTasksUtility.generateCronExpression(updatedReminderSchedule);
						logger.info("Reminder Task Trigger Loadded with cron expression =>{}", cronExpression);
						CronTrigger trigger = new CronTrigger(cronExpression);
						Date nextExec = trigger.nextExecutionTime(triggerContext);
						logger.info("Next Execution Date for Reminder Task is: " + nextExec.toString());
						return nextExec;
					}
				});
			} else {
				logger.warn("Reminder Schedule not present in DB thus Reminder Task not scheduled.");
			}
		} catch (Exception e) {
			logger.error("Exception encountered while starting Reminder Scheduled Task",e);
		}
	}

	@Override
	public void stop() {
		if(future!=null)
			future.cancel(false);
	}

}
