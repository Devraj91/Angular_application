package com.nasscom.einvoice.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nasscom.einvoice.domain.Message;
import com.nasscom.einvoice.entity.Schedule;
import com.nasscom.einvoice.entity.Schedule.Type;
import com.nasscom.einvoice.exception.NoRecordFoundException;
import com.nasscom.einvoice.exception.PageNotFoundException;
import com.nasscom.einvoice.scheduler.AutoSyncMemberScheduledTask;
import com.nasscom.einvoice.scheduler.InvoiceScheduledTask;
import com.nasscom.einvoice.scheduler.ReminderScheduledTask;
import com.nasscom.einvoice.scheduler.ScheduledTasksOrchestrator;
import com.nasscom.einvoice.service.ScheduleService;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

	private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);
	@Autowired
	ScheduleService scheduleService;
	
	@Autowired ScheduledTasksOrchestrator  scheduledTasksOrchestrator ;
	
	
	@RequestMapping(value = "/get", params = { "page", "size" }, method = RequestMethod.GET)
	public Page<Schedule> getSchedules(@RequestParam("page") int page, @RequestParam("size") int size)
			throws PageNotFoundException {
		Page<Schedule> resultPage = scheduleService.findPaginated(page, size);
		if (page > resultPage.getTotalPages()) {
			throw new PageNotFoundException("No-More-record-found");
		}
		logger.debug("Page : {} ", resultPage.getSize());
		return resultPage;
	}

	@RequestMapping(value = "/get/all", method = RequestMethod.GET)
	public List<Schedule> getAllSchedules(){
		return scheduleService.findAll();

	}

	@RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
	public Object getSchedule(@PathVariable("id") Long id) throws NoRecordFoundException {
		Schedule schedule = scheduleService.find(id);
		if(null==schedule)
	         throw new NoRecordFoundException("No Schedule found.");
		return schedule;
	}
	
	@RequestMapping(value = "/getByType/{type}", method = RequestMethod.GET)
	public Object getScheduleByType(@PathVariable("type") String type) throws NoRecordFoundException{
		Schedule schedule=scheduleService.findByType(Schedule.Type.valueOf(type));
		if(null==schedule)
         throw new NoRecordFoundException("No Schedule found.");
		return schedule;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> createSchedule(@RequestBody Schedule schedule) {
		Object obj = schedule(schedule);
		if(null!=obj && !"".equals(obj) && obj instanceof Schedule )//if update in DB is success//if create in DB is success
			updateScheduledCronTasks(schedule);		
		return new ResponseEntity<>(obj, HttpStatus.CREATED);
	}

	private Object schedule(Schedule schedule) {
		Object obj = "";
		try {
			if (schedule.getDate().compareTo(LocalDateTime.now()) == -1
					|| schedule.getDate().plusMinutes(5).compareTo(LocalDateTime.now()) ==-1)
				obj = new Message(Message.MsgCode.Warn, "Please Schedule today Onward date and time.");
			else
				obj = scheduleService.createSchedule(schedule);
		} catch (Exception ex) {
			if (ex.getCause() instanceof ConstraintViolationException)
				obj = new Message(Message.MsgCode.Warn, "Duplicate Entry Not allowed for same Schedule Type and Time.");
			else
				obj = new Message(Message.MsgCode.Error, ex.getMessage());
		}
		
		return obj;
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Object> updateSchedule(@RequestBody Schedule schedule) {
		Object obj = schedule(schedule);
		if(null!=obj && !"".equals(obj) && obj instanceof Schedule )//if update in DB is success
			updateScheduledCronTasks(schedule);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	private void updateScheduledCronTasks(Schedule schedule) {
		if(Type.Invoice.equals(schedule.getType())) {
			scheduledTasksOrchestrator.restart(InvoiceScheduledTask.TASK);
		}
		
		if(Type.Member.equals(schedule.getType())) {
			scheduledTasksOrchestrator.restart(AutoSyncMemberScheduledTask.TASK);
		}
		
		if(Type.Reminder.equals(schedule.getType())) {
			scheduledTasksOrchestrator.restart(ReminderScheduledTask.TASK);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteSchedule(@PathVariable("id") Long id) {
		Object obj = "";
		try {
			scheduleService.deleteSchedule(id);
			obj = new Message(Message.MsgCode.Info, "Schedule Deleted");
		} catch (Exception ex) {
			obj = new Message(Message.MsgCode.Error, ex.getMessage());
		}
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
}
