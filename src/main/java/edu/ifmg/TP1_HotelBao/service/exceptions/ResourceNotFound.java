package edu.ifmg.TP1_HotelBao.service.exceptions;

public class ResourceNotFound extends RuntimeException {

    public  ResourceNotFound() {
        super();
    }

    public ResourceNotFound(String message) {
        super(message);
    }
}
