package kg.ezshopping.ezshopping.security.impl;

import kg.ezshopping.ezshopping.configuration.SecurityConfigurationTest;
import kg.ezshopping.ezshopping.dto.JwtResponseDto;
import kg.ezshopping.ezshopping.dto.UserCredentialsRequestDto;
import kg.ezshopping.ezshopping.entity.AppUser;
import kg.ezshopping.ezshopping.exception.InvalidUserCredentialsException;
import kg.ezshopping.ezshopping.exception.WrongPasswordException;
import kg.ezshopping.ezshopping.security.TestAuthorizationHeaderProvider;
import kg.ezshopping.ezshopping.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnBean(value = SecurityConfigurationTest.class)
public class TestAuthorizationHeaderProviderImpl implements TestAuthorizationHeaderProvider {
    private final AuthenticationService authenticationService;

    @Autowired
    public TestAuthorizationHeaderProviderImpl(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public HttpHeaders getAuthorizationHeaderForUser(AppUser appUser)
            throws InvalidUserCredentialsException,
            WrongPasswordException
    {
        UserCredentialsRequestDto userCredentialsRequestDto = new UserCredentialsRequestDto();
        userCredentialsRequestDto.setLogin(appUser.getLogin());
        userCredentialsRequestDto.setPassword(appUser.getPassword());

        JwtResponseDto jwtResponseDto = this.authenticationService.login(userCredentialsRequestDto);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, this.formatJwt(jwtResponseDto.getJwtValue()));
        return httpHeaders;
    }

    private String formatJwt(String unformattedJwtValue) {
        return "Bearer " + unformattedJwtValue;
    }
}
