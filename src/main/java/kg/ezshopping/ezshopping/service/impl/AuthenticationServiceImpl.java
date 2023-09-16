package kg.ezshopping.ezshopping.service.impl;

import kg.ezshopping.ezshopping.dto.AppUserResponseDto;
import kg.ezshopping.ezshopping.dto.JwtResponseDto;
import kg.ezshopping.ezshopping.dto.UserCredentialsRequestDto;
import kg.ezshopping.ezshopping.entity.AppUser;
import kg.ezshopping.ezshopping.exception.InvalidUserCredentialsException;
import kg.ezshopping.ezshopping.exception.WrongPasswordException;
import kg.ezshopping.ezshopping.security.JwtTokenHandler;
import kg.ezshopping.ezshopping.service.AuthenticationService;
import kg.ezshopping.ezshopping.validator.AppUserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.util.Objects;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AppUserValidator appUserValidator;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenHandler jwtTokenHandler;

    @Autowired
    public AuthenticationServiceImpl(
            AppUserValidator appUserValidator,
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder,
            JwtTokenHandler jwtTokenHandler
    ) {
        this.appUserValidator = appUserValidator;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenHandler = jwtTokenHandler;
    }

    @Override
    public JwtResponseDto login(UserCredentialsRequestDto userCredentialsRequestDto)
            throws InvalidUserCredentialsException,
            WrongPasswordException
    {
        if(Objects.isNull(userCredentialsRequestDto)) {
            throw new IllegalArgumentException("Учетные данные пользователя не могут быть null!");
        }

        String userLogin = userCredentialsRequestDto.getLogin();
        this.appUserValidator.checkIfLoginIsNullOrEmpty(userLogin);

        String userPassword = userCredentialsRequestDto.getPassword();
        this.appUserValidator.checkIfPasswordIsNullOrEmpty(userPassword);

        UserDetails authenticatedUser = this.userDetailsService.loadUserByUsername(userLogin);

        if(this.passwordEncoder.matches(userPassword, authenticatedUser.getPassword())){
            Authentication authentication =
                    UsernamePasswordAuthenticationToken.authenticated(
                            authenticatedUser,
                            null,
                            authenticatedUser.getAuthorities()
                    );
            AppUser client = (AppUser) authenticatedUser;
            JwtResponseDto response = new JwtResponseDto();
            response
                    .setJwtValue(this.jwtTokenHandler.generateJwt(authentication))
                    .setId(client.getId())
                    .setCreatedAt(client.getCreatedAt())
                    .setLogin(client.getLogin())
                    .setFullName(client.getUserFullName())
                    .setUserType(client.getUserType())
                    .setProfileImage(Base64Utils.encodeToString(client.getProfileImage()));
            return response;
        }
        throw new WrongPasswordException("Введен неверный пароль!");
    }
}
