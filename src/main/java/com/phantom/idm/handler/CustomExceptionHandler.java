package com.phantom.idm.handler;

import com.phantom.idm.dto.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleDataNotFoundException(DataNotFoundException ex) {
        BaseResponse<Object> baseResponse = new BaseResponse<>();
        baseResponse.setStatus(HttpStatus.NOT_FOUND.value());
        baseResponse.setMessage(Objects.isNull(ex.getMessage()) ? "Data not found" : ex.getMessage());

        return new ResponseEntity<>(baseResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex) {
        BaseResponse<Object> baseResponse = new BaseResponse<>();
        baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        baseResponse.setMessage(Objects.isNull(ex.getMessage()) ? "Request failed" : ex.getMessage());

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex) {
        BaseResponse<Object> baseResponse = new BaseResponse<>();
        baseResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        baseResponse.setMessage(Objects.isNull(ex.getMessage()) ? "Token invalid" : ex.getMessage());
        return new ResponseEntity<>(baseResponse, HttpStatus.UNAUTHORIZED);
    }

}
