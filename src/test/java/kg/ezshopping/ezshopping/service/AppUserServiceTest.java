package kg.ezshopping.ezshopping.service;

import kg.ezshopping.ezshopping.date.TestRegistrationDateProvider;
import kg.ezshopping.ezshopping.dto.AppUserRequestDto;
import kg.ezshopping.ezshopping.dto.AppUserResponseDto;
import kg.ezshopping.ezshopping.dto.AppUserTestDtoProvider;
import kg.ezshopping.ezshopping.entity.AppUser;
import kg.ezshopping.ezshopping.entity.AppUserTestEntityProvider;
import kg.ezshopping.ezshopping.repository.AppUserRepository;
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

import static org.junit.jupiter.api.Assertions.*;

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
}