package com.booking.app.Booking.App.service;

import com.booking.app.Booking.App.model.BookedRoom;

import java.util.List;

public interface IBookingService {

    String saveBooking(Long roomId, BookedRoom bookingRequest);

    BookedRoom findByBookingConfirmationCode(String confirmCode);

    List<BookedRoom> getAllBookings();

    List<BookedRoom> getAllBookingByRoomId(long roomId);

    void cancelBooking(Long id);

}
