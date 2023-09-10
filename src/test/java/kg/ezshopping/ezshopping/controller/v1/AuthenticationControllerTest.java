package kg.ezshopping.ezshopping.controller.v1;

import kg.ezshopping.ezshopping.configuration.SecurityConfigurationTest;
import kg.ezshopping.ezshopping.date.TestRegistrationDateProvider;
import kg.ezshopping.ezshopping.dto.*;
import kg.ezshopping.ezshopping.entity.AppUser;
import kg.ezshopping.ezshopping.entity.AppUserTestEntityProvider;
import kg.ezshopping.ezshopping.exception.InvalidUserCredentialsException;
import kg.ezshopping.ezshopping.exception.InvalidUserInfoException;
import kg.ezshopping.ezshopping.exception.InvalidUserTypeException;
import kg.ezshopping.ezshopping.exception.LoginAlreadyExistsException;
import kg.ezshopping.ezshopping.initializer.YamlTestContextInitializer;
import kg.ezshopping.ezshopping.repository.AppUserRepository;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.net.URI;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(
        initializers = { YamlTestContextInitializer.class },
        classes = { SecurityConfigurationTest.class }
)
public class AuthenticationControllerTest {
    @MockBean
    private AppUserRepository appUserRepository;
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
                    .checkIfLoginIsNullOrEmpty(Mockito.eq(requestDto.getLogin()));
            Mockito
                    .doNothing()
                    .when(this.appUserValidator)
                    .checkIfPasswordIsNullOrEmpty(Mockito.eq(requestDto.getPassword()));

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
                    .checkIfLoginIsNullOrEmpty(Mockito.eq(requestDto.getLogin()));
            Mockito
                    .doNothing()
                    .when(this.appUserValidator)
                    .checkIfPasswordIsNullOrEmpty(Mockito.eq(requestDto.getPassword()));

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
                    .checkIfLoginIsNullOrEmpty(Mockito.eq(requestDto.getLogin()));
            Mockito
                    .doNothing()
                    .when(this.appUserValidator)
                    .checkIfPasswordIsNullOrEmpty(Mockito.eq(requestDto.getPassword()));

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
                    .checkIfLoginIsNullOrEmpty(Mockito.isNull());

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
                    .checkIfPasswordIsNullOrEmpty(Mockito.isNull());

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

