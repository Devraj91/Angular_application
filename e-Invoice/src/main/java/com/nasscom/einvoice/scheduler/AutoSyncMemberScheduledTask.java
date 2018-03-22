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
import com.nasscom.einvoice.service.MemberService;

@Component
public class AutoSyncMemberScheduledTask implements SchedulerObjectInterface {

	private static final Logger logger = LoggerFactory.getLogger(AutoSyncMemberScheduledTask.class);
	public static final String TASK = "Member";

	private ScheduledFuture<?> future;

	@Autowired
	private org.springframework.scheduling.TaskScheduler scheduler;

	@Autowired
	private MemberService memberService;

	@Autowired
	private ScheduledTasksUtility scheduledTasksUtility;

	@Override
	public void start() {
		try {
			Schedule memberSchedule = scheduledTasksUtility.getActiveSchedule(Type.Member);
			if (memberSchedule != null) {
				future = scheduler.schedule(new Runnable() {
					@Override
					public void run() {
						logger.info("Processing AutoMemberSync Task {}");
						long startTime = System.currentTimeMillis();
						memberService.autoSyncMember();
						logger.debug("AutoMemberSync Processed in (ms) => {}", (System.currentTimeMillis() - startTime));
					}
				}, new Trigger() {
					@Override
					public Date nextExecutionTime(TriggerContext triggerContext) {
						String cronExpression = "";
						Schedule updatedMemberSchedule = scheduledTasksUtility.getActiveSchedule(Type.Member);
						if (updatedMemberSchedule != null)
							cronExpression = scheduledTasksUtility.generateCronExpression(memberSchedule);
						logger.info("AutoMemberSync Trigger Loadded with cron expression =>{}", cronExpression);
						CronTrigger trigger = new CronTrigger(cronExpression);
						Date nextExec = trigger.nextExecutionTime(triggerContext);
						logger.info("Next Execution Date for Auto Member Sync Task is: "+ nextExec.toString());
						return nextExec;
					}
				});
			} else {
				logger.warn("Autosync Member Schedule not present in DB thus Autosync Member Task not scheduled.");
			}
		} catch (Exception e) {
			logger.error("Exception encountered while starting AutoMemberSync Scheduled Task",e);
		}
	}

	@Override
	public void stop() {
		if(future!=null)
			future.cancel(false);
	}

}
