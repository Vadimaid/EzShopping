package kg.ezshopping.ezshopping.configuration;

import kg.ezshopping.ezshopping.adapter.InMemoryUserDetailsManagerAdapter;
import kg.ezshopping.ezshopping.entity.AppUserTestEntityProvider;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@TestConfiguration
@TestPropertySource(value = "classpath:test.yaml")
public class SecurityConfigurationTest {
    public static final Integer TEST_BCRYPT_STRENGTH = 4;

    @Bean
    public UserDetailsService appUserDetailsServiceImpl() {
        return new InMemoryUserDetailsManagerAdapter(
                AppUserTestEntityProvider.getAppUserTestEntity(
                        passwordEncoder().encode(AppUserTestEntityProvider.APP_USER_TEST_RAW_PASSWORD)
                )
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(TEST_BCRYPT_STRENGTH);
    }
}