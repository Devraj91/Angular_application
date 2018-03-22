package com.nasscom.einvoice.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.nasscom.einvoice.entity.Schedule;
import com.nasscom.einvoice.entity.Schedule.Type;
import com.nasscom.einvoice.repository.ScheduleRepository;

@Service
public class ScheduleService {

	@Autowired
	ScheduleRepository scheduleRepository;

	public Page<Schedule> findPaginated(int page, int size) {
		return scheduleRepository.findAll(new PageRequest(page, size));
	}

	public List<Schedule> findAll() {
		return scheduleRepository.findAll();
	}

	public Schedule find(Long id) {
		return scheduleRepository.findOne(id);
	}

	public Schedule createSchedule(Schedule schedule) {
		return scheduleRepository.save(schedule);
	}

	public void deleteSchedule(Long id) {
		scheduleRepository.delete(id);
	}

	public Schedule findByDate(LocalDateTime date) {
		return scheduleRepository.findByDate(date);
	}
	public Schedule findByType(Type type) {
		return scheduleRepository.findByType(type);
	}

}
