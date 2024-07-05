package com.booking.app.Booking.App.exception;

public class InternalServerException extends RuntimeException {
    public InternalServerException(String msg) {
        super(msg);
    }
}
