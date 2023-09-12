package kg.ezshopping.ezshopping.service;

import kg.ezshopping.ezshopping.entity.AppUser;
import kg.ezshopping.ezshopping.entity.AppUserTestEntityProvider;
import kg.ezshopping.ezshopping.repository.AppUserRepository;
import kg.ezshopping.ezshopping.service.impl.AppUserDetailsServiceImpl;
import kg.ezshopping.ezshopping.validator.AppUserValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@ExtendWith(value = MockitoExtension.class)
public class AppUserDetailsServiceTest {
    @Mock
    private AppUserRepository appUserRepository;

    private UserDetailsService userDetailsService;

    @BeforeEach
    public void beforeEach() {
        this.userDetailsService = new AppUserDetailsServiceImpl(this.appUserRepository);
    }

    @Test
    public void testLoadUserByUsername_OK() {
        try {
            AppUser appUser = AppUserTestEntityProvider
                    .getAppUserTestEntity(AppUserTestEntityProvider.APP_USER_TEST_RAW_PASSWORD);

            Mockito
                    .when(this.appUserRepository.getAppUserByLogin(Mockito.eq(appUser.getLogin())))
                    .thenReturn(Optional.of(appUser));

            UserDetails result = this.userDetailsService.loadUserByUsername(appUser.getUsername());
            Assertions.assertInstanceOf(AppUser.class, result);
            Assertions.assertEquals(appUser.getUsername(), result.getUsername());
            Assertions.assertEquals(appUser.getPassword(), result.getPassword());
            Assertions.assertEquals(appUser.getAuthorities(), result.getAuthorities());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testLoadUserByUsername_UsernameNotFound() {
        Mockito
                .when(this.appUserRepository.getAppUserByLogin(
                        Mockito.eq(AppUserTestEntityProvider.APP_USER_TEST_LOGIN)
                ))
                .thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> this.userDetailsService.loadUserByUsername(AppUserTestEntityProvider.APP_USER_TEST_LOGIN)
        );
        Assertions.assertEquals(
                "Пользователя с введенным логином не найдено в системе!",
                exception.getMessage()
        );
    }

    @Test
    public void testLoadUserByUsername_UsernameIsEmpty() {
        Exception exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> this.userDetailsService.loadUserByUsername("")
        );
        Assertions.assertEquals(
                "Логин пользователя не может быть null или пустым!",
                exception.getMessage()
        );
    }
}