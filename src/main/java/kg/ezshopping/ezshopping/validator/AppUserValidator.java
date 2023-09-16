package kg.ezshopping.ezshopping.validator;

import kg.ezshopping.ezshopping.exception.InvalidUserCredentialsException;
import kg.ezshopping.ezshopping.exception.InvalidUserInfoException;
import kg.ezshopping.ezshopping.exception.LoginAlreadyExistsException;

public interface AppUserValidator {

    void checkIfLoginIsNullOrEmpty(String login) throws InvalidUserCredentialsException;

    void checkIfLoginAlreadyExists(String login) throws LoginAlreadyExistsException;

    void checkIfPasswordIsNullOrEmpty(String password) throws InvalidUserCredentialsException;

    void checkIfFirstnameIsNullOrEmpty(String firstname) throws InvalidUserInfoException;

    void checkIfLastnameIsNullOrEmpty(String lastname) throws InvalidUserInfoException;
}
