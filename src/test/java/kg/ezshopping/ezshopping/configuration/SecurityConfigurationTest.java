package kg.ezshopping.ezshopping.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@TestConfiguration
@TestPropertySource(value = "classpath:test.yaml")
public class SecurityConfigurationTest {
}