package com.booking.app.Booking.App.controller;


import com.booking.app.Booking.App.exception.InvalidBookingRequestException;
import com.booking.app.Booking.App.exception.ResourceNotFoundException;
import com.booking.app.Booking.App.model.BookedRoom;
import com.booking.app.Booking.App.model.Room;
import com.booking.app.Booking.App.response.BookingResponse;
import com.booking.app.Booking.App.response.RoomResponse;
import com.booking.app.Booking.App.service.IBookingService;
import com.booking.app.Booking.App.service.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final IBookingService bookingService;
    private final IRoomService roomService;

    @GetMapping("/all-bookings")
    public ResponseEntity<List<BookingResponse>> getAllBookings() {
        List<BookedRoom> bookings = bookingService.getAllBookings();
        List<BookingResponse> responses = new ArrayList<>();
        for (BookedRoom booking : bookings) {
            BookingResponse bookingResponse = getBookingResponse(booking);
            responses.add(bookingResponse);
        }
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/confirm/{confirmCode}")
    public ResponseEntity<?> getBookingByConfirmationCode(@PathVariable("confirmCode") String confirmCode) {
        try {
            BookedRoom booking = bookingService.findByBookingConfirmationCode(confirmCode);
            BookingResponse response = getBookingResponse(booking);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException r) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(r.getMessage());
        }
    }

    @PostMapping("/room/{roomId}/booking")
    public ResponseEntity<?> saveBooking(@PathVariable Long roomId, @RequestBody BookedRoom bookingRequest) {
        try {
            String confirmationCode = bookingService.saveBooking(roomId, bookingRequest);
            return ResponseEntity.ok(
                    "Room booked successfully, Your confirmation code is: " + confirmationCode
            );
        } catch (InvalidBookingRequestException i) {
            return ResponseEntity.badRequest().body(i.getMessage());
        }
    }



    @DeleteMapping("/booking/{bookingId}/delete")
    public void cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
    }

    private BookingResponse getBookingResponse(BookedRoom booking) {
        Room theRoom = roomService.getRoomById(booking.getRoom().getId()).get();
        RoomResponse roomResponse = new RoomResponse(theRoom.getId(), theRoom.getRoomType(), theRoom.getRoomPrice());
        return new BookingResponse(
                booking.getBookingId(), booking.getCheckInDate(),
                booking.getCheckOutDate(), booking.getGuestFullName(),
                booking.getGuestEmail(), booking.getNumOfAdults(),
                booking.getNumOfChildren(), booking.getTotalNumOfGuest(),
                booking.getBookingConfirmationCode(), roomResponse);
    }

}
