package com.booking.app.Booking.App.service;


import com.booking.app.Booking.App.exception.InvalidBookingRequestException;
import com.booking.app.Booking.App.exception.ResourceNotFoundException;
import com.booking.app.Booking.App.model.BookedRoom;
import com.booking.app.Booking.App.model.Room;
import com.booking.app.Booking.App.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService {

    @Autowired
    private final BookingRepository bookingRepository;
    private final RoomService roomService;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public String saveBooking(Long roomId, BookedRoom bookingRequest) {
        if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
            throw new InvalidBookingRequestException("Check-in day must be before check-out day.");
        }
        Room room = roomService.getRoomById(roomId).orElseThrow(() -> new InvalidBookingRequestException("Room not found."));
        List<BookedRoom> existingBookings = room.getBookings();
        boolean roomIsAvailable = roomIsAvailable(bookingRequest, existingBookings);
        if (roomIsAvailable) {
            room.addBooking(bookingRequest);
            bookingRepository.save(bookingRequest);
        } else {
            throw new InvalidBookingRequestException("Sorry, this room is not available for selected dates");
        }
        return bookingRequest.getBookingConfirmationCode();
    }


    @Override
    public List<BookedRoom> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public List<BookedRoom> getAllBookingByRoomId(long roomId) {
        return bookingRepository.findByRoomId(roomId);
    }

    @Override
    public void cancelBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public BookedRoom findByBookingConfirmationCode(String confirmCode) {
        return bookingRepository.findByBookingConfirmationCode(confirmCode)
                .orElseThrow(() -> new ResourceNotFoundException("No booking found with booking code : " + confirmCode));
    }

    private boolean roomIsAvailable(BookedRoom bookingRequest, List<BookedRoom> existingBookings) {
        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        datesOverlap(bookingRequest, existingBooking)
                );
    }

    private boolean datesOverlap(BookedRoom bookingRequest, BookedRoom existingBooking) {
        return !bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckInDate()) &&
                !bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckOutDate());
    }

}
