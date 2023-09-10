package kg.ezshopping.ezshopping.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.ezshopping.ezshopping.dto.AppUserRequestDto;
import kg.ezshopping.ezshopping.dto.AppUserResponseDto;
import kg.ezshopping.ezshopping.dto.JwtResponseDto;
import kg.ezshopping.ezshopping.dto.UserCredentialsRequestDto;
import kg.ezshopping.ezshopping.exception.*;
import kg.ezshopping.ezshopping.service.AppUserService;
import kg.ezshopping.ezshopping.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final AuthenticationService authenticationService;
    private final AppUserService appUserService;

    @Autowired
    public AuthenticationController(
            AuthenticationService authenticationService,
            AppUserService appUserService
    ) {
        this.authenticationService = authenticationService;
        this.appUserService = appUserService;
    }

    @Operation(
            summary = "Регистрация нового пользователя.",
            description = "Регистрирует пользователя в системе. " +
                    "В теле запроса принимает данные пользователя в виде AppUserRequestDto. " +
                    "Возвращает 200-OK и информацию о сохраненном пользователе в теле ответа в виде AppUserResponseDto, " +
                    "если пользователь сохранен успешно. " +
                    "Иначе 400-BAD_REQUEST и в теле ответа сообщение об ошибке"
    )
    @PostMapping(value = "/register")
    public ResponseEntity<AppUserResponseDto> registerNewAppUser(
            @RequestBody AppUserRequestDto appUserRequestDto
    )
            throws InvalidUserInfoException,
            InvalidUserTypeException,
            LoginAlreadyExistsException,
            InvalidUserCredentialsException
    {
        return ResponseEntity.ok(this.appUserService.registerNewAppUser(appUserRequestDto));
    }

    @Operation(
            summary = "Вход в систему.",
            description = "Вход в систему. Возвращает: 200-OK и обернутый в JwtResponseDto jwt, если успешно. " +
                    "Иначе 400-BAD_REQUEST и сообщение об ошибке "
    )
    @PostMapping(value = "/login")
    public ResponseEntity<JwtResponseDto> login(
            @RequestBody UserCredentialsRequestDto userCredentialsRequestDto
    )
            throws InvalidUserCredentialsException,
            WrongPasswordException
    {
        return ResponseEntity.ok(this.authenticationService.login(userCredentialsRequestDto));
    }
}
