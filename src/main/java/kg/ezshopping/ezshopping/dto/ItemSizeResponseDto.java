package kg.ezshopping.ezshopping.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Объект для передачи размеров товара")
public class ItemSizeResponseDto {

    @Schema(description = "ID размера")
    private Long id;

    @Schema(description = "Название размера")
    private String name;

    public ItemSizeResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public ItemSizeResponseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ItemSizeResponseDto setName(String name) {
        this.name = name;
        return this;
    }
}
