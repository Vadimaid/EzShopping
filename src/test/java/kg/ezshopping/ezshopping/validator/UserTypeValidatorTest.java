package kg.ezshopping.ezshopping.validator;

import kg.ezshopping.ezshopping.entity.AppUserTestEntityProvider;
import kg.ezshopping.ezshopping.exception.InvalidUserTypeException;
import kg.ezshopping.ezshopping.types.UserType;
import kg.ezshopping.ezshopping.validator.impl.UserTypeValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(value = MockitoExtension.class)
public class UserTypeValidatorTest {
    private UserTypeValidator userTypeValidator;

    @BeforeEach
    public void beforeEach() {
        this.userTypeValidator = new UserTypeValidatorImpl();
    }

    @Test
    public void testCheckIfUserTypeIsNull_OK() {
        try {
            this.userTypeValidator.checkIfUserTypeIsNull(AppUserTestEntityProvider.APP_USER_TEST_USER_TYPE);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testCheckIfUserTypeIsNull_UserTypeIsNull() {
        Exception exception = Assertions.assertThrows(
                InvalidUserTypeException.class,
                () -> this.userTypeValidator.checkIfUserTypeIsNull(null)
        );
        Assertions.assertEquals(
                "Тип пользователя не может быть пустым!",
                exception.getMessage()
        );
    }

    @Test
    public void testCheckIfUserTypeExists_OK() {
        try {
            this.userTypeValidator.checkIfUserTypeIsNull(AppUserTestEntityProvider.APP_USER_TEST_USER_TYPE);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }
}