package kg.ezshopping.ezshopping.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public abstract class AppUserRegisterDto {

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

    @Schema(description = "Номер телефона", required = true)
    @NotNull
    @NotEmpty
    private String phone;

    @Schema(description = "Изображение профиля пользователя")
    private String profileImageBase64;

    public String getLogin() {
        return login;
    }

    public AppUserRegisterDto setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AppUserRegisterDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public AppUserRegisterDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public AppUserRegisterDto setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getProfileImageBase64() {
        return profileImageBase64;
    }

    public AppUserRegisterDto setProfileImageBase64(String profileImageBase64) {
        this.profileImageBase64 = profileImageBase64;
        return this;
    }
}
