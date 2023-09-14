package kg.ezshopping.ezshopping.validator;

import kg.ezshopping.ezshopping.dto.AppUserRequestDto;
import kg.ezshopping.ezshopping.exception.InvalidUserCredentialsException;
import kg.ezshopping.ezshopping.exception.InvalidUserInfoException;
import kg.ezshopping.ezshopping.exception.InvalidUserTypeException;
import kg.ezshopping.ezshopping.exception.LoginAlreadyExistsException;
import kg.ezshopping.ezshopping.types.UserType;

public interface AppUserValidator {
    void validateAppUserRequestDto(AppUserRequestDto appUserRequestDto)
            throws InvalidUserCredentialsException,
            LoginAlreadyExistsException,
            InvalidUserInfoException,
            InvalidUserTypeException;

    void checkIfLoginIsNullOrEmpty(String login) throws InvalidUserCredentialsException;

    void checkIfLoginAlreadyExists(String login) throws LoginAlreadyExistsException;

    void checkIfPasswordIsNullOrEmpty(String password) throws InvalidUserCredentialsException;

    void checkIfFirstnameIsNullOrEmpty(String firstname) throws InvalidUserInfoException;

    void checkIfLastnameIsNullOrEmpty(String lastname) throws InvalidUserInfoException;
}
