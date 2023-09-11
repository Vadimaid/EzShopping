package kg.ezshopping.ezshopping.controller.v1;

import kg.ezshopping.ezshopping.configuration.SecurityConfigurationTest;
import kg.ezshopping.ezshopping.dto.JwtResponseDto;
import kg.ezshopping.ezshopping.dto.UserTypeResponseDto;
import kg.ezshopping.ezshopping.initializer.YamlTestContextInitializer;
import kg.ezshopping.ezshopping.utils.UserTypeAssertionsUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "spring.main.allow-bean-definition-overriding=true"
)
@ContextConfiguration(
        initializers = { YamlTestContextInitializer.class },
        classes = { SecurityConfigurationTest.class }
)
class UserTypeControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @Test
    void testGetAllUserTypes_OK() {
        // TODO: 11.09.2023 Дописать после добавления раграничения доступа по ролям
//        try {
//            URI uri = new URI("http://localhost:" + this.port + "/v1/user-types/all");
//            ResponseEntity<List<UserTypeResponseDto>> response = this.testRestTemplate.exchange(
//                    uri,
//                    HttpMethod.GET,
//                    new HttpEntity<>(null),
//                    new ParameterizedTypeReference<List<UserTypeResponseDto>>() {}
//            );
//
//            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//        } catch (Exception e) {
//            Assertions.fail(e.getMessage());
//        }
    }

    @Test
    void testGetNonSystemUserTypes_OK() {
        try {
            URI uri = new URI("http://localhost:" + this.port + "/v1/user-types/non-system");
            ResponseEntity<List<UserTypeResponseDto>> response = this.testRestTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    new HttpEntity<>(null),
                    new ParameterizedTypeReference<List<UserTypeResponseDto>>() {}
            );

            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
            Assertions.assertTrue(Objects.nonNull(response.getBody()));
            Assertions.assertTrue(UserTypeAssertionsUtils.assertThatNotContainsSystemType(response.getBody()));
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }
}