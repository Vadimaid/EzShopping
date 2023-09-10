package kg.ezshopping.ezshopping.validator;

import kg.ezshopping.ezshopping.exception.InvalidUserCredentialsException;

public interface AppUserValidator {
    void validateLogin(String login) throws InvalidUserCredentialsException;
    void validatePassword(String password) throws InvalidUserCredentialsException;
}
