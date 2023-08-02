package com.example.springsecurityrest.payload.response;

import com.example.springsecurityrest.constants.StatusEnum;
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
    private StatusEnum status;
    private T data;

    public ResponseObject(String message, StatusEnum status) {
        this.message = message;
        this.status = status;
    }

    public ResponseObject(String message, StatusEnum status, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
