package kg.ezshopping.ezshopping.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.ezshopping.ezshopping.dto.UserTypeResponseDto;
import kg.ezshopping.ezshopping.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/user-types")
@Tag(
        name = "User Type Controller",
        description = "Endpoint'ы предоставляющие доступ к типам пользователей системы"
)
public class UserTypeController {
    private final UserTypeService userTypeService;

    @Autowired
    public UserTypeController(UserTypeService userTypeService) {
        this.userTypeService = userTypeService;
    }

    @Operation(
            summary = "Просмотр всех зарегистрированных в системе типов пользователей.",
            description = "Просмотр всех зарегистрированных в системе типов пользователей. ",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Список всех зарегестриванных в системе типов пользователей",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = UserTypeResponseDto.class))
                            )
                    )
            }
    )
    @GetMapping(value = "/all")
    public ResponseEntity<List<UserTypeResponseDto>> getAllUserTypes() {
        ;
        return ResponseEntity.ok(this.userTypeService.getAllUserTypes());
    }

    @Operation(
            summary = "Просмотр зарегистрированных в системе несистемных типов пользователей.",
            description = "Просмотр зарегистрированных в системе несистемных типов пользователей.",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Список зарегестриванных в системе несистемных типов пользователей",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = UserTypeResponseDto.class))
                            )
                    )
            }
    )
    @GetMapping(value = "/non-system")
    public ResponseEntity<List<UserTypeResponseDto>> getNonSystemUserTypes() {
        return ResponseEntity.ok(this.userTypeService.getAllNonSystemUserTypes());
    }
}
