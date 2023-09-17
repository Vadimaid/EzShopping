package kg.ezshopping.ezshopping.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.ezshopping.ezshopping.dto.ItemColourResponseDto;
import kg.ezshopping.ezshopping.dto.ItemCreateRequestDto;
import kg.ezshopping.ezshopping.dto.ItemCreateResponseDto;
import kg.ezshopping.ezshopping.dto.ItemImageUploadDto;
import kg.ezshopping.ezshopping.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/items")
@Tag(
        name = "Items Controller",
        description = "Endpoint'ы для работы с товарами"
)
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @Operation(
            summary = "Сохранение общей информации о товаре",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Объект содержащий ID сохраненного товара",
                            content = @Content(schema = @Schema(implementation = ItemCreateResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400 BAD_REQUEST",
                            description = "Сообщение об ошибке",
                            content = @Content(schema = @Schema(implementation = String.class))
                    )
            }
    )
    @PostMapping(value = "/create")
    public ResponseEntity<ItemCreateResponseDto> createNewItem(
            @RequestBody ItemCreateRequestDto source
    ) {
        return ResponseEntity.ok(this.itemService.createNewItem(source));
    }

    @Operation(
            summary = "Загрузка изображения товара",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Объект содержащий ID сохраненного товара",
                            content = @Content(schema = @Schema(implementation = ItemCreateResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400 BAD_REQUEST",
                            description = "Сообщение об ошибке",
                            content = @Content(schema = @Schema(implementation = String.class))
                    )
            }
    )
    @PostMapping(value = "/image/{itemId}/upload")
    public ResponseEntity<ItemCreateResponseDto> uploadImageToItem(
            @PathVariable(name = "itemId") Long itemId,
            @RequestBody ItemImageUploadDto source
    ) {
        return ResponseEntity.ok(this.itemService.uploadImageToItem(itemId, source));
    }
}
