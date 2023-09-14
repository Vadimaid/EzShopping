package kg.ezshopping.ezshopping.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kg.ezshopping.ezshopping.types.UserType;

@Schema(description = "DTO для передачи типа пользователя клиентскому приложению в теле ответа")
public class UserTypeResponseDto {
    @Schema(description = "Тип пользователя", example = "CLIENT", required = true)
    private UserType userType;

    public UserTypeResponseDto() {
    }

    public UserType getUserType() {
        return userType;
    }

    public UserTypeResponseDto setUserType(UserType userType) {
        this.userType = userType;
        return this;
    }
}
