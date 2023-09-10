package kg.ezshopping.ezshopping.service;

import kg.ezshopping.ezshopping.dto.JwtResponseDto;
import kg.ezshopping.ezshopping.dto.UserCredentialsRequestDto;
import kg.ezshopping.ezshopping.exception.InvalidUserCredentialsException;
import kg.ezshopping.ezshopping.exception.WrongPasswordException;

public interface AuthenticationService {
    JwtResponseDto login(UserCredentialsRequestDto userCredentialsRequestDto)
            throws InvalidUserCredentialsException,
            WrongPasswordException;
}
