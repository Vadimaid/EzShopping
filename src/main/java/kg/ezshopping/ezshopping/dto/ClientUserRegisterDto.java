package kg.ezshopping.ezshopping.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Schema(description = "Объект для создания нового клиента")
public class ClientUserRegisterDto extends AppUserRegisterDto {

    @Schema(description = "Дата рождения", required = true)
    @NotNull
    private LocalDate dateOfBirth;

    @Schema(description = "Почтовый адрес пользователя")
    private String email;

    public ClientUserRegisterDto() {
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public ClientUserRegisterDto setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ClientUserRegisterDto setEmail(String email) {
        this.email = email;
        return this;
    }
}
