package kg.ezshopping.ezshopping.validator.impl;

import kg.ezshopping.ezshopping.dto.AppUserRequestDto;
import kg.ezshopping.ezshopping.entity.AppUser;
import kg.ezshopping.ezshopping.exception.InvalidUserCredentialsException;
import kg.ezshopping.ezshopping.exception.InvalidUserInfoException;
import kg.ezshopping.ezshopping.exception.InvalidUserTypeException;
import kg.ezshopping.ezshopping.exception.LoginAlreadyExistsException;
import kg.ezshopping.ezshopping.repository.AppUserRepository;
import kg.ezshopping.ezshopping.validator.AppUserValidator;
import kg.ezshopping.ezshopping.validator.UserTypeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class AppUserValidatorImpl implements AppUserValidator {
    private final AppUserRepository appUserRepository;
    private final UserTypeValidator userTypeValidator;

    @Autowired
    public AppUserValidatorImpl(
            AppUserRepository appUserRepository,
            UserTypeValidator userTypeValidator) {
        this.appUserRepository = appUserRepository;
        this.userTypeValidator = userTypeValidator;
    }

    @Override
    public void checkIfLoginIsNullOrEmpty(String login) throws InvalidUserCredentialsException {
        if(Objects.isNull(login) || login.isEmpty()) {
            throw new InvalidUserCredentialsException("Логин пользователя не может быть пустым!");
        }
    }

    @Override
    public void checkIfLoginAlreadyExists(String login)
            throws LoginAlreadyExistsException
    {
        Optional<AppUser> appUserOptional = this.appUserRepository.getAppUserByLogin(login);
        if(appUserOptional.isPresent()) {
            throw new LoginAlreadyExistsException("Пользователь с таким логином уже существует!");
        }
    }

    @Override
    public void checkIfPasswordIsNullOrEmpty(String password) throws InvalidUserCredentialsException {
        if(Objects.isNull(password) || password.isEmpty()) {
            throw new InvalidUserCredentialsException("Пароль пользователя не может быть пустым!");
        }

    }

    @Override
    public void checkIfFirstnameIsNullOrEmpty(String firstname) throws InvalidUserInfoException {
        if(Objects.isNull(firstname) || firstname.isEmpty()) {
            throw new InvalidUserInfoException("Имя пользователя не может быть пустым!");
        }
    }

    @Override
    public void checkIfLastnameIsNullOrEmpty(String lastname) throws InvalidUserInfoException {
        if(Objects.isNull(lastname) || lastname.isEmpty()) {
            throw new InvalidUserInfoException("Фамилия пользователя не может быть пустой!");
        }
    }


}
