package kg.ezshopping.ezshopping.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.ezshopping.ezshopping.dto.*;
import kg.ezshopping.ezshopping.exception.*;
import kg.ezshopping.ezshopping.service.AppUserService;
import kg.ezshopping.ezshopping.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
            summary = "Регистрация нового магазина.",
            description = "Регистрирует пользователя в системе. " +
                    "В теле запроса принимает данные пользователя в виде ShopUserRegisterDto. ",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Информация о зарегестриванном пользователе",
                            content = @Content(schema = @Schema(implementation = AppUserResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400 BAD_REQUEST",
                            description = "Сообщение об ошибке",
                            content = @Content(schema = @Schema(implementation = String.class))
                    )
            }
    )
    @PostMapping(value = "/register/shop")
    public ResponseEntity<AppUserResponseDto> registerNewShop(
            @RequestBody @Valid ShopUserRegisterDto appUserRequestDto
    ) throws LoginAlreadyExistsException, InvalidUserCredentialsException {
        return ResponseEntity.ok(this.appUserService.registerNewShop(appUserRequestDto));
    }

    @Operation(
            summary = "Регистрация нового клиента.",
            description = "Регистрирует пользователя в системе. " +
                    "В теле запроса принимает данные пользователя в виде ClientUserRegisterDto. ",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Информация о зарегестриванном пользователе",
                            content = @Content(schema = @Schema(implementation = AppUserResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400 BAD_REQUEST",
                            description = "Сообщение об ошибке",
                            content = @Content(schema = @Schema(implementation = String.class))
                    )
            }
    )
    @PostMapping(value = "/register/client")
    public ResponseEntity<AppUserResponseDto> registerNewClient(
            @RequestBody @Valid ClientUserRegisterDto appUserRequestDto
    ) throws LoginAlreadyExistsException, InvalidUserCredentialsException {
        return ResponseEntity.ok(this.appUserService.registerNewClient(appUserRequestDto));
    }

    @Operation(
            summary = "Вход в систему.",
            description = "Вход в систему. ",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "JWT",
                            content = @Content(schema = @Schema(implementation = JwtResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400 BAD_REQUEST",
                            description = "Сообщение об ошибке",
                            content = @Content(schema = @Schema(implementation = String.class))
                    )
            }
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
