package kg.ezshopping.ezshopping.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.ezshopping.ezshopping.dto.AppUserRequestDto;
import kg.ezshopping.ezshopping.dto.AppUserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/auth")
@Tag(
        name = "Authentication Controller",
        description = "Endpoint'ы для регистрации и авторизации пользователя"
)
public class AuthenticationController {
    @Operation(
            summary = "Регистрация нового пользователя.",
            description = ""
    )
    @PostMapping(value = "/register")
    public ResponseEntity<AppUserResponseDto> registerNewAppUser(
            @RequestBody AppUserRequestDto appUserRequestDto
    ) {
        return null;
    }
}
