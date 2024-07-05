package com.booking.app.Booking.App.repository;

import com.booking.app.Booking.App.model.BookedRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<BookedRoom, Long> {

    List<BookedRoom> findByRoomId(Long RoomId);

    Optional<BookedRoom> findByBookingConfirmationCode(String confirmCode);
}
