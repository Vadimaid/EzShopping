package kg.ezshopping.ezshopping.controller.v1;

import kg.ezshopping.ezshopping.configuration.SecurityConfigurationTest;
import kg.ezshopping.ezshopping.dto.AppUserTestDtoProvider;
import kg.ezshopping.ezshopping.dto.JwtResponseDto;
import kg.ezshopping.ezshopping.dto.UserCredentialsRequestDto;
import kg.ezshopping.ezshopping.entity.AppUser;
import kg.ezshopping.ezshopping.entity.AppUserTestEntityProvider;
import kg.ezshopping.ezshopping.exception.InvalidUserCredentialsException;
import kg.ezshopping.ezshopping.initializer.YamlTestContextInitializer;
import kg.ezshopping.ezshopping.security.JwtTokenHandler;
import kg.ezshopping.ezshopping.validator.AppUserValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.net.URI;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(
        initializers = { YamlTestContextInitializer.class },
        classes = { SecurityConfigurationTest.class }
)
@TestPropertySource(value = "classpath:test.yaml")
public class AuthenticationControllerTest {
    @MockBean
    private AppUserValidator appUserValidator;
    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenHandler jwtTokenHandler;
    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void testLogin_OK() {
        try {
            UserCredentialsRequestDto requestDto = AppUserTestDtoProvider.getUserCredentialsTestRequestDto();
            Mockito
                    .doNothing()
                    .when(this.appUserValidator)
                    .validateLogin(Mockito.eq(requestDto.getLogin()));
            Mockito
                    .doNothing()
                    .when(this.appUserValidator)
                    .validatePassword(Mockito.eq(requestDto.getPassword()));

            AppUser testAppUser = AppUserTestEntityProvider.getAppUserTestEntity(
                    this.passwordEncoder.encode(AppUserTestEntityProvider.APP_USER_TEST_RAW_PASSWORD)
            );
            Mockito
                    .when(this.userDetailsService.loadUserByUsername(requestDto.getLogin()))
                    .thenReturn(testAppUser);

            URI uri = new URI("http://localhost:" + this.port + "/v1/auth/login");
            ResponseEntity<JwtResponseDto> response = this.testRestTemplate.postForEntity(
                    uri,
                    requestDto,
                    JwtResponseDto.class
            );

            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
            Assertions.assertTrue(Objects.nonNull(response.getBody()));

            String loginFromJwt = this.jwtTokenHandler.getLoginFromJwtClaims(response.getBody().getJwtValue());
            Assertions.assertEquals(requestDto.getLogin(), loginFromJwt);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testLogin_WrongLogin() {
        try {
            UserCredentialsRequestDto requestDto = AppUserTestDtoProvider.getUserCredentialsTestRequestDto();
            Mockito
                    .doNothing()
                    .when(this.appUserValidator)
                    .validateLogin(Mockito.eq(requestDto.getLogin()));
            Mockito
                    .doNothing()
                    .when(this.appUserValidator)
                    .validatePassword(Mockito.eq(requestDto.getPassword()));

            Mockito
                    .when(this.userDetailsService.loadUserByUsername(requestDto.getLogin()))
                    .thenThrow(new UsernameNotFoundException("Пользователя с введенным логином не найдено в системе!"));

            URI uri = new URI("http://localhost:" + this.port + "/v1/auth/login");
            ResponseEntity<String> response = this.testRestTemplate.postForEntity(
                    uri,
                    requestDto,
                    String.class
            );

            Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            Assertions.assertTrue(Objects.nonNull(response.getBody()));
            Assertions.assertEquals("Пользователя с введенным логином не найдено в системе!", response.getBody());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testLogin_WrongPassword() {
        try {
            UserCredentialsRequestDto requestDto = AppUserTestDtoProvider.getUserCredentialsTestRequestDto();
            Mockito
                    .doNothing()
                    .when(this.appUserValidator)
                    .validateLogin(Mockito.eq(requestDto.getLogin()));
            Mockito
                    .doNothing()
                    .when(this.appUserValidator)
                    .validatePassword(Mockito.eq(requestDto.getPassword()));

            AppUser testAppUser = AppUserTestEntityProvider
                    .getAppUserTestEntity(AppUserTestEntityProvider.TEST_WRONG_PASSWORD);
            Mockito
                    .when(this.userDetailsService.loadUserByUsername(requestDto.getLogin()))
                    .thenReturn(testAppUser);

            URI uri = new URI("http://localhost:" + this.port + "/v1/auth/login");
            ResponseEntity<String> response = this.testRestTemplate.postForEntity(
                    uri,
                    requestDto,
                    String.class
            );

            Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            Assertions.assertTrue(Objects.nonNull(response.getBody()));
            Assertions.assertEquals("Введен неверный пароль!", response.getBody());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testLogin_LoginIsNull() {
        try {
            Mockito
                    .doThrow(new InvalidUserCredentialsException("Логин пользователя не может быть null или пустым!"))
                    .when(this.appUserValidator)
                    .validateLogin(Mockito.isNull());

            UserCredentialsRequestDto requestDto = AppUserTestDtoProvider.getUserCredentialsTestRequestDto();
            requestDto.setLogin(null);

            URI uri = new URI("http://localhost:" + this.port + "/v1/auth/login");
            ResponseEntity<String> response = this.testRestTemplate.postForEntity(
                    uri,
                    requestDto,
                    String.class
            );

            Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            Assertions.assertTrue(Objects.nonNull(response.getBody()));
            Assertions.assertEquals("Логин пользователя не может быть null или пустым!", response.getBody());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testLogin_PasswordIsNull() {
        try {
            Mockito
                    .doThrow(new InvalidUserCredentialsException("Пароль пользователя не может быть null или пустым!"))
                    .when(this.appUserValidator)
                    .validatePassword(Mockito.isNull());

            UserCredentialsRequestDto requestDto = AppUserTestDtoProvider.getUserCredentialsTestRequestDto();
            requestDto.setPassword(null);

            URI uri = new URI("http://localhost:" + this.port + "/v1/auth/login");
            ResponseEntity<String> response = this.testRestTemplate.postForEntity(
                    uri,
                    requestDto,
                    String.class
            );

            Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            Assertions.assertTrue(Objects.nonNull(response.getBody()));
            Assertions.assertEquals("Пароль пользователя не может быть null или пустым!", response.getBody());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

}