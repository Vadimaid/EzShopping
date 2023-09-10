package kg.ezshopping.ezshopping.validator.impl;

import kg.ezshopping.ezshopping.exception.InvalidUserTypeException;
import kg.ezshopping.ezshopping.types.UserType;
import kg.ezshopping.ezshopping.validator.UserTypeValidator;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
public class UserTypeValidatorImpl implements UserTypeValidator {
    @Override
    public void checkIfUserTypeIsNull(UserType userType) throws InvalidUserTypeException {
        if(Objects.isNull(userType)) {
            throw new InvalidUserTypeException("Тип пользователя не может быть пустым!");
        }
    }

    @Override
    public void checkIfUserTypeExists(UserType userType) throws InvalidUserTypeException {
        if(!Arrays.asList(UserType.values()).contains(userType)) {
            throw new InvalidUserTypeException("Введенного типа пользователя не существует в системе!");
        }
    }
}
