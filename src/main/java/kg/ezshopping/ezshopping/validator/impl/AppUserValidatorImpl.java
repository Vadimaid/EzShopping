package kg.ezshopping.ezshopping.validator.impl;

import kg.ezshopping.ezshopping.exception.InvalidUserCredentialsException;
import kg.ezshopping.ezshopping.repository.AppUserRepository;
import kg.ezshopping.ezshopping.validator.AppUserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AppUserValidatorImpl implements AppUserValidator {
    private final AppUserRepository appUserRepository;

    @Autowired
    public AppUserValidatorImpl(
            AppUserRepository appUserRepository
    ) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public void validateLogin(String login) throws InvalidUserCredentialsException {
        if(Objects.isNull(login) || login.isEmpty()) {
            throw new InvalidUserCredentialsException("Логин пользователя не может быть null или пустым!");
        }
    }

    @Override
    public void validatePassword(String password) throws InvalidUserCredentialsException {
        if(Objects.isNull(password) || password.isEmpty()) {
            throw new InvalidUserCredentialsException("Пароль пользователя не может быть null или пустым!");
        }

    }
}
