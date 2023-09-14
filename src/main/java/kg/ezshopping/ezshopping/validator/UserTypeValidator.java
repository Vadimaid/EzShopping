package kg.ezshopping.ezshopping.validator;

import kg.ezshopping.ezshopping.exception.InvalidUserTypeException;
import kg.ezshopping.ezshopping.types.UserType;

public interface UserTypeValidator {
    void checkIfUserTypeIsNull(UserType userType) throws InvalidUserTypeException;
    void checkIfUserTypeExists(UserType userType) throws InvalidUserTypeException;
}
