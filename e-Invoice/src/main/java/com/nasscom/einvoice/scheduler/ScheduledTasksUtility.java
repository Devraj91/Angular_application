package com.nasscom.einvoice.scheduler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nasscom.einvoice.entity.Schedule;
import com.nasscom.einvoice.entity.Schedule.RecurringType;
import com.nasscom.einvoice.entity.Schedule.Type;
import com.nasscom.einvoice.service.ScheduleService;

@Component
public class ScheduledTasksUtility {

	private static final Logger logger = LoggerFactory.getLogger(ScheduledTasksUtility.class);

	@Autowired
	private ScheduleService scheduleService;

	public Schedule getActiveSchedule(Type scheduleType) {
		Schedule schedule = null;
		try {
			schedule = scheduleService.findByType(scheduleType);
			if (schedule != null) {
				logger.info("Schedule found in DB for scheduleType: " + scheduleType + " \n isScheduled: ");
			}
		} catch (Exception e) {
			logger.error("Exception occured. Schedule not found for scheduleType: " + scheduleType, e);
		}
		logger.info("Active Schedule for " + scheduleType + " is: " + " \n  Schedule: " + schedule);
		return schedule;
	}

	/**
	 * Generates the cron expression dynamically based on schedule stored in DB.
	 * Sample Cron Expression fields format : <second> <minute> <hour>
	 * <day-of-month> <month> <day-of-week> <year> <command> From these, <year>
	 * field is optional
	 * 
	 * @param scheduleType
	 * @return dynamicCronExpression
	 */
	public String generateCronExpression(Schedule schedule) {

		String dynamicCronExpression = "";
		if (schedule != null) {
			LocalDateTime scheduleDateTime = schedule.getDate();
			LocalTime time = scheduleDateTime.toLocalTime();
			LocalDate date = scheduleDateTime.toLocalDate();

			boolean isRecurring = schedule.getIsRecurring();
			RecurringType scheduleRecurrenceType = schedule.getRecurringType();// Daily Weekly Monthly

			// <second> <minute> <hour> <day-of-month> <month> <day-of-week> <year>
			// <command>
			int second = time.getSecond();
			int minute = time.getMinute();
			int hour = time.getHour();

			int dayOfMonth = date.getDayOfMonth();// 1-31
			int month = date.getMonthValue();// 1-12
			int dayOfWeek = date.getDayOfWeek().getValue();// (0 - 6) (Sunday to Saturday;7 is also Sunday on some
															// systems)
			int year = date.getYear();

			if (isRecurring) {// cronExpression will support Fixed +Recurring schedule
				logger.info("Schedule isRecurring " + isRecurring + " \n Schedule: " + schedule);
				logger.info("ScheduleRecurrenceType:  " + scheduleRecurrenceType);
				switch (scheduleRecurrenceType) {
				case Daily:
					// On all months and years , considered only time,task runs daily at
					// scheduledTime
					// <second> <minute> <hour> <day-of-month>* <month>* <day-of-week>* <year>*
					// <command> <year> field is optional
					dynamicCronExpression = second + " " + minute + " " + hour + " " + "*" + " " + "*" + " " + "*";
					break;
				case Weekly:
					// On all months and years ,task runs every day-Of-Week(every mon/Tue/Wed etc..)
					// at scheduledTime
					dynamicCronExpression = second + " " + minute + " " + hour + " " + "*" + " " + "*" + " "
							+ dayOfWeek;
					break;
				case Monthly:
					// Consider only day-Of-Month,task runs every Month at scheduledTime
					dynamicCronExpression = second + " " + minute + " " + hour + " " + dayOfMonth + " " + "*" + " "
							+ "*";
					break;
				default:
					logger.error("Supported Schedule RecurrenceType--> " + scheduleRecurrenceType);
				}

				logger.info("Recurring+Fixed dynamicCronExpression as per DB Schedule " + dynamicCronExpression);
				return dynamicCronExpression;
			} else {// only Fixed cron Expression
				logger.info("onlyFixed.Recurring is: " + isRecurring + " \n Schedule: " + schedule);
				logger.info("Fixed dynamicCronExpression as per DB Schedule " + dynamicCronExpression);
				dynamicCronExpression = second + " " + minute + " " + hour + " " + dayOfMonth + " " + month + " "
						+ dayOfWeek;
			}
		} else {
			logger.warn("Schedule not found in DB ");
		}

		return dynamicCronExpression;
	}
}
