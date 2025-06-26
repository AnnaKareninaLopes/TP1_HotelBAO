package edu.ifmg.TP1_HotelBao.service.exceptions;

public class DatabaseException extends RuntimeException {

    public DatabaseException() {

    }

    public DatabaseException(String message) {
        super(message);
    }
}
