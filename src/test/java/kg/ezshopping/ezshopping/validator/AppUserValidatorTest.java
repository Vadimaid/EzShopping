package kg.ezshopping.ezshopping.validator;

import kg.ezshopping.ezshopping.dto.AppUserRequestDto;
import kg.ezshopping.ezshopping.dto.AppUserTestDtoProvider;
import kg.ezshopping.ezshopping.entity.AppUser;
import kg.ezshopping.ezshopping.entity.AppUserTestEntityProvider;
import kg.ezshopping.ezshopping.exception.InvalidUserCredentialsException;
import kg.ezshopping.ezshopping.exception.InvalidUserInfoException;
import kg.ezshopping.ezshopping.exception.LoginAlreadyExistsException;
import kg.ezshopping.ezshopping.repository.AppUserRepository;
import kg.ezshopping.ezshopping.validator.impl.AppUserValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(value = MockitoExtension.class)
public class AppUserValidatorTest {
    @Mock
    private AppUserRepository appUserRepository;
    @Mock
    private UserTypeValidator userTypeValidator;

    private AppUserValidator appUserValidator;

    @BeforeEach
    public void beforeEach() {
        this.appUserValidator = new AppUserValidatorImpl(this.appUserRepository, this.userTypeValidator);
    }

    @Test
    public void testValidateAppUserRequestDto_OK() {
        try {
            AppUserRequestDto requestDto = AppUserTestDtoProvider.getAppUserTestRequestDto();
            Mockito
                    .doNothing()
                    .when(this.userTypeValidator)
                    .checkIfUserTypeIsNull(Mockito.eq(requestDto.getUserType()));
            Mockito
                    .doNothing()
                    .when(this.userTypeValidator)
                    .checkIfUserTypeExists(Mockito.eq(requestDto.getUserType()));

            this.appUserValidator.validateAppUserRequestDto(requestDto);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testValidateAppUserRequestDto_NullRequestDto() {
        Exception exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> this.appUserValidator.validateAppUserRequestDto(null)
        );
        Assertions.assertEquals(
                "Данные пользователя не могут быть null!",
                exception.getMessage()
        );
    }

    @Test
    public void testCheckIfLoginIsNullOrEmpty_OK() {
        try {
            this.appUserValidator.checkIfLoginIsNullOrEmpty(AppUserTestEntityProvider.APP_USER_TEST_LOGIN);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testCheckIfLoginIsNullOrEmpty_LoginIsEmpty() {
        Exception exception = Assertions.assertThrows(
                InvalidUserCredentialsException.class,
                () -> this.appUserValidator.checkIfLoginIsNullOrEmpty("")
        );
        Assertions.assertEquals(
                "Логин пользователя не может быть пустым!",
                exception.getMessage()
        );
    }

    @Test
    public void testCheckIfLoginAlreadyExists_OK() {
        try {
            Mockito
                    .when(this.appUserRepository.getAppUserByLogin(
                            Mockito.eq(AppUserTestEntityProvider.APP_USER_TEST_LOGIN)
                    ))
                    .thenReturn(Optional.empty());
            this.appUserValidator.checkIfLoginAlreadyExists(AppUserTestEntityProvider.APP_USER_TEST_LOGIN);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testCheckIfLoginAlreadyExists_LoginAlreadyExists() {
        Mockito
                .when(this.appUserRepository.getAppUserByLogin(
                        Mockito.eq(AppUserTestEntityProvider.APP_USER_TEST_LOGIN)
                ))
                .thenAnswer(invocationOnMock -> Optional.of(new AppUser()));
        Exception exception = Assertions.assertThrows(
                LoginAlreadyExistsException.class,
                () -> this.appUserValidator.checkIfLoginAlreadyExists(AppUserTestEntityProvider.APP_USER_TEST_LOGIN)
        );
        Assertions.assertEquals(
                "Пользователь с таким логином уже существует!",
                exception.getMessage()
        );
    }

    @Test
    public void testCheckIfPasswordIsNullOrEmpty_OK() {
        try {
            this.appUserValidator.checkIfPasswordIsNullOrEmpty(AppUserTestEntityProvider.APP_USER_TEST_RAW_PASSWORD);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testCheckIfPasswordIsNullOrEmpty_PasswordIsEmpty() {
        Exception exception = Assertions.assertThrows(
                InvalidUserCredentialsException.class,
                () -> this.appUserValidator.checkIfPasswordIsNullOrEmpty("")
        );
        Assertions.assertEquals(
                "Пароль пользователя не может быть пустым!",
                exception.getMessage()
        );
    }

    @Test
    public void testCheckIfFirstnameIsNullOrEmpty_OK() {
        try {
            this.appUserValidator.checkIfFirstnameIsNullOrEmpty(AppUserTestEntityProvider.APP_USER_TEST_FIRSTNAME);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testCheckIfFirstnameIsNullOrEmpty_FirstnameIsEmpty() {
        Exception exception = Assertions.assertThrows(
                InvalidUserInfoException.class,
                () -> this.appUserValidator.checkIfFirstnameIsNullOrEmpty("")
        );
        Assertions.assertEquals(
                "Имя пользователя не может быть пустым!",
                exception.getMessage()
        );
    }

    @Test
    public void testCheckIfLastnameIsNullOrEmpty_OK() {
        try {
            this.appUserValidator.checkIfLastnameIsNullOrEmpty(AppUserTestEntityProvider.APP_USER_TEST_LASTNAME);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testCheckIfLastnameIsNullOrEmpty_LastnameIsEmpty() {
        Exception exception = Assertions.assertThrows(
                InvalidUserInfoException.class,
                () -> this.appUserValidator.checkIfLastnameIsNullOrEmpty("")
        );
        Assertions.assertEquals(
                "Фамилия пользователя не может быть пустой!",
                exception.getMessage()
        );
    }
}