    @Test
    public void testRegisterNewAppUser_OK() {
        try {
            AppUserRequestDto requestDto = AppUserTestDtoProvider.getAppUserTestRequestDto();
            Mockito
                    .doNothing()
                    .when(this.appUserValidator)
                    .validateAppUserRequestDto(Mockito.any());

            AppUser appUser = AppUserTestEntityProvider.getAppUserTestEntity(requestDto.getPassword());
            Mockito
                    .when(this.appUserRepository.save(Mockito.eq(appUser)))
                    .thenAnswer(
                            invocationOnMock -> {
                                AppUser persistedUser = (AppUser) invocationOnMock.getArguments()[0];
                                persistedUser.setCreatedAt(TestRegistrationDateProvider.TEST_REGISTRATION_DATE);
                                persistedUser.setActive(Boolean.TRUE);
                                persistedUser.setId(appUser.getId());
                                return persistedUser;
                            }
                    );

            URI uri = new URI("http://localhost:" + this.port + "/v1/auth/register");
            ResponseEntity<AppUserResponseDto> response = this.testRestTemplate.postForEntity(
                    uri,
                    requestDto,
                    AppUserResponseDto.class
            );

            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
            Assertions.assertTrue(Objects.nonNull(response.getBody()));

            Assertions.assertEquals(appUser.getId(), response.getBody().getId());
            Assertions.assertEquals(requestDto.getLogin(), response.getBody().getLogin());
            Assertions.assertEquals(requestDto.getFirstName(), response.getBody().getFirstName());
            Assertions.assertEquals(requestDto.getLastName(), response.getBody().getLastName());
            Assertions.assertEquals(requestDto.getUserType(), response.getBody().getUserType());
            Assertions.assertEquals(appUser.getCreatedAt(), response.getBody().getCreatedAt());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testRegisterNewAppUser_EmptyLogin() {
        try {
            AppUserRequestDto requestDto = AppUserTestDtoProvider.getAppUserTestRequestDto();
            requestDto.setLogin(null);
            Mockito
                    .doThrow(new InvalidUserCredentialsException("Логин пользователя не может быть пустым!"))
                    .when(this.appUserValidator)
                    .validateAppUserRequestDto(Mockito.eq(requestDto));

            URI uri = new URI("http://localhost:" + this.port + "/v1/auth/register");
            ResponseEntity<String> response = this.testRestTemplate.postForEntity(
                    uri,
                    requestDto,
                    String.class
            );

            Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            Assertions.assertTrue(Objects.nonNull(response.getBody()));
            Assertions.assertEquals("Логин пользователя не может быть пустым!", response.getBody());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testRegisterNewAppUser_LoginAlreadyExists() {
        try {
            AppUserRequestDto requestDto = AppUserTestDtoProvider.getAppUserTestRequestDto();
            requestDto.setPassword(null);
            Mockito
                    .doThrow(new LoginAlreadyExistsException("Пользователь с таким логином уже существует!"))
                    .when(this.appUserValidator)
                    .validateAppUserRequestDto(Mockito.eq(requestDto));

            URI uri = new URI("http://localhost:" + this.port + "/v1/auth/register");
            ResponseEntity<String> response = this.testRestTemplate.postForEntity(
                    uri,
                    requestDto,
                    String.class
            );

            Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            Assertions.assertTrue(Objects.nonNull(response.getBody()));
            Assertions.assertEquals("Пользователь с таким логином уже существует!", response.getBody());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testRegisterNewAppUser_EmptyPassword() {
        try {
            AppUserRequestDto requestDto = AppUserTestDtoProvider.getAppUserTestRequestDto();
            requestDto.setPassword(null);
            Mockito
                    .doThrow(new InvalidUserCredentialsException("Пароль пользователя не может быть пустым!"))
                    .when(this.appUserValidator)
                    .validateAppUserRequestDto(Mockito.eq(requestDto));

            URI uri = new URI("http://localhost:" + this.port + "/v1/auth/register");
            ResponseEntity<String> response = this.testRestTemplate.postForEntity(
                    uri,
                    requestDto,
                    String.class
            );

            Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            Assertions.assertTrue(Objects.nonNull(response.getBody()));
            Assertions.assertEquals("Пароль пользователя не может быть пустым!", response.getBody());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testRegisterNewAppUser_EmptyFirstname() {
        try {
            AppUserRequestDto requestDto = AppUserTestDtoProvider.getAppUserTestRequestDto();
            requestDto.setFirstName(null);
            Mockito
                    .doThrow(new InvalidUserInfoException("Имя пользователя не может быть пустым!"))
                    .when(this.appUserValidator)
                    .validateAppUserRequestDto(Mockito.eq(requestDto));

            URI uri = new URI("http://localhost:" + this.port + "/v1/auth/register");
            ResponseEntity<String> response = this.testRestTemplate.postForEntity(
                    uri,
                    requestDto,
                    String.class
            );

            Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            Assertions.assertTrue(Objects.nonNull(response.getBody()));
            Assertions.assertEquals("Имя пользователя не может быть пустым!", response.getBody());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testRegisterNewAppUser_EmptyLastname() {
        try {
            AppUserRequestDto requestDto = AppUserTestDtoProvider.getAppUserTestRequestDto();
            requestDto.setLastName(null);
            Mockito
                    .doThrow(new InvalidUserInfoException("Фамилия пользователя не может быть пустой!"))
                    .when(this.appUserValidator)
                    .validateAppUserRequestDto(Mockito.eq(requestDto));

            URI uri = new URI("http://localhost:" + this.port + "/v1/auth/register");
            ResponseEntity<String> response = this.testRestTemplate.postForEntity(
                    uri,
                    requestDto,
                    String.class
            );

            Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            Assertions.assertTrue(Objects.nonNull(response.getBody()));
            Assertions.assertEquals("Фамилия пользователя не может быть пустой!", response.getBody());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testRegisterNewAppUser_UserTypeIsEmpty() {
        try {
            AppUserRequestDto requestDto = AppUserTestDtoProvider.getAppUserTestRequestDto();
            requestDto.setUserType(null);
            Mockito
                    .doThrow(new InvalidUserTypeException("Тип пользователя не может быть пустым!"))
                    .when(this.appUserValidator)
                    .validateAppUserRequestDto(Mockito.eq(requestDto));

            URI uri = new URI("http://localhost:" + this.port + "/v1/auth/register");
            ResponseEntity<String> response = this.testRestTemplate.postForEntity(
                    uri,
                    requestDto,
                    String.class
            );

            Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            Assertions.assertTrue(Objects.nonNull(response.getBody()));
            Assertions.assertEquals("Тип пользователя не может быть пустым!", response.getBody());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }
}