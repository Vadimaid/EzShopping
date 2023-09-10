package kg.ezshopping.ezshopping.validator;

import kg.ezshopping.ezshopping.entity.AppUserTestEntityProvider;
import kg.ezshopping.ezshopping.exception.InvalidUserCredentialsException;
import kg.ezshopping.ezshopping.repository.AppUserRepository;
import kg.ezshopping.ezshopping.validator.impl.AppUserValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(value = MockitoExtension.class)
public class AppUserValidatorTest {

    @Mock
    private AppUserRepository appUserRepository;

    private AppUserValidator appUserValidator;

    @BeforeEach
    public void beforeEach() {
        this.appUserValidator = new AppUserValidatorImpl(this.appUserRepository);
    }

    @Test
    public void testValidateLogin_OK() {
        try {
            this.appUserValidator.validateLogin(AppUserTestEntityProvider.APP_USER_TEST_LOGIN);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testValidateLogin_LoginIsEmpty() {
        Exception exception = Assertions.assertThrows(
                InvalidUserCredentialsException.class,
                () -> this.appUserValidator.validateLogin("")
        );
        Assertions.assertEquals(
                "Логин пользователя не может быть null или пустым!",
                exception.getMessage()
        );
    }

    @Test
    public void testValidatePassword_OK() {
        try {
            this.appUserValidator.validatePassword(AppUserTestEntityProvider.APP_USER_TEST_RAW_PASSWORD);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testValidatePassword_PasswordIsEmpty() {
        Exception exception = Assertions.assertThrows(
                InvalidUserCredentialsException.class,
                () -> this.appUserValidator.validatePassword("")
        );
        Assertions.assertEquals(
                "Пароль пользователя не может быть null или пустым!",
                exception.getMessage()
        );
    }
}