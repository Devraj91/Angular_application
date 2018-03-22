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
import com.nasscom.einvoice.service.InvoiceService;

@Component
public class InvoiceScheduledTask implements SchedulerObjectInterface {

	private static final Logger logger = LoggerFactory.getLogger(InvoiceScheduledTask.class);
	public static final String TASK = "Invoice";

	private ScheduledFuture<?> future;

	@Autowired
	private org.springframework.scheduling.TaskScheduler scheduler;

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private ScheduledTasksUtility scheduledTasksUtility;

	@Override
	public void start() {
		try {
			Schedule invoiceSchedule = scheduledTasksUtility.getActiveSchedule(Type.Invoice);
			if (invoiceSchedule != null) {
				future = scheduler.schedule(new Runnable() {
					@Override
					public void run() {
						long startTime = System.currentTimeMillis();
						logger.debug("Processing Invoice Task");
						invoiceService.executeInvoiceTask();
						logger.debug("Invoice Task Processed  in (ms) => {}", (System.currentTimeMillis() - startTime));
					}
				}, new Trigger() {

					@Override
					public Date nextExecutionTime(TriggerContext triggerContext) {
						// String cron = cronConfig(Type.Invoice);
						String cronExpression = "";
						Schedule updatedInvoiceSchedule = scheduledTasksUtility.getActiveSchedule(Type.Invoice);
						if (updatedInvoiceSchedule != null)
							cronExpression = scheduledTasksUtility.generateCronExpression(updatedInvoiceSchedule);
						logger.info("Invoice Task Trigger Loadded with cron expression =>{}", cronExpression);
						CronTrigger trigger = new CronTrigger(cronExpression);
						Date nextExec = trigger.nextExecutionTime(triggerContext);
						logger.info("Next Execution Date for Invoice Task is: " + nextExec.toString());

						return nextExec;
					}
				});
			} else {
				logger.warn("Invoice Schedule not present in DB thus Invoice Task not scheduled.");
			}
		} catch (Exception e) {
			logger.error("Exception encountered while starting Invoice Scheduled Task",e);
		}
	}

	@Override
	public void stop() {
		if(future!=null)
			future.cancel(false);
	}

}
