package com.codetest.bookingsystem.interfaces;

import com.codetest.bookingsystem.controller.SessionContext;
import com.codetest.bookingsystem.dto.ScheduleClassDTO;

import java.util.List;

public interface ScheduleClassService {

	ScheduleClassDTO getScheduleClassById(SessionContext sessionContext, Long id);

	ScheduleClassDTO createScheduleClass(SessionContext sessionContext, ScheduleClassDTO scheduleClassDTO);

	List<ScheduleClassDTO> getAllScheduleClass(SessionContext sessionContext);

	ScheduleClassDTO updateScheduleClass(SessionContext sessionContext, Long id, ScheduleClassDTO scheduleClassDTO);

	List<ScheduleClassDTO> findUpcomingClasses(SessionContext sessionContext);
}
