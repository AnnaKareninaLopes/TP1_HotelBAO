package edu.ifmg.TP1_HotelBao.service.exceptions;

public class RoomUnavailableException extends RuntimeException {
    public RoomUnavailableException(String message) {
        super(message);
    }
}
