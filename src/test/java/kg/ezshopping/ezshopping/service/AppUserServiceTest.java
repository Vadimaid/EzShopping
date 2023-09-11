package kg.ezshopping.ezshopping.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import kg.ezshopping.ezshopping.date.TestRegistrationDateProvider;
import kg.ezshopping.ezshopping.dto.AppUserRequestDto;
import kg.ezshopping.ezshopping.dto.AppUserResponseDto;
import kg.ezshopping.ezshopping.dto.AppUserTestDtoProvider;
import kg.ezshopping.ezshopping.entity.AppUser;
import kg.ezshopping.ezshopping.entity.AppUserTestEntityProvider;
import kg.ezshopping.ezshopping.entity.QAppUser;
import kg.ezshopping.ezshopping.exception.AppUsersNotFoundException;
import kg.ezshopping.ezshopping.exception.IncorrectDateFiltersException;
import kg.ezshopping.ezshopping.exception.InvalidIdException;
import kg.ezshopping.ezshopping.repository.AppUserRepository;
import kg.ezshopping.ezshopping.utils.AuthenticationMockingUtils;
import kg.ezshopping.ezshopping.service.impl.AppUserServiceImpl;
import kg.ezshopping.ezshopping.validator.AppUserValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(value = MockitoExtension.class)
public class AppUserServiceTest {
    @Mock
    private AppUserValidator appUserValidator;
    @Mock
    private AppUserRepository appUserRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private AppUserService appUserService;

    @BeforeEach
    public void beforeEach() {
        this.appUserService = new AppUserServiceImpl(
                this.appUserValidator,
                this.appUserRepository,
                this.passwordEncoder
        );
    }

    @Test
    void testRegisterNewAppUser_OK() {
        try {
            AppUserRequestDto requestDto = AppUserTestDtoProvider.getAppUserTestRequestDto();
            Mockito
                    .doNothing()
                    .when(this.appUserValidator)
                    .validateAppUserRequestDto(Mockito.any());

            Mockito
                    .when(this.passwordEncoder.encode(Mockito.eq(requestDto.getPassword())))
                    .thenReturn(requestDto.getPassword());

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

            AppUserResponseDto result = this.appUserService.registerNewAppUser(requestDto);
            Assertions.assertEquals(appUser.getId(), result.getId());
            Assertions.assertEquals(requestDto.getLogin(), result.getLogin());
            Assertions.assertEquals(requestDto.getFirstName(), result.getFirstName());
            Assertions.assertEquals(requestDto.getLastName(), result.getLastName());
            Assertions.assertEquals(requestDto.getUserType(), result.getUserType());
            Assertions.assertEquals(appUser.getCreatedAt(), result.getCreatedAt());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testGetCurrentAuthorizedUser_OK() {
        try {
            AppUser authorizedUser = AppUserTestEntityProvider.
                    getAppUserTestEntity(AppUserTestEntityProvider.APP_USER_TEST_RAW_PASSWORD);
            AuthenticationMockingUtils.mockAuthentication(authorizedUser);

            AppUserResponseDto result = this.appUserService.getCurrentAuthorizedUser();
            Assertions.assertEquals(authorizedUser.getId(), result.getId());
            Assertions.assertEquals(authorizedUser.getLogin(), result.getLogin());
            Assertions.assertEquals(authorizedUser.getFirstName(), result.getFirstName());
            Assertions.assertEquals(authorizedUser.getLastName(), result.getLastName());
            Assertions.assertEquals(authorizedUser.getUserType(), result.getUserType());
            Assertions.assertEquals(authorizedUser.getCreatedAt(), result.getCreatedAt());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testGetAllAppUsers_OK() {
        try {
            AppUser appUser = AppUserTestEntityProvider.
                    getAppUserTestEntity(AppUserTestEntityProvider.APP_USER_TEST_RAW_PASSWORD);

            Mockito
                    .when(this.appUserRepository.findAll(Mockito.any(Predicate.class)))
                    .thenAnswer(invocationOnMock -> List.of(appUser));

            List<AppUserResponseDto> resultList = this.appUserService.getAllAppUsers(
                    AppUserTestEntityProvider.APP_USER_TEST_ID,
                    AppUserTestEntityProvider.APP_USER_TEST_LOGIN,
                    AppUserTestEntityProvider.APP_USER_TEST_USER_TYPE,
                    TestRegistrationDateProvider.TEST_START_DATE_FILTER,
                    TestRegistrationDateProvider.TEST_END_DATE_FILTER
            );

            Assertions.assertEquals(1, resultList.size());
            Assertions.assertEquals(appUser.getId(), resultList.get(0).getId());
            Assertions.assertEquals(appUser.getLogin(), resultList.get(0).getLogin());
            Assertions.assertEquals(appUser.getFirstName(), resultList.get(0).getFirstName());
            Assertions.assertEquals(appUser.getLastName(), resultList.get(0).getLastName());
            Assertions.assertEquals(appUser.getUserType(), resultList.get(0).getUserType());
            Assertions.assertEquals(appUser.getCreatedAt(), resultList.get(0).getCreatedAt());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testGetAllAppUsers_InvalidId() {
        Exception exception = Assertions.assertThrows(
                InvalidIdException.class,
                () -> this.appUserService.getAllAppUsers(
                        0L,
                        AppUserTestEntityProvider.APP_USER_TEST_LOGIN,
                        AppUserTestEntityProvider.APP_USER_TEST_USER_TYPE,
                        TestRegistrationDateProvider.TEST_START_DATE_FILTER,
                        TestRegistrationDateProvider.TEST_END_DATE_FILTER
                )
        );
        Assertions.assertEquals(
                "ID пользователя не может быть меньше 1!",
                exception.getMessage()
        );
    }

    @Test
    public void testGetAllAppUsers_IncorrectDateFilters() {
        Exception exception = Assertions.assertThrows(
                IncorrectDateFiltersException.class,
                () -> this.appUserService.getAllAppUsers(
                        AppUserTestEntityProvider.APP_USER_TEST_ID,
                        AppUserTestEntityProvider.APP_USER_TEST_LOGIN,
                        AppUserTestEntityProvider.APP_USER_TEST_USER_TYPE,
                        TestRegistrationDateProvider.TEST_END_DATE_FILTER,
                        TestRegistrationDateProvider.TEST_START_DATE_FILTER
                )
        );
        Assertions.assertEquals(
                "Неверно заданы фильтры поиска по дате! Начальная дата не может быть позже конечной!",
                exception.getMessage()
        );
    }

    @Test
    public void testGetAllAppUsers_AppUsersNotFound() {
        Mockito
                .when(this.appUserRepository.findAll(Mockito.any(Predicate.class)))
                .thenAnswer(invocationOnMock -> new ArrayList<>());

        Exception exception = Assertions.assertThrows(
                AppUsersNotFoundException.class,
                () -> this.appUserService.getAllAppUsers(
                        AppUserTestEntityProvider.APP_USER_TEST_ID,
                        AppUserTestEntityProvider.APP_USER_TEST_LOGIN,
                        AppUserTestEntityProvider.APP_USER_TEST_USER_TYPE,
                        TestRegistrationDateProvider.TEST_START_DATE_FILTER,
                        TestRegistrationDateProvider.TEST_END_DATE_FILTER
                )
        );
        Assertions.assertEquals(
                "По заданным параметрам не найдено ни одного пользователя!",
                exception.getMessage()
        );
    }
}