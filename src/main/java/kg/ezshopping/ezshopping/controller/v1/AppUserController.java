package kg.ezshopping.ezshopping.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.ezshopping.ezshopping.dto.AppUserResponseDto;
import kg.ezshopping.ezshopping.exception.AppUsersNotFoundException;
import kg.ezshopping.ezshopping.exception.IncorrectDateFiltersException;
import kg.ezshopping.ezshopping.exception.InvalidIdException;
import kg.ezshopping.ezshopping.service.AppUserService;
import kg.ezshopping.ezshopping.types.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/users")
@Tag(
        name = "Application User Controller",
        description = "Endpoint'ы для просмотра информации о пользователях и управления сущностями пользователей"
)
public class AppUserController {
    private final AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Operation(
            summary = "Просмотр информации о текущем авторизованном пользователе.",
            description = "Выводит информацию о текущем авторизованном пользователе. ",
            security = @SecurityRequirement(name = "Bearer Authentication"),
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Информация о текущем авторизованном пользователе",
                            content = @Content(schema = @Schema(implementation = AppUserResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "401 UNAUTHORIZED",
                            description = "Попытка доступа к ресурсу неавторизованным пользователем"
                    )
            }
    )
    @GetMapping(value = "/current")
    public ResponseEntity<AppUserResponseDto> getCurrentAuthorizedUser() {
        return ResponseEntity.ok(this.appUserService.getCurrentAuthorizedUser());
    }

}
