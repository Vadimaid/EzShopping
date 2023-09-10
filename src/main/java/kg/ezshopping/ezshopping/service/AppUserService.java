package kg.ezshopping.ezshopping.service;

import kg.ezshopping.ezshopping.dto.AppUserRequestDto;
import kg.ezshopping.ezshopping.dto.AppUserResponseDto;
import kg.ezshopping.ezshopping.exception.InvalidUserCredentialsException;
import kg.ezshopping.ezshopping.exception.InvalidUserInfoException;
import kg.ezshopping.ezshopping.exception.InvalidUserTypeException;
import kg.ezshopping.ezshopping.exception.LoginAlreadyExistsException;
import org.springframework.transaction.annotation.Transactional;

public interface AppUserService {
    @Transactional
    AppUserResponseDto registerNewAppUser(AppUserRequestDto requestDto)
            throws InvalidUserInfoException,
            InvalidUserTypeException,
            LoginAlreadyExistsException,
            InvalidUserCredentialsException;
}
