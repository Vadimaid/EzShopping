package kg.ezshopping.ezshopping.service.impl;

import kg.ezshopping.ezshopping.dto.AppUserRequestDto;
import kg.ezshopping.ezshopping.dto.AppUserResponseDto;
import kg.ezshopping.ezshopping.entity.AppUser;
import kg.ezshopping.ezshopping.exception.InvalidUserCredentialsException;
import kg.ezshopping.ezshopping.exception.InvalidUserInfoException;
import kg.ezshopping.ezshopping.exception.InvalidUserTypeException;
import kg.ezshopping.ezshopping.exception.LoginAlreadyExistsException;
import kg.ezshopping.ezshopping.mapper.AppUserMapper;
import kg.ezshopping.ezshopping.repository.AppUserRepository;
import kg.ezshopping.ezshopping.service.AppUserService;
import kg.ezshopping.ezshopping.validator.AppUserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserServiceImpl implements AppUserService {
    private final AppUserValidator appUserValidator;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppUserServiceImpl(
            AppUserValidator appUserValidator,
            AppUserRepository appUserRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.appUserValidator = appUserValidator;
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUserResponseDto registerNewAppUser(AppUserRequestDto requestDto)
            throws InvalidUserInfoException,
            InvalidUserTypeException,
            LoginAlreadyExistsException,
            InvalidUserCredentialsException
    {
        this.appUserValidator.validateAppUserRequestDto(requestDto);
        AppUser appUser = AppUserMapper.mapToAppUserEntity(requestDto);

        appUser.setPassword(this.passwordEncoder.encode(appUser.getPassword()));

        appUser = this.appUserRepository.save(appUser);
        return AppUserMapper.mapEntityToAppUserResponseDto(appUser);
    }
}
