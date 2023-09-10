package kg.ezshopping.ezshopping.dto;

import kg.ezshopping.ezshopping.entity.AppUserTestEntityProvider;

public class AppUserTestDtoProvider {
    public static AppUserRequestDto getAppUserTestRequestDto() {
        return new AppUserRequestDto()
                .setLogin(AppUserTestEntityProvider.APP_USER_TEST_LOGIN)
                .setPassword(AppUserTestEntityProvider.APP_USER_TEST_RAW_PASSWORD)
                .setFirstName(AppUserTestEntityProvider.APP_USER_TEST_FIRSTNAME)
                .setLastName(AppUserTestEntityProvider.APP_USER_TEST_LASTNAME)
                .setUserType(AppUserTestEntityProvider.APP_USER_TEST_USER_TYPE);
    }
}
