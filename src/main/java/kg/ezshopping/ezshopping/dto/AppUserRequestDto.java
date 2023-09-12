package kg.ezshopping.ezshopping.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kg.ezshopping.ezshopping.types.UserType;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

@Schema(description = "DTO для передачи данных о пользователе на сервер в теле запроса")
public class AppUserRequestDto {
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
    @Schema(description = "Имя пользователя. Не может быть null или пустым", example = "Иван", required = true)
    private String firstName;
    @Schema(description = "Фамилия пользователя. Не может быть null или пустым", example = "Петров", required = true)
    private String lastName;
    @Schema(description = "Тип пользователя. Не может быть null", example = "SHOP", required = true)
    private UserType userType;

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

    public String getFirstName() {
        return firstName;
    }

    public AppUserRequestDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public AppUserRequestDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserType getUserType() {
        return userType;
    }

    public AppUserRequestDto setUserType(UserType userType) {
        this.userType = userType;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUserRequestDto that = (AppUserRequestDto) o;
        return Objects.equals(login, that.login) &&
                Objects.equals(password, that.password) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                userType == that.userType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, firstName, lastName, userType);
    }
}
