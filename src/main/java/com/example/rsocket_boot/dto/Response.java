package com.example.rsocket_boot.dto;

import com.example.rsocket_boot.dto.error.ErrorEvent;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Response<T> {

    ErrorEvent errorResponse;
    T successResponse;

    public Response() {
    }

    public Response(ErrorEvent errorResponse) {
        this.errorResponse = errorResponse;
    }

    public Response(T successResponse) {
        this.successResponse = successResponse;
    }

    public boolean hasError() {
        return Objects.nonNull(errorResponse);
    }

    public static <T> Response<T> with(T t) {
        return new Response<>(t);
    }

    public static <T> Response<T> with(ErrorEvent errorResponse) {
        return new Response<>(errorResponse);
    }
}
