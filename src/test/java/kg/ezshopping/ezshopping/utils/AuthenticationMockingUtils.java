package kg.ezshopping.ezshopping.utils;

import kg.ezshopping.ezshopping.entity.AppUser;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class AuthenticationMockingUtils {
    public static void mockAuthentication(AppUser appUser) {
        if(Objects.isNull(appUser)) {
            throw new IllegalArgumentException("Пользователь системы для загулшки аутентиыикации не может быть null!");
        }

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        Mockito
                .when(securityContext.getAuthentication())
                .thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
        Mockito
                .when(SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .thenReturn(appUser);
    }
}
