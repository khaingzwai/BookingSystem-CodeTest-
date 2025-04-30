package com.codetest.bookingsystem.interfaces;

import com.codetest.bookingsystem.controller.SessionContext;
import com.codetest.bookingsystem.dto.BookingCancel;
import com.codetest.bookingsystem.dto.BookingDTO;
import com.codetest.bookingsystem.dto.BookingRequest;
import com.codetest.bookingsystem.dto.BookingResponse;

import java.util.List;

public interface BookingService {

	public BookingResponse addBooking(SessionContext sessionContext, BookingRequest bookingRequest);

	public BookingResponse cancelBooking(SessionContext sessionContext, BookingCancel bookingCancel);

	public List<BookingDTO> getAllBookingsByUserId(SessionContext sessionContext, Long userId);
}
