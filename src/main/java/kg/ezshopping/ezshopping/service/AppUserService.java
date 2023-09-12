package kg.ezshopping.ezshopping.service;

import kg.ezshopping.ezshopping.dto.AppUserRequestDto;
import kg.ezshopping.ezshopping.dto.AppUserResponseDto;
import kg.ezshopping.ezshopping.exception.*;
import kg.ezshopping.ezshopping.types.UserType;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface AppUserService {
    @Transactional
    AppUserResponseDto registerNewAppUser(AppUserRequestDto requestDto)
            throws InvalidUserInfoException,
            InvalidUserTypeException,
            LoginAlreadyExistsException,
            InvalidUserCredentialsException;

    AppUserResponseDto getCurrentAuthorizedUser();

    List<AppUserResponseDto> getAllAppUsers(
            Long appUserId,
            String login,
            UserType userType,
            LocalDateTime startDate,
            LocalDateTime endDate
    )
            throws InvalidIdException,
            IncorrectDateFiltersException,
            AppUsersNotFoundException;

    AppUserResponseDto updateUserInfo(
            String oldPassword,
            String newPassword,
            String newFirstName,
            String newLastName,
            UserType userType,
            Boolean isActive
    ) throws WrongPasswordException;
}
