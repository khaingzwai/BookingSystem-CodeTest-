package com.codetest.bookingsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codetest.bookingsystem.enums.BookingStatus;
import com.codetest.bookingsystem.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE b.scheduleClassId = :scheduleClassId " +
            "AND b.bookingStatus = :bookingStatus " +
            "ORDER BY b.creationDate ASC")
    List<Booking> findTopPendingBooking(
            @Param("scheduleClassId") Long scheduleClassId,
            @Param("bookingStatus") BookingStatus bookingStatus,
            Pageable pageable);

    Optional<List<Booking>> findByUserId(Long userId);

    Booking findByBookingReferenceNo(String bookingReferenceNo);

   /* @Transactional
    @Modifying
    @Query("UPDATE Booking b SET b.bookingStatus = :newStatus " +
            "WHERE b.id IN (SELECT b.id FROM Booking b WHERE b.scheduleClassId = :scheduleClassId " +
            "AND b.bookingStatus = :bookingStatus ORDER BY b.creationDate ASC)")
    int findTopPendingBooking(
            @Param("scheduleClassId") Long scheduleClassId,
            @Param("bookingStatus") BookingStatus bookingStatus,
            Pageable pageable);*/
}
