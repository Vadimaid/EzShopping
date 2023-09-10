package kg.ezshopping.ezshopping.controller.advice;

import kg.ezshopping.ezshopping.controller.v1.AuthenticationController;
import kg.ezshopping.ezshopping.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = { AuthenticationController.class })
public class AuthenticationControllerAdvice {

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(value = LoginAlreadyExistsException.class)
    public ResponseEntity<String> handleLoginAlreadyExistsException(LoginAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(value = InvalidUserCredentialsException.class)
    public ResponseEntity<String> handleInvalidUserCredentialsException(InvalidUserCredentialsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(value = WrongPasswordException.class)
    public ResponseEntity<String> handleWrongPasswordException(WrongPasswordException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(value = InvalidUserInfoException.class)
    public ResponseEntity<String> handleInvalidUserInfoException(InvalidUserInfoException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(value = InvalidUserTypeException.class)
    public ResponseEntity<String> handleInvalidUserTypeException(InvalidUserTypeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
