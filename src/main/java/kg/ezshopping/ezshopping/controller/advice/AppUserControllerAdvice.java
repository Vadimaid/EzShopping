package kg.ezshopping.ezshopping.controller.advice;

import kg.ezshopping.ezshopping.controller.v1.AppUserController;
import kg.ezshopping.ezshopping.exception.AppUsersNotFoundException;
import kg.ezshopping.ezshopping.exception.IncorrectDateFiltersException;
import kg.ezshopping.ezshopping.exception.InvalidIdException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = AppUserController.class)
public class AppUserControllerAdvice {
    @ExceptionHandler(value = InvalidIdException.class)
    public ResponseEntity<String> handleInvalidIdException(InvalidIdException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(value = IncorrectDateFiltersException.class)
    public ResponseEntity<String> handleIncorrectDateFiltersException(IncorrectDateFiltersException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(value = AppUsersNotFoundException.class)
    public ResponseEntity<String> handleAppUsersNotFoundException(AppUsersNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
