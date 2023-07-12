package com.kkot.blog.handler;

import com.kkot.blog.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
    @ExceptionHandler(value = IllegalArgumentException.class) // 처리할 예외 지정
    public ResponseDTO<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseDTO<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}
