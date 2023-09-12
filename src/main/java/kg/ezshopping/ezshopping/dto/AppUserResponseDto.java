package kg.ezshopping.ezshopping.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kg.ezshopping.ezshopping.types.UserType;

import java.time.LocalDateTime;

@Schema(description = "DTO для передачи данных о пользователе клиентскому приложению в теле ответа")
public class AppUserResponseDto {
    @Schema(description = "ID пользователя", example = "1", required = true, minimum = "1")
    private Long id;
    @Schema(description = "Логин пользователя", example = "example_login", required = true)
    private String login;
    @Schema(description = "Имя пользователя", example = "Иван", required = true)
    private String firstName;
    @Schema(description = "Фамилия пользователя", example = "Петров", required = true)
    private String lastName;
    @Schema(description = "Тип пользователя", example = "CLIENT", required = true)
    private UserType userType;
    @Schema(
            description = "Дата и время регистрации пользователя в формате ISO",
            example = "2020-04-20T12:00:00Z",
            required = true
    )
    private LocalDateTime createdAt;

    public AppUserResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public AppUserResponseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public AppUserResponseDto setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public AppUserResponseDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public AppUserResponseDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserType getUserType() {
        return userType;
    }

    public AppUserResponseDto setUserType(UserType userType) {
        this.userType = userType;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public AppUserResponseDto setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
