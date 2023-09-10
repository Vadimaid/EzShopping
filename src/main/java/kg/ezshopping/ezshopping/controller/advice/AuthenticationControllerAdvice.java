package kg.ezshopping.ezshopping.controller.advice;

import kg.ezshopping.ezshopping.controller.v1.AuthenticationController;
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

}
