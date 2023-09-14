package kg.ezshopping.ezshopping.controller.v1;

import com.querydsl.core.types.Predicate;
import kg.ezshopping.ezshopping.configuration.SecurityConfigurationTest;
import kg.ezshopping.ezshopping.date.TestRegistrationDateFiltersProvider;
import kg.ezshopping.ezshopping.dto.AppUserResponseDto;
import kg.ezshopping.ezshopping.entity.AppUser;
import kg.ezshopping.ezshopping.entity.AppUserTestEntityProvider;
import kg.ezshopping.ezshopping.initializer.YamlTestContextInitializer;
import kg.ezshopping.ezshopping.repository.AppUserRepository;
import kg.ezshopping.ezshopping.security.TestAuthorizationHeaderProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "spring.main.allow-bean-definition-overriding=true"
)
@ContextConfiguration(
        initializers = { YamlTestContextInitializer.class },
        classes = { SecurityConfigurationTest.class }
)
public class AppUserControllerTest {
    @MockBean
    private AppUserRepository appUserRepository;

    @Autowired
    private TestAuthorizationHeaderProvider testAuthorizationHeaderProvider;
    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void testGetCurrentAuthorizedUser_OK() {
        try {
            AppUser testUser = AppUserTestEntityProvider
                    .getAppUserTestEntity(AppUserTestEntityProvider.APP_USER_TEST_RAW_PASSWORD);

            HttpHeaders authorizationHeader =
                    this.testAuthorizationHeaderProvider.getAuthorizationHeaderForUser(testUser);
            URI uri = new URI("http://localhost:" + this.port + "/v1/users/current");

            ResponseEntity<AppUserResponseDto> response = this.testRestTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    new HttpEntity<>(authorizationHeader),
                    AppUserResponseDto.class
            );

            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
            Assertions.assertTrue(Objects.nonNull(response.getBody()));


        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testGetCurrentAuthorizedUser_401Unauthorized() {
        try {
            URI uri = new URI("http://localhost:" + this.port + "/v1/users/current");
            ResponseEntity<AppUserResponseDto> response = this.testRestTemplate.getForEntity(
                    uri,
                    AppUserResponseDto.class
            );
            Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testGetAllAppUsers_OK() {
        try {
            URI uri = new URI("http://localhost:" + this.port + "/v1/users");
            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                    .fromUri(uri)
                    .queryParam("id", AppUserTestEntityProvider.APP_USER_TEST_ID)
                    .queryParam("login", AppUserTestEntityProvider.APP_USER_TEST_LOGIN)
                    .queryParam("userType", AppUserTestEntityProvider.APP_USER_TEST_USER_TYPE)
                    .queryParam("startDate", TestRegistrationDateFiltersProvider.TEST_START_DATE_FILTER)
                    .queryParam("endDate", TestRegistrationDateFiltersProvider.TEST_END_DATE_FILTER);

            AppUser testUser = AppUserTestEntityProvider
                    .getAppUserTestEntity(AppUserTestEntityProvider.APP_USER_TEST_RAW_PASSWORD);
            HttpHeaders authorizationHeader =
                    this.testAuthorizationHeaderProvider.getAuthorizationHeaderForUser(testUser);

            Mockito
                    .when(this.appUserRepository.findAll(Mockito.any(Predicate.class)))
                    .thenAnswer(invocationOnMock -> List.of(testUser));

            ResponseEntity<List<AppUserResponseDto>> response = this.testRestTemplate.exchange(
                    uriComponentsBuilder.build().encode().toUri(),
                    HttpMethod.GET,
                    new HttpEntity<>(authorizationHeader),
                    new ParameterizedTypeReference<List<AppUserResponseDto>>() {}
            );

            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
            Assertions.assertTrue(Objects.nonNull(response.getBody()));
            Assertions.assertEquals(1, response.getBody().size());
            Assertions.assertEquals(testUser.getId(), response.getBody().get(0).getId());
            Assertions.assertEquals(testUser.getLogin(), response.getBody().get(0).getLogin());
            Assertions.assertEquals(testUser.getFirstName(), response.getBody().get(0).getFirstName());
            Assertions.assertEquals(testUser.getLastName(), response.getBody().get(0).getLastName());
            Assertions.assertEquals(testUser.getUserType(), response.getBody().get(0).getUserType());
            Assertions.assertEquals(testUser.getCreatedAt(), response.getBody().get(0).getCreatedAt());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testGetAllAppUsers_InvalidId() {
        try {
            URI uri = new URI("http://localhost:" + this.port + "/v1/users");
            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                    .fromUri(uri)
                    .queryParam("id", 0L)
                    .queryParam("login", AppUserTestEntityProvider.APP_USER_TEST_LOGIN)
                    .queryParam("userType", AppUserTestEntityProvider.APP_USER_TEST_USER_TYPE)
                    .queryParam("startDate", TestRegistrationDateFiltersProvider.TEST_START_DATE_FILTER)
                    .queryParam("endDate", TestRegistrationDateFiltersProvider.TEST_END_DATE_FILTER);

            AppUser testUser = AppUserTestEntityProvider
                    .getAppUserTestEntity(AppUserTestEntityProvider.APP_USER_TEST_RAW_PASSWORD);
            HttpHeaders authorizationHeader =
                    this.testAuthorizationHeaderProvider.getAuthorizationHeaderForUser(testUser);

            Mockito
                    .when(this.appUserRepository.findAll(Mockito.any(Predicate.class)))
                    .thenAnswer(invocationOnMock -> List.of(testUser));

            ResponseEntity<String> response = this.testRestTemplate.exchange(
                    uriComponentsBuilder.build().encode().toUri(),
                    HttpMethod.GET,
                    new HttpEntity<>(authorizationHeader),
                    String.class
            );

            Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            Assertions.assertTrue(Objects.nonNull(response.getBody()));
            Assertions.assertEquals("ID пользователя не может быть меньше 1!", response.getBody());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testGetAllAppUsers_IncorrectDateFilters() {
        try {
            URI uri = new URI("http://localhost:" + this.port + "/v1/users");
            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                    .fromUri(uri)
                    .queryParam("id", AppUserTestEntityProvider.APP_USER_TEST_ID)
                    .queryParam("login", AppUserTestEntityProvider.APP_USER_TEST_LOGIN)
                    .queryParam("userType", AppUserTestEntityProvider.APP_USER_TEST_USER_TYPE)
                    .queryParam("startDate", TestRegistrationDateFiltersProvider.TEST_END_DATE_FILTER)
                    .queryParam("endDate", TestRegistrationDateFiltersProvider.TEST_START_DATE_FILTER);

            AppUser testUser = AppUserTestEntityProvider
                    .getAppUserTestEntity(AppUserTestEntityProvider.APP_USER_TEST_RAW_PASSWORD);
            HttpHeaders authorizationHeader =
                    this.testAuthorizationHeaderProvider.getAuthorizationHeaderForUser(testUser);

            Mockito
                    .when(this.appUserRepository.findAll(Mockito.any(Predicate.class)))
                    .thenAnswer(invocationOnMock -> List.of(testUser));

            ResponseEntity<String> response = this.testRestTemplate.exchange(
                    uriComponentsBuilder.build().encode().toUri(),
                    HttpMethod.GET,
                    new HttpEntity<>(authorizationHeader),
                    String.class
            );

            Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            Assertions.assertTrue(Objects.nonNull(response.getBody()));
            Assertions.assertEquals(
                    "Неверно заданы фильтры поиска по дате! Начальная дата не может быть позже конечной!",
                    response.getBody()
            );
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testGetAllAppUsers_AppUsersNotFound() {
        try {
            URI uri = new URI("http://localhost:" + this.port + "/v1/users");
            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                    .fromUri(uri)
                    .queryParam("id", AppUserTestEntityProvider.APP_USER_TEST_ID)
                    .queryParam("login", AppUserTestEntityProvider.APP_USER_TEST_LOGIN)
                    .queryParam("userType", AppUserTestEntityProvider.APP_USER_TEST_USER_TYPE)
                    .queryParam("startDate", TestRegistrationDateFiltersProvider.TEST_START_DATE_FILTER)
                    .queryParam("endDate", TestRegistrationDateFiltersProvider.TEST_END_DATE_FILTER);

            AppUser testUser = AppUserTestEntityProvider
                    .getAppUserTestEntity(AppUserTestEntityProvider.APP_USER_TEST_RAW_PASSWORD);
            HttpHeaders authorizationHeader =
                    this.testAuthorizationHeaderProvider.getAuthorizationHeaderForUser(testUser);

            Mockito
                    .when(this.appUserRepository.findAll(Mockito.any(Predicate.class)))
                    .thenAnswer(invocationOnMock -> new ArrayList<>());

            ResponseEntity<String> response = this.testRestTemplate.exchange(
                    uriComponentsBuilder.build().encode().toUri(),
                    HttpMethod.GET,
                    new HttpEntity<>(authorizationHeader),
                    String.class
            );

            Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            Assertions.assertTrue(Objects.nonNull(response.getBody()));
            Assertions.assertEquals(
                    "По заданным параметрам не найдено ни одного пользователя!",
                    response.getBody()
            );
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testGetAllAppUsers_Unauthorized() {
        try {
            URI uri = new URI("http://localhost:" + this.port + "/v1/users");
            ResponseEntity<List<AppUserResponseDto>> response = this.testRestTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    new HttpEntity<>(new HttpHeaders()),
                    new ParameterizedTypeReference<List<AppUserResponseDto>>() {}
            );
            Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }
}