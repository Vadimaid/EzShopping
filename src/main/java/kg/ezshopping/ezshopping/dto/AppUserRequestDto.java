package kg.ezshopping.ezshopping.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "DTO для передачи данных о пользователе на сервер в теле запроса")
public class AppUserRequestDto {

    @Schema(description = "Логин пользователя. Ограничения: Не может быть null или пустым. Должен быть уникальным", example = "example_login", required = true)
    @NotNull
    @NotEmpty
    private String login;

    @Schema(description = "Незашифрованный пароль пользователя. Не может быть null или пустым", example = "examplePassword", required = true)
    @NotNull
    @NotEmpty
    private String password;

    @Schema(description = "Имя пользователя. Не может быть null или пустым", example = "Иванов Иван", required = true)
    @NotNull
    @NotEmpty
    private String fullName;


    public AppUserRequestDto() {
    }

    public String getLogin() {
        return login;
    }

    public AppUserRequestDto setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AppUserRequestDto setPassword(String password) {
        this.password = password;
        return this;
    }
}
