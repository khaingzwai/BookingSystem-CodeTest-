package com.codetest.bookingsystem.repository;

import com.codetest.bookingsystem.model.ScheduleClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ScheduleClassRepository extends JpaRepository<ScheduleClass, Long> {

    List<ScheduleClass> findByClassDate(Date classDate);

    @Query("SELECT s FROM ScheduleClass s WHERE CURRENT_TIMESTAMP < s.classDate AND CURRENT_TIMESTAMP < s.endTime")
    List<ScheduleClass> findUpcomingClasses();
}
