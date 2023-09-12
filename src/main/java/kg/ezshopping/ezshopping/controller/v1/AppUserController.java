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
import kg.ezshopping.ezshopping.exception.WrongPasswordException;
import kg.ezshopping.ezshopping.service.AppUserService;
import kg.ezshopping.ezshopping.types.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(
            summary = "Просмотр информации о всех пользователях системы",
            description = "Выводит информацию о всех пользователях системы по параметрам. ",
            security = @SecurityRequirement(name = "Bearer Authentication"),
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Список найденных по параметрам запроса пользователей",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = AppUserResponseDto.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400 BAD_REQUEST",
                            description = "Сообщение об ошибке",
                            content = @Content(schema = @Schema(implementation = String.class))
                    ),
                    @ApiResponse(
                            responseCode = "401 UNAUTHORIZED",
                            description = "Попытка доступа к ресурсу неавторизованным пользователем"
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<AppUserResponseDto>> getAllAppUsers(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String login,
            @RequestParam(required = false) UserType userType,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam(required = false) LocalDateTime startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam(required = false) LocalDateTime endDate
    )
            throws IncorrectDateFiltersException,
            AppUsersNotFoundException,
            InvalidIdException
    {
        return ResponseEntity.ok(this.appUserService.getAllAppUsers(id, login, userType, startDate, endDate));
    }

    @PutMapping("/update")
    public ResponseEntity<AppUserResponseDto> putUserInfo(
            @RequestParam(required = true) String oldPassword,
            @RequestParam(required = false) String newPassword,
            @RequestParam(required = false) String newFirstName,
            @RequestParam(required = false) String newLastName,
            @RequestParam(required = false) UserType newUserType,
            @RequestParam(required = false) Boolean newIsActive
    ) throws WrongPasswordException {

        return ResponseEntity.ok(
                this.appUserService.
                        updateUserInfo(
                                oldPassword,
                                newPassword,
                                newFirstName,
                                newLastName,
                                newUserType,
                                newIsActive
                        )
        );
    }
}
