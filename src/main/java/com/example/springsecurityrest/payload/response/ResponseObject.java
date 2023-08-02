package com.example.springsecurityrest.payload.response;

import com.example.springsecurityrest.constants.Status;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Data
@Getter
@Setter
@RequiredArgsConstructor
public class ResponseObject<T> {
    private String message;
    private Status status;
    private T data;

    public ResponseObject(String message, Status status) {
        this.message = message;
        this.status = status;
    }

    public ResponseObject(String message, Status status, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
