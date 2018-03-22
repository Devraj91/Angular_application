package com.nasscom.einvoice.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nasscom.einvoice.entity.Schedule;
import com.nasscom.einvoice.entity.Schedule.Type;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long>{

	public Schedule findByDate(LocalDateTime dateTime);

	public Schedule findByType(Type type);
}
