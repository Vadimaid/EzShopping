package kg.ezshopping.ezshopping.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.ezshopping.ezshopping.dto.ItemCategoryResponseDto;
import kg.ezshopping.ezshopping.dto.ItemColourResponseDto;
import kg.ezshopping.ezshopping.dto.ItemSizeResponseDto;
import kg.ezshopping.ezshopping.service.CategoryService;
import kg.ezshopping.ezshopping.service.ColourService;
import kg.ezshopping.ezshopping.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/reference")
@Tag(
        name = "Reference Controller",
        description = "Endpoint'ы для получения справочников"
)
public class ReferenceController {

    private final CategoryService categoryService;
    private final SizeService sizeService;
    private final ColourService colourService;

    @Autowired
    public ReferenceController(
            CategoryService categoryService,
            SizeService sizeService,
            ColourService colourService) {
        this.categoryService = categoryService;
        this.sizeService = sizeService;
        this.colourService = colourService;
    }

    @Operation(
            summary = "Получение категорий товаров",
            description = "Использует parent как RequestParam, если не указывать вернет категории верхнего уровня, если указать вернет подкатегории родительской категории",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Список категорий",
                            content = @Content(schema = @Schema(implementation = ItemCategoryResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400 BAD_REQUEST",
                            description = "Сообщение об ошибке",
                            content = @Content(schema = @Schema(implementation = String.class))
                    )
            }
    )
    @GetMapping(value = "/categories")
    public ResponseEntity<List<ItemCategoryResponseDto>> getAvailableCategories(
            @RequestParam(name = "parent", required = false) Long parentId
    ) {
        return ResponseEntity.ok(this.categoryService.findAllAvailableCategories(parentId));
    }

    @Operation(
            summary = "Получение размеров товаров",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Список размеров",
                            content = @Content(schema = @Schema(implementation = ItemSizeResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400 BAD_REQUEST",
                            description = "Сообщение об ошибке",
                            content = @Content(schema = @Schema(implementation = String.class))
                    )
            }
    )
    @GetMapping(value = "/sizes")
    public ResponseEntity<List<ItemSizeResponseDto>> getAvailableSizes() {
        return ResponseEntity.ok(this.sizeService.findAllAvailableSizes());
    }

    @Operation(
            summary = "Получение цветов товаров",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Список цветов",
                            content = @Content(schema = @Schema(implementation = ItemColourResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400 BAD_REQUEST",
                            description = "Сообщение об ошибке",
                            content = @Content(schema = @Schema(implementation = String.class))
                    )
            }
    )
    @GetMapping(value = "/colours")
    public ResponseEntity<List<ItemColourResponseDto>> getAvailableColours() {
        return ResponseEntity.ok(this.colourService.findAllItemColours());
    }
}
