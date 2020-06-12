package br.com.apirest.users.entrypoint;

import br.com.apirest.users.domain.exceptions.UserNotFoundException;
import br.com.apirest.users.entrypoint.dto.UserErrorDetailsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@ControllerAdvice
@Slf4j

public class CustomExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    public ResponseEntity<Object> userNotFound(UserNotFoundException ex, HttpServletRequest request) {
        log.warn(ex.getMessage(), ex);
        UserErrorDetailsDto userErrorDetailsDto = UserErrorDetailsDto
                .builder()
                .error(ex.getMessage())
                .timestamp(new Date())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userErrorDetailsDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Object> userErrorValidation(MethodArgumentNotValidException ex, WebRequest request) {
        log.warn(ex.getMessage(), ex);
        UserErrorDetailsDto userErrorDetailsDto = UserErrorDetailsDto
                .builder()
                .error(ex.getBindingResult().getFieldError().getDefaultMessage())
                .timestamp(new Date())
                .path(request.getDescription(false))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userErrorDetailsDto);
    }
}
