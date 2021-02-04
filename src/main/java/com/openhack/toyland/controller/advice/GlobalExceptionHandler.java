package com.openhack.toyland.controller.advice;

import com.openhack.toyland.dto.ErrorResponse;
import com.openhack.toyland.exception.EntityNotFoundException;
import com.openhack.toyland.exception.InvalidRequestBodyException;
import com.openhack.toyland.exception.UnAuthorizedEventException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j(topic = "[global exception]")
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = HttpServerErrorException.InternalServerError.class)
	public ResponseEntity<?> handleInternalServerException() {
		log.error("Internal server exception");
		ErrorResponse response = new ErrorResponse("서버 내부 오류");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
		HttpRequestMethodNotSupportedException e) {
		log.error("Http Request Method Not Supported Exception" + e.getMessage());
		ErrorResponse response = new ErrorResponse("요청 메소드 형식이 잘못되었습니다");
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		log.error("Method Argument Not Valid Exception" + e.getMessage());
		ErrorResponse response = new ErrorResponse("요청 인수 형식이 잘못되었습니다");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
		MethodArgumentTypeMismatchException e) {
		log.error("Method Argument Type Mismatch Exception" + e.getMessage());
		ErrorResponse response = new ErrorResponse("요청 인수 형식이 잘못되었습니다");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(value = InvalidRequestBodyException.class)
	public ResponseEntity<?> handleInvalidRequestBodyException(InvalidRequestBodyException e) {
		log.error("Invalid Request Body Exception" + e.getMessage());
		ErrorResponse response = new ErrorResponse("요청 값이 잘못되었습니다");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleException(Exception e) {
		log.error(e.getMessage(), e);
		ErrorResponse response = new ErrorResponse("서버 내부 오류");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}

	@ExceptionHandler(value = UnAuthorizedEventException.class)
	public ResponseEntity<?> handleUnAuthorizedEventException() {
		log.error("Unauthorized event exception");
		ErrorResponse response = new ErrorResponse("패스워드 오류");
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}

	@ExceptionHandler(value = EntityNotFoundException.class)
	public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException e) {
		log.error("Entity Not Found exception" + e.getMessage());
		ErrorResponse response = new ErrorResponse(e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
}
