package kg.ezshopping.ezshopping.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Объект для передачи категории товара")
public class ItemCategoryResponseDto {

    @Schema(description = "ID категории")
    private Long id;

    @Schema(description = "Название категории")
    private String name;

    public ItemCategoryResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public ItemCategoryResponseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ItemCategoryResponseDto setName(String name) {
        this.name = name;
        return this;
    }
}
