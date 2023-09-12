package kg.ezshopping.ezshopping.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO для передачи логина и пароля пользователя в теле запроса для входа в систему")
public class UserCredentialsRequestDto {
    @Schema(
            description = "Логин пользователя. Ограничения: Не может быть null или пустым. Должен быть уникальным",
            example = "example_login",
            required = true
    )
    private String login;
    @Schema(
            description = "Незашифрованный пароль пользователя. Не может быть null или пустым",
            example = "examplePassword",
            required = true
    )
    private String password;

    public UserCredentialsRequestDto() {
    }

    public String getLogin() {
        return login;
    }

    public UserCredentialsRequestDto setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserCredentialsRequestDto setPassword(String password) {
        this.password = password;
        return this;
    }
}
