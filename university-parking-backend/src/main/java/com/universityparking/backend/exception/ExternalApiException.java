package com.universityparking.backend.exception;

public class ExternalApiException extends RuntimeException {

    public ExternalApiException(String apiName, String message) {
        super("The external API " + apiName + " has caused a problem : " + message);
    }
}
