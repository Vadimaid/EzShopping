package kg.ezshopping.ezshopping.security;

import kg.ezshopping.ezshopping.entity.AppUser;
import kg.ezshopping.ezshopping.exception.InvalidUserCredentialsException;
import kg.ezshopping.ezshopping.exception.WrongPasswordException;
import org.springframework.http.HttpHeaders;

public interface TestAuthorizationHeaderProvider {
    HttpHeaders getAuthorizationHeaderForUser(AppUser appUser)
            throws InvalidUserCredentialsException,
            WrongPasswordException;
}
