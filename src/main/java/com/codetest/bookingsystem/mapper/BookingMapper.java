package com.codetest.bookingsystem.mapper;


import com.codetest.bookingsystem.dto.BookingDTO;
import com.codetest.bookingsystem.model.Booking;

public class BookingMapper {

    public static BookingDTO mapToDTO(Booking booking) {
        BookingDTO dto = null;
        if (booking != null) {
            dto = new BookingDTO();
            dto.setId(booking.getId());
            dto.setBookingReferenceNo(booking.getBookingReferenceNo());
            dto.setNoOfSlots(booking.getNoOfSlots());
            dto.setBookingStatus(booking.getBookingStatus());
            dto.setBookingDate(booking.getBookingDate());
            dto.setPendingDate(booking.getPendingDate());
            dto.setRefundDate(booking.getRefundDate());
            dto.setCancelledDate(booking.getCancelledDate());
            dto.setScheduleClassId(booking.getScheduleClassId());
            dto.setExpiryDate(booking.getExpiryDate());
            dto.setCreationDate(booking.getCreationDate());
            dto.setScheduleClassId(booking.getScheduleClassId());
            dto.setUserId(booking.getUserId());
        }
        return dto;
    }

    public static Booking mapToEntity(BookingDTO bookingDTO) {
        Booking booking = null;
        if (bookingDTO != null) {
            booking = new Booking();
            booking.setId(bookingDTO.getId());
            booking.setBookingReferenceNo(bookingDTO.getBookingReferenceNo());
            booking.setNoOfSlots(bookingDTO.getNoOfSlots());
            booking.setBookingStatus(bookingDTO.getBookingStatus());
            booking.setBookingDate(bookingDTO.getBookingDate());
            booking.setPendingDate(bookingDTO.getPendingDate());
            booking.setRefundDate(bookingDTO.getRefundDate());
            booking.setCancelledDate(bookingDTO.getCancelledDate());
            booking.setScheduleClassId(bookingDTO.getScheduleClassId());
            booking.setExpiryDate(bookingDTO.getExpiryDate());
            booking.setCreationDate(bookingDTO.getCreationDate());
            booking.setScheduleClassId(bookingDTO.getScheduleClassId());
            booking.setUserId(bookingDTO.getUserId());
        }
        return booking;
    }
}
