package kg.ezshopping.ezshopping.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Объект для передачи ID в случае успешного сохранения товара")
public class ItemCreateResponseDto {

    @Schema(description = "ID сохраненного товара")
    private Long id;

    public ItemCreateResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public ItemCreateResponseDto setId(Long id) {
        this.id = id;
        return this;
    }
}
