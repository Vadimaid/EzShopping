package kg.ezshopping.ezshopping.entity;

import kg.ezshopping.ezshopping.date.TestRegistrationDateProvider;
import kg.ezshopping.ezshopping.types.UserType;

import java.util.List;

public class AppUserTestEntityProvider {
    public static final Long APP_USER_TEST_ID = 1L;
    public static final Long APP_USER_SECOND_TEST_ID = 2L;
    public static final String APP_USER_TEST_LOGIN = "test_login";
    public static final String APP_USER_TEST_RAW_PASSWORD = "test_password";
    public static final String TEST_WRONG_PASSWORD = "WRONG_PASSWORD";
    public static final String APP_USER_TEST_FIRSTNAME = "test_firstname";
    public static final String APP_USER_TEST_LASTNAME = "test_lastname";
    public static final UserType APP_USER_TEST_USER_TYPE = UserType.CLIENT;

    public static AppUser getAppUserTestEntity(String testUserPassword) {
        AppUser appUserTestEntity = new AppUser();
        appUserTestEntity.setId(APP_USER_TEST_ID);
        appUserTestEntity.setLogin(APP_USER_TEST_LOGIN);
        appUserTestEntity.setPassword(testUserPassword);
        appUserTestEntity.setFirstName(APP_USER_TEST_FIRSTNAME);
        appUserTestEntity.setLastName(APP_USER_TEST_LASTNAME);
        appUserTestEntity.setCreatedAt(TestRegistrationDateProvider.TEST_REGISTRATION_DATE);
        appUserTestEntity.setUserType(APP_USER_TEST_USER_TYPE);
        appUserTestEntity.setActive(Boolean.TRUE);
        return appUserTestEntity;
    }

    public static List<AppUser> getAppUserTestEntityList(String testUsersPassword) {
        AppUser firstUser = AppUserTestEntityProvider.getAppUserTestEntity(testUsersPassword);
        AppUser secondUser = AppUserTestEntityProvider.getAppUserTestEntity(testUsersPassword);
        secondUser.setId(APP_USER_SECOND_TEST_ID);
        return List.of(firstUser, secondUser);
    }
}
